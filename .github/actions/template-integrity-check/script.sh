#!/bin/bash
root_directory=$1
if [ "$root_directory" == "" ]; then
  echo "The root directory must be the first parameter."
  exit 1
fi

use_default_differ="false"
rebuild=""

shift # consume root_directory ($1), leaving only flags
while [ $# -gt 0 ]; do
  case "$1" in
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

differ="diff --strip-trailing-cr"
if [ "$use_default_differ" == "false" ] && command -v delta &> /dev/null; then
  differ="delta -s --paging=never"
fi
rebuild_applied="false"

template_file="$root_directory/playground/s2c.template.toml"
expected_dir="$root_directory/integrity-check/expected/template"
errors=()

# Convert kebab-case or snake_case filename to PascalCase.
to_pascal_case() {
   echo "$1" | awk -F'[-_]' '{ for(i=1;i<=NF;i++) $i=toupper(substr($i,1,1)) substr($i,2); print }' OFS=''
 }

# Hardcoded list of input files covering all 5 categories (simple, groups,
# chunks, gradients, masks) across SVG and AVG.
svg_files=(
  "samples/svg/attention-filled.svg"
  "samples/svg/android.svg"
  "samples/svg/brasil.svg"
  "samples/svg/gradient/linear-gradient01.svg"
  "samples/svg/mask/mask-with-group.svg"
)
avg_files=(
  "samples/avg/shield-halved-solid.xml"
  "samples/avg/android.xml"
  "samples/avg/gradient/stroke_gradient.xml"
)

process_file() {
  local input="$1"
  local type="$2"
  local ext="$3"
  local package="dev.tonholo.s2c.integrity.icon.${type}"

  basename="${input##*/}"
  stem="${basename%.*}"
  icon_name=$(to_pascal_case "$stem")
  expected_file="$expected_dir/${icon_name}.${ext}.template.kt"

  s2c_version=$(command "$root_directory/s2c" --version)
  echo "Parsing $stem to Jetpack Compose Icon (template) using $s2c_version"

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
        --template "$template_file" \
        ${rebuild_arg:+"$rebuild_arg"} \
        "$root_directory/$input"; then
    echo "Failed to execute CLI template integrity check for $stem."
    rm -rf "$tmp_dir"
    errors+=("$stem.$ext")
    return
  fi

  if [ -n "$rebuild" ]; then
    rebuild_applied="true"
  fi

  if [ ! -f "$expected_file" ]; then
    mkdir -p "$expected_dir"
    cp "$tmp_output" "$expected_file"
    echo "BOOTSTRAP: Wrote expected file ${icon_name}.${ext}.template.kt"
    rm -rf "$tmp_dir"
    return
  fi

  echo "Verifying $stem (template) against expected file."
  if ! $differ "$tmp_output" "$expected_file"; then
    errors+=("$stem.$ext")
  else
    echo "✅ $stem.$ext (template) pass"
  fi
  rm -rf "$tmp_dir"
}

for input in "${svg_files[@]}"; do
  process_file "$input" "svg" "svg"
done

for input in "${avg_files[@]}"; do
  process_file "$input" "avg" "xml"
done

echo
echo
if [ "${#errors[@]}" -eq 0 ]; then
  echo "✅ Template integrity check pass"
  exit 0
else
  echo "❌ Template integrity check failed"
  echo
  echo "Failed files:"
  for f in "${errors[@]}"; do
    echo "    - $f"
  done
  exit 1
fi
