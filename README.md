# SVG to Compose
A command-line tool for convert SVG or an Android Vector Drawable to Android Jetpack Compose Icons.

## Why?
We usually use Android Vector drawables to display icons on Android apps.

With the addition of Jetpack Compose, we don't use XML to write views and we can use all the power of Kotlin to speed-up the view development process.

With that in mind, what if we could also avoid using our old approach to import icons to our project, and now rely on `ImageVector`s and creating the icons using Kotlin, following the same approach that is made for all Material Icons, from Google.

There are existing plugins available, but they usually don't optimize the svg before converting it to Jetpack Compose Icons.

This library adds that functionallity.

## Installation
As this is just a shell script, the installation is very simple:
1. Download the s2c script from this repository and save it in your preferred folder
2. Give execution permission to the script:
```sh
chmod +x s2c
```
3. If you want to run the script from anywhere, you might need to add it to your path, in your `~/.bashrc`, `~/.zshrc`, `~/.zshenv`, or `~/.profile`:
```sh
export PATH=<s2c path>:$PATH
```
Replacing `<s2c path>` to the folder's path where you stored the script

### Dependencies
This script relies on three others to perform the optimization:
- [SVGO](https://github.com/svg/svgo): Optmizes the SVG reducing the paths.
```sh
npm -g install svgo
```
- [SVG2VectorDrawable](https://github.com/Ashung/svg2vectordrawable): Converts the SVG to an Android Vector.
```sh
npm install -g svg2vectordrawable
```
- [Avocado](https://github.com/alexjlockwood/avocado): Optimizes Android VectorDrawable (VD) and AnimatedVectorDrawable (AVD) xml files.
```sh
npm -g install avocado
```

If you don't want to optimizes the SVG before converting it, you can just disable the optimization using the parameter `-opt` or `--optimize` passing `false`


## Use in command-line
Convert a SVG to a Compose Icon:
```sh
s2c -o OutputIconFile.kt -p your.app.package.icon -t your.app.package.theme.YourAppComposeTheme input.svg
```

Convert an Android Drawable Vector to a Compose Icon:
```sh
s2c -o OutputIconFile.kt -p your.app.package.icon -t your.app.package.theme.YourAppComposeTheme input.xml
```

Convert all SVGs and Android Drawable Vectors to Compose Icons:
```sh
s2c -o /my/desired/directory -p your.app.package.icon -t your.app.package.theme.YourAppComposeTheme /my/svg/or/xml/directory
```

Help for advance usage:
```sh
s2c --help
```

Disabling SVG optimization:

```sh
s2c -o OutputIconFile.kt -p your.app.package.icon -t your.app.package.theme.YourAppComposeTheme --optmize false input.svg
```

> **Important**: If you don't specify the full qualifier of the Theme, you'll need to add the import it later.

# License
This software is released under the terms of the [MIT license](https://github.com/rafaeltonholo/svg-to-compose/blob/main/LICENSE).
