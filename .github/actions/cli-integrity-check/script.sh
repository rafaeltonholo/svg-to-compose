#!/bin/bash
# Parse and validate the required positional parameter
root_directory="${1:?Error: Root directory must be the first parameter}"
shift # consume root_directory, leaving only flags

# Parse flags
optimize="false"
suffix="nonoptimized"
rebuild=""
use_default_differ="false"
rebuild_applied="false"
ext=""

while [ $# -gt 0 ]; do
  case "$1" in
    --extension)
      ext="${2:?Error: --extension flag requires a value}"
      shift 2
      ;;
    --optimize)
      optimize="true"
      suffix="optimized"
      shift
      ;;
    --rebuild)
      rebuild="--upgrade"
      shift
      ;;
    --default-differ)
      use_default_differ="true"
      shift
      ;;
    *)
      echo "Warning: unknown flag '$1'" >&2
      shift
      ;;
  esac
done

if [ -z "$ext" ]; then
  echo "Error: --extension flag is required" >&2
  exit 1
fi

# Determine file type based on extension
if [ "$ext" = "xml" ]; then
  type="avg"
else
  type="svg"
  ext="svg"
fi

differ="diff --strip-trailing-cr"
if [ "$use_default_differ" = "false" ] && command -v delta &> /dev/null; then
  differ="delta -s --paging=never"
fi

# Package must match exactly what the Gradle plugin functional tests use so
# both tools validate against the same expected .kt files.
package="dev.tonholo.s2c.integrity.icon.${type}"
expected_dir="$root_directory/integrity-check/expected"
errors=()

# Convert kebab-case or snake_case filename to PascalCase.
to_pascal_case() {
  echo "$1" | awk -F'[-_]' '{ for(i=1;i<=NF;i++) $i=toupper(substr($i,1,1)) substr($i,2); print }' OFS=''
}

# Only process flat (non-directory) files in samples/{type}/ - subdirectories
# such as gradient/, rects/, css/ etc. are intentionally excluded.
for input in "$root_directory/samples/${type}"/*."${ext}"; do
  [ -f "$input" ] || continue

  basename="${input##*/}"
  stem="${basename%.*}"
  icon_name=$(to_pascal_case "$stem")
  expected_file="$expected_dir/${icon_name}.${ext}.${suffix}.kt"

  if [ "$optimize" = "true" ]; then
    if [ "$type" = "svg" ]; then
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
  if [ "$rebuild_applied" = "false" ]; then
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
  if ! $differ "$tmp_output" "$expected_file"; then
    errors+=("$stem.$ext")
  else
    echo "✅ $stem.$ext pass"
  fi
  rm -rf "$tmp_dir"
done

echo
echo
if [ "${#errors[@]}" -eq 0 ]; then
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
