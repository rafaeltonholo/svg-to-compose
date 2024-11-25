# SVG/XML to Compose Gradle Plugin
[![SVG to Compose Gradle Plugin Latest version](https://img.shields.io/maven-central/v/dev.tonholo.s2c/svg-to-compose-gradle-plugin?display_name=tag&label=svg-to-compose-gradle-plugin&logo=apachemaven)](https://central.sonatype.com/artifact/dev.tonholo.s2c/svg-to-compose-gradle-plugin)
[![LICENSE](https://img.shields.io/github/license/rafaeltonholo/svg-to-compose)](./LICENSE)

## Table of Contents

- [Motivation](#motivation)
- [Platform support](#platform-support)
- [Installation](#installation)
- [How It Works](#how-it-works) 
- [Configuration](#configuration)

## Motivation

The **SVG/XML to Compose** Gradle Plugin simplifies the process of converting
SVG and Android Vector Drawable (AVG/XML) files into Jetpack Compose
`ImageVector` properties. Manually converting vector assets can be tedious and
error-prone, especially in projects with a large number of icons. This plugin
automates the conversion process, ensuring consistency and saving valuable
development time.

By using the **SVG/XML to Compose** plugin, you can automate and customize the
integration of vector assets into your Compose projects, ensuring a more
efficient and error-free workflow.

## Platform support
| Platform             | Support  |
|----------------------|:--------:|
| Android              |    ✅    |
| Kotlin Multiplatform |    ✅    |

## Installation

The **SVG/XML to Compose** plugin is available on
[Maven Central](https://search.maven.org/). You can apply it to your project
using the Plugins DSL or include it via buildSrc or build conventions.

### Applying via Plugins DSL

Add the plugin to your module's `build.gradle.kts` file:

```kotlin
plugins {
    id("dev.tonholo.s2c") version "<latest-version>"
}
```

Ensure that Maven Central is included in your plugin repositories. In most
cases, Gradle includes Maven Central by default. If not, add the following to
your settings.gradle.kts or build.gradle.kts:

```kotlin
pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}
```

### Applying via buildSrc or Build Conventions

If you prefer to manage your plugins through buildSrc or a custom build
conventions plugin, you can include Svg2Compose as a dependency.

#### Using buildSrc

1. Create buildSrc Directory: If it doesn’t exist, create a buildSrc directory
   at the root of your project.
2. Add Dependencies: In buildSrc/build.gradle.kts, add the plugin as a
   dependency:

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("dev.tonholo.s2c:svg-to-compose-gradle-plugin:<latest-version>")
}
```

3. Apply the Plugin: In your module’s build.gradle.kts, apply the plugin using
   the plugins block:

```kotlin
plugins {
    id("dev.tonholo.s2c")
}
```

## How It Works

The plugin automates the conversion of SVG and Android Vector Drawable (AVG)
files into Jetpack Compose `ImageVector` objects. It scans the specified
directories, processes each icon according to your configuration, and generates
the corresponding Compose code.

When a Kotlin Compile task is initiated in your project, the plugin checks for
changes in the specified icons. Its built-in cache determines whether an icon
needs to be regenerated or deleted from the original folder to ensure effective
icon generation.

In addition, whenever a change is made to the plugin configuration, the cache is
invalidated to regenerate the icons reflecting the changes in the next build.

### Processing Flow

1. **Configuration Parsing**: The plugin reads your configurations defined in
   the `svgToCompose` extension.
2. **Icon Scanning**: It scans the specified directories for SVG and AVG files.
3. **Icon Processing**: Each icon is processed with the specified options (e.g.,
   optimization, minification).
4. **Code Generation**: Generates `ImageVector` objects in the specified
   package.
5. **Build Integration**: The generated code is integrated into your project
   during the build process.

## Configuration

After applying the plugin, configure it in your `build.gradle.kts` file using
the `svgToCompose` extension. This extension allows you to specify how the
SVG/AVG files should be processed and converted.

### Basic Configuration

```kotlin
svgToCompose {
    processor {
        val icons by creating {
            from(layout.projectDirectory.dir("src/main/resources/icons"))
            destinationPackage("com.example.app.ui.icons")
            optimize(true)
            recursive()
            icons {
                theme("com.example.app.ui.theme.AppTheme")
                minify()
                exclude(".*_exclude\\.svg".toRegex())
                mapIconNameTo { iconName ->
                    iconName.replace("_filled", "")
                }
            }
        }
    }
}
```

### Common Configuration

You can define common settings for all processors:

```kotlin
svgToCompose {
    processor {
        common {
            optimize(true)
            recursive()
            icons {
                minify()
            }
        }

        val outlinedIcons by creating {
            from(layout.projectDirectory.dir("src/main/resources/icons/outlined"))
            destinationPackage("com.example.app.ui.icons.outlined")
        }

        val filledIcons by creating {
            from(layout.projectDirectory.dir("src/main/resources/icons/filled"))
            destinationPackage("com.example.app.ui.icons.filled")
        }
    }
}
```

#### Configuration Options

**svgToCompose Extension**

- **processor**: Configures individual processors for different sets of icons.

**Processor Configuration**

- **from(Directory)**: Specifies the directory containing the SVG/AVG icons.
- **destinationPackage(String)**: The package where the generated `ImageVector`
  objects will reside.
- **optimize(Boolean)**: Enables optimization of the generated code.
- **recursive()**: Enables recursive search for icons in subdirectories.
- **maxDepth(Int)**: Sets the maximum depth for recursive search.
- **icons { ... }**: Configures icon-specific parsing options.

**Icon Parser Configuration**

Within the `icons` block, you can customize how icons are processed:

- **theme(String)**: Specifies the fully qualified name of the theme for
  previews.
- **minify()**: Removes comments and extra spaces to reduce code size.
- **noPreview()**: Disables the generation of preview images.
- **makeInternal()**: Adds the `internal` modifier to the generated
  `ImageVector`.
- **receiverType(String)**: Sets a receiver type for the `ImageVector` property.
- **addToMaterialIcons()**: Uses Material `Icons` as the receiver type.
- **mapIconNameTo((String) -> String)**: Customizes icon names using a mapping
  function.
- **exclude(vararg Regex)**: Excludes icons based on filename patterns.
- **persist()**: Persists generated files in the source set (use with caution).

### Enabling Parallel Processing (Experimental)

You can enable experimental parallel processing to speed up icon generation:

```kotlin
svgToCompose {
    processor {
        @OptIn(dev.tonholo.s2c.annotations.ExperimentalParallelProcessing)
        useParallelism(parallelism = 4)
        // Processor configurations...
    }
}
```

**Note**: This feature is experimental and may have unexpected behavior.

### Persistent Generation

> [!WARNING]
> This is a delicate operation. Use `persist()` only if you're sure
> you want to overwrite existing files in your source set.

To persist generated icons in your source code (overwriting existing files):

```kotlin
svgToCompose {
    processor {
        val persistentIcons by creating {
            // Processor configurations...
            icons {
                @OptIn(dev.tonholo.s2c.annotations.DelicateSvg2ComposeApi)
                persist()
            }
        }
    }
}
```
