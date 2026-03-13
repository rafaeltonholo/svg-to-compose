# Migration Guide — Emitter Refactor

This guide covers the migration from the legacy `materialize()` API to the new
`CodeEmitter` pipeline introduced in the code generation refactor.

## Overview

The refactor separates **what to generate** (domain model) from **how to
generate it** (code emitter), enabling:

- Configurable formatting via `.editorconfig` or CLI flags
- Future pluggable output formats (Canvas, VectorPainter)
- Proper Logger dependency injection (no global singletons)

## Deprecation Timeline

| API                                   | Status                 | Removal            |
|---------------------------------------|------------------------|--------------------|
| `PathNodes.materialize()`             | `@Deprecated(WARNING)` | Next major version |
| `ImageVectorNode.materialize()`       | `@Deprecated(WARNING)` | Next major version |
| `IconFileContents.materialize()`      | `@Deprecated(WARNING)` | Next major version |
| `ImageParser.Factory.parse(): String` | `@Deprecated(WARNING)` | Next major version |

All deprecated APIs remain fully functional and delegate to the new emitter
internally.

## Migrating from `materialize()` to `CodeEmitter`

### Before

```kotlin
val contents: IconFileContents = // ... build from parsed SVG/AVG
val output: String = contents.materialize()
```

### After

```kotlin
import dev.tonholo.s2c.emitter.CodeEmitterFactory
import dev.tonholo.s2c.emitter.FormatConfig

val emitterFactory = CodeEmitterFactory(logger)
val emitter = emitterFactory.create(
    formatConfig = FormatConfig(indentSize = 4),
)
val output: String = emitter.emit(contents)
```

The `CodeEmitter.emit()` method accepts an `IconFileContents` and produces the
full Kotlin source file as a `String`.

## Migrating from `parse()` to `parseToModel()`

### Before

```kotlin
val parser = ImageParser.Factory(fileManager, logger)
val kotlinSource: String = parser.parse(file, iconName, config)
```

### After

```kotlin
val parser = ImageParser.Factory(fileManager, logger)
val model: IconFileContents = parser.parseToModel(file, iconName, config)

val emitter = CodeEmitterFactory(logger).create()
val kotlinSource: String = emitter.emit(model)
```

This separation lets you inspect or transform the `IconFileContents` model
before emission, and swap emitters for different output formats.

## FormatConfig and EditorConfig

### Automatic resolution

When `ParserConfig.formatConfig` is `null` (the default), the `Processor`
automatically resolves formatting from `.editorconfig` files by walking up from
the output directory. Supported properties:

| EditorConfig Property  | FormatConfig Field                   |
|------------------------|--------------------------------------|
| `indent_size`          | `indentSize` (default: 4)            |
| `indent_style`         | `indentStyle` (default: SPACE)       |
| `max_line_length`      | `maxLineLength` (default: 120)       |
| `insert_final_newline` | `insertFinalNewline` (default: true) |

### CLI flags

```bash
# Override indent size
s2c --indent-size 2 -p com.example -t MyTheme -o output/ input.svg

# Use tabs
s2c --indent-style tab -p com.example -t MyTheme -o output/ input.svg

# Disable .editorconfig resolution (use defaults)
s2c --no-editorconfig -p com.example -t MyTheme -o output/ input.svg
```

### Programmatic override

```kotlin
val config = ParserConfig(
    pkg = "com.example",
    theme = "MyTheme",
    optimize = true,
    // ...
    formatConfig = FormatConfig(
        indentSize = 2,
        indentStyle = IndentStyle.SPACE,
    ),
)
```

When `formatConfig` is explicitly set, `.editorconfig` resolution is skipped.

## OutputFormat

The `OutputFormat` enum currently has a single value:

- `IMAGE_VECTOR` — generates `ImageVector.Builder` Kotlin code (default)

Future versions will add `CANVAS` and `VECTOR_PAINTER` formats. Each format
will have its own `CodeEmitter` implementation, requiring no changes to domain
classes.

```kotlin
// Future usage
val emitter = emitterFactory.create(
    outputFormat = OutputFormat.CANVAS,
    formatConfig = FormatConfig(indentSize = 2),
)
```

## Logger Changes

Global top-level logger functions (`verbose()`, `debug()`, `warn()`, etc.) and
the `CommonLogger()` singleton have been removed from the domain and emitter
packages. Logger is now threaded via constructor injection or function
parameters throughout the codebase.

If you were importing these functions directly:

```kotlin
// Before
import dev.tonholo.s2c.logger.verbose
import dev.tonholo.s2c.logger.debug

verbose("message")
debug("message")
```

Use a `Logger` instance instead:

```kotlin
// After
val logger: Logger = // injected or passed as parameter
    logger.verbose("message")
logger.debug("message")
```
