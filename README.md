# SVG to Compose

[![Built with KMP](https://img.shields.io/badge/Built_with_KMP-gray?logo=kotlin)](https://kotlinlang.org/docs/multiplatform.html)
[![SVG to Compose Latest version](https://img.shields.io/maven-central/v/dev.tonholo.s2c/svg-to-compose?display_name=tag&label=svg-to-compose&logo=apachemaven)](https://central.sonatype.com/artifact/dev.tonholo.s2c/svg-to-compose)
[![SVG to Compose Gradle Plugin Latest version](https://img.shields.io/maven-central/v/dev.tonholo.s2c/svg-to-compose-gradle-plugin?display_name=tag&label=svg-to-compose-gradle-plugin&logo=apachemaven)](https://central.sonatype.com/artifact/dev.tonholo.s2c/svg-to-compose-gradle-plugin)
[![LICENSE](https://img.shields.io/github/license/rafaeltonholo/svg-to-compose)](./LICENSE)

A suite of tools to convert SVG or Android Vector Drawable (AVD/XML) files into
Android Jetpack Compose Icons. This project provides:

- A **command-line tool** for manual conversion.
- A **Gradle plugin** for automating the conversion within your build process.

---

For more detailed information on each tool, configurations, and features, please
refer to the full documentation:

- [Command-line Tool Documentation](./svg-to-compose/README.md)
- [Gradle Plugin Documentation](./svg-to-compose-gradle-plugin/README.md)

---

## Table of Contents

- [Why?](#why)
- [Platform Support](#platform-support)
- [Available Tools](#available-tools)
    - [Command-line Tool](#command-line-tool)
    - [Gradle Plugin](#gradle-plugin)
- [Getting Started](#getting-started)
    - [Command-line Tool Installation](#command-line-tool-installation)
    - [Gradle Plugin Installation](#gradle-plugin-installation)
- [Usage](#usage)
    - [Using the Command-line Tool](#using-the-command-line-tool)
    - [Using the Gradle Plugin](#using-the-gradle-plugin)
- [License](#license)

## Why?

With the introduction of Jetpack Compose, Android developers can leverage the
full power of Kotlin to build UI components, moving away from traditional XML
layouts. However, integrating vector assets like icons often still relies on
using Android Vector Drawables (AVD/XML) resources.

This project aims to streamline the integration of vector assets into Compose
applications by providing tools that convert SVG or AVD files directly into
Compose `ImageVector` objects, following the same approach used for Google's
Material Icons.

**Key Advantages:**

- **Custom Parsing Algorithm:** The project employs its own parsing algorithm,
  written on Kotlin Multiplatform, specifically designed to handle complex
  vector graphics that are not fully supported by the standard
  `com.android.tools:sdk-common` library.
- **Addresses Missing Features:** By addressing missing features in the default
  SDK tools, the algorithm can parse and convert complex SVGs and AVGs that
  other tools might fail to process correctly.
- **Supports Complex Vectors:** Capable of handling intricate vector graphics,
  ensuring that even detailed icons are accurately converted.
- **Optimization via Trusted Tools**: The optimization of SVGs is performed
  using external, well-known dependencies like **[SVGO](https://github.com/svg/svgo)** 
  and **[Avocado](https://github.com/alexjlockwood/avocado)**, ensuring 
  efficient and clean generated code without reinventing the wheel.

## Platform Support

### Command-line Tool

| Platform           | Command-line Tool |
|--------------------|:-----------------:|
| macOS Arm64        |         ✅         |
| macOS x64          |         ✅         |
| Linux x64          |         ✅         |
| Windows (mingwX64) |         ✅         |
| Windows (WSL)      |         ✅         |

### Gradle Plugin

| Platform             | Gradle Plugin |
|----------------------|:-------------:|
| Android              |       ✅       |
| Kotlin Multiplatform |       ✅       |

## Available Tools

### Command-line Tool

A CLI tool for manually converting SVG or AVD files into Jetpack Compose
`ImageVector` objects. It supports optimization of SVGs and provides various
options for customization.

Ideal for CI integration as no additional dependencies are required (not even
Java) other than the CLI tool's script and, if you wish, the optimization tools.

[Full documentation for the Command-line Tool can be found here.](./svg-to-compose/README.md)

### Gradle Plugin

A Gradle plugin that automates the conversion process within your build system,
ideal for projects with a large number of icons or for ensuring consistency and
saving development time.

[Full documentation for the Gradle Plugin can be found here.](./svg-to-compose/README.md)

## Getting Started

### Command-line Tool Installation

The CLI tool relies
on [Kotlin Native](https://kotlinlang.org/docs/native-overview.html) to parse
the SVG/AVD files. You can install it by:

1. **Downloading the `s2c` script** from this repository and saving it in your
   preferred folder, or
2. **Cloning the project**.

The script will handle downloading or building the native binaries.

After downloading the script or cloning the project:

1. **Give execution permission** to the script:

    ```console
    chmod +xw s2c
    ```

2. **Optionally, add the script to your PATH** to run it from anywhere:

    ```shell
    export PATH=<s2c path>:$PATH
    ```

   Replace `<s2c path>` with the folder's path where you stored the script.

#### External Dependencies

> [!NOTE]
> This is optional. If you don't want to use external dependencies, make sure to
> disable optimization via `--optimize false` when using the CLI tool or by
> calling the `optimize(enabled = false)` when using the Gradle Plugin.

> [!IMPORTANT]
> By default, Optimization is enabled by default on both CLI tool and Gradle
> Plugin.

For SVG optimization, this script relies on:

- **[SVGO](https://github.com/svg/svgo)**: Optimizes SVG files by reducing paths.

    ```console
    npm -g install svgo
    ```

- **[Avocado](https://github.com/alexjlockwood/avocado)**: Optimizes Android
  VectorDrawable and AnimatedVectorDrawable XML files.

    ```console
    npm -g install avocado
    ```

### Gradle Plugin Installation

The **SVG/XML to Compose** Gradle Plugin is available
on [Maven Central](https://search.maven.org/). It simplifies the process of
converting SVG and Android Vector Drawable (AVG/XML) files into Jetpack Compose
`ImageVector` properties, automating the integration of vector assets into your
Compose projects, ensuring a more efficient and error-free workflow.

#### Applying the plugin

Add the plugin to your module's `build.gradle.kts` file:

```kotlin
plugins {
    id("dev.tonholo.s2c") version "<latest-version>"
}
```

Ensure that Maven Central is included in your plugin repositories. If not, add
the following to your `settings.gradle.kts` or `build.gradle.kts`:

```kotlin
pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}
```

### Configuring .gitignore

Both the CLI tool and the Gradle plugin create a hidden folder to handle the
conversion of the vectors without modifying the original file.

To avoid VCS noise, make sure you add the following to your `.gitignore` which
is located in the root folder of your project or the folder you are using the
tool in:

```
.s2c
```

The algorithm will delete all the contents of the `.s2c` folder after parsing,
but leave it empty. If you delete the folder, it will be recreated on the next
run.

## Usage

### Using the Command-line Tool

> [!NOTE]
> For detailed usage instructions and options, please refer to
> the [Command-line Tool Documentation](./svg-to-compose/README.md).

To see all available options, run:

```console
s2c --help
```

**Example Commands**

- **Convert an SVG to a Compose Icon:**

    ```console
    s2c -o OutputIconFile.kt \
        -p your.app.package.icon \
        -t your.app.package.theme.YourAppComposeTheme \
        input.svg
    ```

- **Convert an Android Vector Drawable to a Compose Icon:**

    ```console
    s2c -o OutputIconFile.kt \
        -p your.app.package.icon \
        -t your.app.package.theme.YourAppComposeTheme \
        input.xml
    ```

- **Convert all SVGs and AVGs within a directory to Compose Icons:**

    ```console
    s2c -o /my/desired/directory \
        -p your.app.package.icon \
        -t your.app.package.theme.YourAppComposeTheme \
        /my/svg/or/xml/directory
    ```

### Using the Gradle Plugin

> [!NOTE]
> For a complete list of configuration options and advanced usage, please refer
> to the [Gradle Plugin Documentation](./svg-to-compose-gradle-plugin/README.md).

After applying the plugin, configure it in your `build.gradle.kts` file using
the `svgToCompose` extension. This extension allows you to specify how the
SVG/AVG files should be processed and converted.

**Basic Configuration Example**

```kotlin
svgToCompose {
    processor {
        val projectIcons by creating {
            from(layout.projectDirectory.dir("src/main/resources/icons"))
            destinationPackage("com.example.app.ui.icons")
            icons {
                theme("com.example.app.ui.theme.AppTheme")
            }
            // Additional configurations...
        }
    }
}
```

## Result Examples

### Simple SVG File

#### Without Optimization

Command:

```console
s2c -o <app path>/app/src/main/java/dev/tonholo/composeicons/ui/icon/ShieldSolid.kt \
    -p dev.tonholo.composeicons.ui.icon \
    --theme dev.tonholo.composeicons.ui.theme.ComposeIconsTheme \
    -opt=false \
    <parent-path>/shield-halved-solid.svg
```

Input file: ![shield-halved-solid.svg](https://raw.githubusercontent.com/rafaeltonholo/svg-to-compose/refs/heads/main/samples/svg/shield-halved-solid.svg)

Output file: [ShieldSolid.nonoptimized.kt](https://raw.githubusercontent.com/rafaeltonholo/svg-to-compose/refs/heads/main/samples/ShieldSolid.svg.nonoptimized.kt)

#### With Optimization

Command:

```console
s2c -o <app path>/app/src/main/java/dev/tonholo/composeicons/ui/icon/ShieldSolid.kt \
    -p dev.tonholo.composeicons.ui.icon \
    --theme dev.tonholo.composeicons.ui.theme.ComposeIconsTheme \
    -opt=true \
    <parent-path>/shield-halved-solid.svg
```

Input file: ![shield-halved-solid.svg](https://raw.githubusercontent.com/rafaeltonholo/svg-to-compose/refs/heads/main/samples/svg/shield-halved-solid.svg)

Output file: [ShieldSolid.svg.optimized.kt](https://raw.githubusercontent.com/rafaeltonholo/svg-to-compose/refs/heads/main/samples/ShieldSolid.svg.optimized.kt)

### Complex SVG File

#### Without Optimization

Command:

```console
s2c -o <app path>/app/src/main/java/dev/tonholo/composeicons/ui/icon/Illustration.kt \
    -p dev.tonholo.composeicons.ui.icon \
    --theme dev.tonholo.composeicons.ui.theme.ComposeIconsTheme \
    -opt=false \
    <parent-path>/illustration.svg
```

Input file: ![illustration.svg](https://raw.githubusercontent.com/rafaeltonholo/svg-to-compose/refs/heads/main/samples/svg/illustration.svg)

Output file: [Illustration.svg.nonoptimized.kt](https://raw.githubusercontent.com/rafaeltonholo/svg-to-compose/refs/heads/main/samples/Illustration.svg.nonoptimized.kt)

#### With Optimization

Command:

```console
s2c -o <app path>/app/src/main/java/dev/tonholo/composeicons/ui/icon/Illustration.kt \
    -p dev.tonholo.composeicons.ui.icon \
    --theme dev.tonholo.composeicons.ui.theme.ComposeIconsTheme \
    -opt=true \
    <parent-path>/illustration.svg
```

Input file: ![illustration.svg](https://raw.githubusercontent.com/rafaeltonholo/svg-to-compose/refs/heads/main/samples/svg/illustration.svg)

Output file: [Illustration.svg.optimized.kt](https://raw.githubusercontent.com/rafaeltonholo/svg-to-compose/refs/heads/main/samples/Illustration.svg.optimized.kt)

## License

This software is released under the terms of the [MIT License](https://github.com/rafaeltonholo/svg-to-compose/blob/main/LICENSE).
