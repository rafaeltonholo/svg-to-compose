#!/bin/bash
# Parse and validate required parameters
root_directory="${1:?Error: Root directory must be the first parameter}"
ext="${2:?Error: File extension must be the second parameter}"

# Parse optimization and rebuild flags (rebuild can be $3 or $4)
optimize="false"
suffix="nonoptimized"
rebuild=""

if [ "$3" == "optimize" ]; then
  optimize="true"
  suffix="optimized"
  [ "$4" == "--rebuild" ] && rebuild="--upgrade"
elif [ "$3" == "--rebuild" ]; then
  rebuild="--upgrade"
fi

# Determine file type based on extension
if [ "$ext" == "xml" ]; then
  type="avg"
else
  type="svg"
  ext="svg"
fi

rebuild_applied="false"

# Package must match exactly what the Gradle plugin functional tests use so
# both tools validate against the same expected .kt files.
package="dev.tonholo.s2c.integrity.icon.${type}"
expected_dir="$root_directory/integrity-check/expected"
errors=()

# Convert kebab-case or snake_case filename to PascalCase.
to_pascal_case() {
   echo "$1" | awk -F'[-_]' '{ for(i=1;i<=NF;i++) $i=toupper(substr($i,1,1)) substr($i,2); print }' OFS=''
 }

# Only process flat (non-directory) files in samples/{type}/ — subdirectories
# such as gradient/, rects/, css/ etc. are intentionally excluded.
for input in "$root_directory/samples/${type}"/*."${ext}"; do
  [ -f "$input" ] || continue

  basename="${input##*/}"
  stem="${basename%.*}"
  icon_name=$(to_pascal_case "$stem")
  expected_file="$expected_dir/${icon_name}.${ext}.${suffix}.kt"

  if [ "$optimize" == "true" ]; then
    if [ "$type" == "svg" ]; then
      echo "svgo version $(svgo --version)"
    else
      echo "avocado version $(avocado --version)"
    fi
  fi

  s2c_version=$(command "$root_directory/s2c" --version)
  echo "Parsing $stem to Jetpack Compose Icon using $s2c_version"

  # Write to a temp file named after the icon so the generator derives the
  # correct Kotlin identifier from the file name.
  tmp_dir="$(mktemp -d)"
  tmp_output="${tmp_dir}/${icon_name}.kt"

  rebuild_arg=""
  if [ "$rebuild_applied" == "false" ]; then
    rebuild_arg="$rebuild"
  fi

  if ! command "$root_directory/s2c" \
        -o "$tmp_output" \
        -p "$package" \
        --theme "" \
        --no-preview \
        -opt="$optimize" \
        --debug \
        ${rebuild_arg:+"$rebuild_arg"} \
        "$input"; then
    echo "Failed to execute CLI integrity check for $stem."
    rm -rf "$tmp_dir"
    errors+=("$stem.$ext")
    continue
  fi

  if [ -n "$rebuild" ]; then
    rebuild_applied="true"
  fi

  if [ ! -f "$expected_file" ]; then
    mkdir -p "$expected_dir"
    cp "$tmp_output" "$expected_file"
    echo "BOOTSTRAP: Wrote expected file ${icon_name}.${ext}.${suffix}.kt"
    rm -rf "$tmp_dir"
    continue
  fi

  echo "Verifying $stem against expected file."
  if ! diff --strip-trailing-cr "$tmp_output" "$expected_file"; then
    errors+=("$stem.$ext")
  else
    echo "✅ $stem.$ext pass"
  fi
  rm -rf "$tmp_dir"
done

echo
echo
if [ "${#errors[@]}" == 0 ]; then
  echo "✅ Integrity check pass"
  exit 0
else
  echo "❌ Integrity check failed"
  echo
  echo "Failed files:"
  for f in "${errors[@]}"; do
    echo "    - $f"
  done
  exit 1
fi
