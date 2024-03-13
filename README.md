# SVG to Compose
A command-line tool for convert SVG or an Android Vector Drawable (AVD) to Android Jetpack Compose Icons.

## Table of content
- [Why?](#why)
- [Platform support](#platform-support)
- [Installation](#installation)
- [External dependencies](#external-dependencies)
- [Using the command-line tool](#using-the-command-line-tool)
- [Result Examples](#result-examples)

## Why?
We usually use Android Vector drawables to display icons on Android apps.

With the addition of Jetpack Compose, we don't use XML to write views, and we can use all the power of Kotlin to speed 
up the view development process.

With that in mind, what if we could also avoid using our old approach to import icons to our project, and now rely on 
`ImageVector`s and creating the icons using Kotlin, following the same approach made for all Material Icons, from Google.

There are existing plugins available, but they usually don't optimize the svg before converting it to Jetpack Compose 
Icons.

This command-line tool adds that functionality.

## Platform support
| Platform           | With optimization | Without optimization |
|--------------------|:-----------------:|:--------------------:|
| macOS Arm64        |         ✅         |          ✅           |
| macOS x64          |         ✅         |          ✅           |
| Linux x64          |         ✅         |          ✅           |
| Windows (mingwX64) |         ✅         |          ✅           |
| Windows (WSL)      |         ✅         |    ❌ (not tested)    |


## Installation
This CLI tool relies on [Kotlin Native](https://kotlinlang.org/docs/native-overview.html) to parse the SVG/AVD file,
thus we need the binaries to be able to run it.
There are two ways to achieve this:
1. Download the s2c script from this repository and save it in your preferred folder, or
2. Cloning the project.

The script will take care of downloading or building the native binaries.

After downloading the script or cloning the project:
1. Give execution permission to the script:
```console
chmod +xw s2c
```
2. If you want to run the script from anywhere, you might need to add it to your path, in your `~/.bashrc`, `~/.zshrc`, 
`~/.zshenv`, or `~/.profile`:
```shell
export PATH=<s2c path>:$PATH
```
Replacing `<s2c path>` to the folder's path where you stored the script

### External Dependencies
This script relies on three others to perform the **optimization**:
- [SVGO](https://github.com/svg/svgo): Optimizes the SVG reducing the paths.
```console
npm -g install svgo
```
- [SVG2VectorDrawable](https://github.com/Ashung/svg2vectordrawable): Converts the SVG to an Android Vector.
```console
npm install -g svg2vectordrawable
```
- [Avocado](https://github.com/alexjlockwood/avocado): Optimizes Android VectorDrawable and AnimatedVectorDrawable xml 
files.
```console
npm -g install avocado
```
> [!IMPORTANT]
> If you don't want to optimize the SVG before converting it, you can just disable the optimization using the parameter
> `-opt` or `--optimize` passing `false`.
> 
> Optimization is enabled by default.


## Using the command-line tool
Help for advance usage:
```console
s2c --help
```
Help output:
```console
Usage: client [<options>] <path>

Options:
  -v, --version                Show this CLI version
  -p, --package=<text>         Specify icons's package. This will replace package at the top of the icon file
  -t, --theme=<text>           Specify project's theme name. This will take place in the Icon Preview composable function and in the ImageVector Builder's names.
  -o, --output=<text>          output filename; if no .kt extension specified, it will be automatically added. In case of the input is a directory, output MUST also be a directory.
  -opt, --optimize=true|false  Enable svg optimization before parsing to Jetpack Compose icon. The optimization process uses the following programs: svgo, svg2vectordrawable, avocado from NPM Registry
  -rt, --receiver-type=<text>  Adds a receiver type to the Icon definition. This will generate the Icon as a extension of the passed argument.

                               E.g.: s2c <args> -o MyIcon.kt -rt Icons.Filled my-icon.svg will creates the Compose Icon:

                               val Icons.Filled.MyIcon: ImageVector.
  --add-to-material            Add the icon to the Material Icons context provider.
  --debug                      Enable debug log.
  --verbose                    Enable verbose log.
  -np, --no-preview, --kmp     Removes the preview function from the file. It is very useful if you are generating the icons for KMP, since KMP doesn't support previews yet.
  --make-internal              Mark the icon as internal
  --minified                   Remove all comments explaining the path logic creation and inline all method parameters.
  -h, --help                   Show this message and exit

Arguments:
  <path>  file *.svg | *.xml | directory
```

Convert an SVG to a Compose Icon:
```console
s2c -o OutputIconFile.kt \
    -p your.app.package.icon \
    -t your.app.package.theme.YourAppComposeTheme \
    input.svg
```

Convert an Android Drawable Vector to a Compose Icon:
```console
s2c -o OutputIconFile.kt \
  -p your.app.package.icon \
  -t your.app.package.theme.YourAppComposeTheme \
  input.xml
```

Convert all SVGs and Android Drawable Vectors within a directory to Compose Icons:
```console
s2c -o /my/desired/directory \
  -p your.app.package.icon \
  -t your.app.package.theme.YourAppComposeTheme \
  /my/svg/or/xml/directory
```
> [!WARNING]
> If the input path is a directory and the output is not a directory,
> the CLI will not parse any icon and will finish the execution with an error.

Disabling SVG optimization:

```console
s2c -o OutputIconFile.kt \
  -p your.app.package.icon \
  -t your.app.package.theme.YourAppComposeTheme \
  --opitmize false \
  input.svg
```

> [!IMPORTANT]
> If you don't specify the full qualifier of the Theme, you'll need to add the import it later.

## Result Examples
### Simple SVG file
#### Without optimization
Command:
```console
./s2c -o <app path>/app/src/main/java/dev/tonholo/composeicons/ui/icon/ShieldSolid.kt \
      -p dev.tonholo.composeicons.ui.icon \
      --theme dev.tonholo.composeicons.ui.theme.ComposeIconsTheme \
      -opt=false \
      <parent-path>/shield-halved-solid.svg
```
Input file: ![shield-halved-solid.svg](samples/svg/shield-halved-solid.svg)

Output file: [ShieldSolid.nonoptimized.kt](samples/ShieldSolid.svg.nonoptimized.kt)

#### With optimization
Command:
```console
./s2c -o <app path>/app/src/main/java/dev/tonholo/composeicons/ui/icon/ShieldSolid.kt \
      -p dev.tonholo.composeicons.ui.icon \
      --theme dev.tonholo.composeicons.ui.theme.ComposeIconsTheme \
      -opt=true \
      <parent-path>/shield-halved-solid.svg
```

Input file: ![shield-halved-solid.svg](samples/svg/shield-halved-solid.svg)

Output file: [ShieldSolid.svg.optimized.kt](samples/ShieldSolid.svg.optimized.kt)


### Complex SVG file
#### Without optimization
Command:
```console
./s2c -o <app path>/app/src/main/java/dev/tonholo/composeicons/ui/icon/Illustration.kt \
      -p dev.tonholo.composeicons.ui.icon \
      --theme dev.tonholo.composeicons.ui.theme.ComposeIconsTheme \
      -opt=false \
      <parent-path>/illustration.svg
```

Input file: ![illustration.svg](samples/svg/illustration.svg)

Output file: [Illustration.svg.nonoptimized.kt](samples/Illustration.svg.nonoptimized.kt)

#### With optimization
Command:
```console
./s2c -o <app path>/app/src/main/java/dev/tonholo/composeicons/ui/icon/Illustration.kt \
      -p dev.tonholo.composeicons.ui.icon \
      --theme dev.tonholo.composeicons.ui.theme.ComposeIconsTheme \
      -opt=true \
      <parent-path>/illustration.svg
```

Input file: ![illustration.svg](samples/svg/illustration.svg)

Output file: [Illustration.svg.optimized.kt](samples/Illustration.svg.optimized.kt)


# License
This software is released under the terms of the [MIT license](https://github.com/rafaeltonholo/svg-to-compose/blob/main/LICENSE).
