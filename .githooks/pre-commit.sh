#!/bin/sh
readonly INVALID_OUTPUT_FILE_ERROR_CODE=-1
fn_create_output_file() {
    TYPE=$(echo "$1" | tr '[:upper:]' '[:lower:]')
    BASE_DIR="./build/reports/detekt"
    if [ "$TYPE" = "library" ]; then
        echo "$BASE_DIR/detekt-library-$(date +%s).log"
    elif [ "$TYPE" = "plugin" ]; then
        echo "$BASE_DIR/detekt-plugin-$(date +%s).log"
    else
        echo "Invalid TYPE. Please provide either 'library' or 'plugin'"
        exit $INVALID_OUTPUT_FILE_ERROR_CODE
    fi
}

fn_run_detekt() {
    TYPE=$(echo "$1" | tr '[:upper:]' '[:lower:]')
    echo "Running $TYPE detekt check..."
    GRADLE_TASK="detekt"
    if [ "$TYPE" = "library" ]; then
        GRADLE_TASK="detektMetadataCommonMain"
    fi
    OUTPUT_FILE=$(fn_create_output_file "$TYPE")

    ./gradlew "$GRADLE_TASK" > "$OUTPUT_FILE"
    EXIT_CODE=$?
    if [ $EXIT_CODE -ne 0 ]; then
        cat "$OUTPUT_FILE"
        rm "$OUTPUT_FILE"
        echo "********************************************"
        echo "                 Detekt failed              "
        echo " Please fix the above issues before pushing "
        echo "********************************************"
        exit $EXIT_CODE
    fi
    rm "$OUTPUT_FILE"
}


fn_main() {
    fn_run_detekt "library"
    fn_run_detekt "plugin"

    exit 0
}

fn_main
