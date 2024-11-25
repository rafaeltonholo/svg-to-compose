#!/bin/bash
declare -a files=(
  "shield-halved-solid"
  "illustration"
)
declare -a names=(
  "ShieldSolid"
  "Illustration"
)
root_directory=$1
if [ "$root_directory" == "" ]; then
  echo "The root directory must be the first parameter."
  exit 1
fi
ext=$2
if [ "$ext" == "" ]; then
  echo "The file extension must be the second parameter."
  exit 1
fi
optimize="false"
suffix="NonOptimized"
if [ "$3" == "optimize" ]; then
  optimize="true"
  suffix="Optimized"
fi

type="svg"
if [ "$ext" == "xml" ]; then
  type="avg"
else
  ext="svg"
fi

base_package="dev.tonholo.svg_to_compose.playground.ui"
package="$base_package.icon.${type}"
theme="$base_package.theme.SampleAppTheme"
errors=()

for index in "${!files[@]}"; do
  filename=${files[index]}
  output_name="${names[index]}.${type}"
  input="$root_directory/samples/${type}/$filename.${ext}"
  output="$root_directory/integrity-check/${type}/$output_name.${suffix}.kt"

  if [ $optimize == "true" ]; then
    if [ $type == "svg" ]; then
      echo "svgo version $(svgo --version)"
    else
      echo "avocado version $(avocado --version)"
    fi
  fi

  s2c_version=$(command "$root_directory/s2c" --version)
  echo "Parsing $filename to Jetpack Compose Icon using $s2c_version"
  if ! command "$root_directory/s2c" -o "$output" \
        -p "$package" \
        --theme "$theme" \
        -opt=$optimize \
        "$input"; then
      echo "Failed to execute CLI integrity check."
      exit 1
  fi
  diff_suffix=$(echo $suffix | tr "[:upper:]" "[:lower:]")
  diff_file="$root_directory/samples/$output_name.${diff_suffix}.kt"

  echo "Verifying $filename with provided sample."
  if ! diff --strip-trailing-cr "$output" "$diff_file"; then
    errors+=("$filename")
  fi
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
  for index in "${!errors[@]}"; do
    echo "    - ${errors[index]}.${ext}"
  done
  exit 1
fi
