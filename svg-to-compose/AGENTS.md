# svg-to-compose — Core Library

This is the main Kotlin Multiplatform library that parses SVG and Android Vector
Drawable (AVG/XML) files and generates Jetpack Compose `ImageVector` Kotlin
code.

Read [.ai/guidelines.md](../.ai/guidelines.md) first.

## Platform Targets

- **JVM**: Java 8 bytecode
- **Native**: macOS (arm64, x64), Linux (x64), Windows (mingwX64)

## Source Structure

```
src/
├── commonMain/kotlin/dev/tonholo/s2c/
│   ├── Processor.kt                 # Main entry point — orchestrates parsing and code generation
│   ├── parser/                      # File parsing (SVG, AVG)
│   │   └── ast/css/                 # CSS AST parsing
│   ├── lexer/css/                   # CSS tokenizer
│   │   ├── CssTokenizer.kt          # Tokenizes CSS input
│   │   ├── CssTokenKind.kt          # Token type definitions
│   │   └── token/consumer/          # Token consumer pattern
│   ├── domain/                      # Domain models
│   │   ├── svg/                     # SVG element models (paths, groups, gradients, transforms)
│   │   ├── avg/                     # Android Vector Drawable models
│   │   ├── compose/                 # Compose ImageVector models
│   │   ├── builder/                 # ImageVector code builders
│   │   └── delegate/                # Delegate patterns
│   ├── geom/                        # Geometry and math
│   │   ├── transform/               # Affine transformations
│   │   ├── bounds/                  # Bounding box calculations
│   │   └── path/                    # Path data operations
│   ├── io/                          # File I/O abstractions
│   ├── extensions/                  # Kotlin extension functions
│   ├── error/                       # Error types
│   ├── logger/                      # Logging abstraction
│   └── command/                     # External command execution
├── commonTest/kotlin/dev/tonholo/s2c/
│   ├── lexer/css/                   # CSS tokenizer tests
│   ├── geom/bounds/                 # Bounds calculation tests
│   ├── domain/svg/                  # SVG model tests
│   └── parser/ast/                  # AST parser tests
├── jvmMain/                         # JVM-specific implementations
└── nativeMain/                      # Native-specific (CLI entry points)
    ├── appleMain/                   # macOS
    ├── linuxMain/                   # Linux
    └── mingwMain/                   # Windows
```

## Key Concepts

- **Processor** (`Processor.kt`): The main orchestrator. Reads input files,
  delegates to parsers, and writes generated Compose code.
- **Domain models** map SVG/AVG elements to an intermediate representation,
  which is then converted to Compose `ImageVector` builder calls.
- **CSS lexer/parser**: Handles inline and embedded CSS styles in SVG files.
- **Geometry module**: Handles path transformations, bounding box calculations,
  and coordinate math.

## Key Dependencies

- `com.fleeksoft.ksoup` — XML/HTML parsing (multiplatform)
- `com.squareup.okio` — File I/O (multiplatform)
- `com.github.ajalt.clikt` — CLI framework (native targets only)

## Build and Test

```bash
./gradlew :svg-to-compose:build
./gradlew :svg-to-compose:allTests
./gradlew :svg-to-compose:allTests --tests "dev.tonholo.s2c.lexer.css.CssTokenizerTest"
./gradlew :svg-to-compose:detektMetadataCommonMain
```

## Conventions

- Common code goes in `commonMain`. Only put genuinely platform-specific code in
  `jvmMain`/`nativeMain`.
- Tests go in `commonTest` unless they require platform-specific APIs.
- Use `okio` for all file I/O — do not use `java.io.File` in common code.
- Follow the existing package structure when adding new features.
