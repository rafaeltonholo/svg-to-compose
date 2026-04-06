# SVG-to-Compose Architecture & Pipeline

This document describes the data flow and architectural components of the
`svg-to-compose` tool.

## Module Layout

The tool is split across two main modules:

- **`svg-to-compose`** (core library) - parsing, conversion, and code generation
  logic. This is a KMP library with no executable entry points.
- **`modules/cli`** (CLI module) - the Clikt command-line entry point. Depends on
  the core library and produces native executables (macOS arm64, Linux x64,
  Windows mingwX64) plus a JVM fat JAR for platforms without native support.

The Gradle plugin (`svg-to-compose-gradle-plugin`) also depends on the core
library.

## Data Flow Pipeline

The conversion process follows a linear pipeline managed by the `Processor`
class:

1. **Input Identification**: `Processor.kt` receives a file or directory path.
   It identifies files with `.svg` or `.xml` (AVG) extensions.
2. **Optimization (Optional)**: If `--optimize` is true, the `Optimizer.Factory`
   invokes external tools (`svgo` for SVG, `avocado` for AVG) to simplify paths
   and remove metadata.
3. **Parsing**:
    - The `ImageParser.Factory` selects either `SvgParser` or `AvgParser`.
    - **SvgParser**: Uses `ksoup` for XML parsing. It handles `<path>`,
      `<circle>`, `<rect>`, `<group>`, and style attributes (including basic
      CSS).
    - **AvgParser**: Parses Android Vector Drawable XML files.
4. **Intermediate Representation (IR)**: Both parsers map elements to domain
   models in `dev.tonholo.s2c.domain`. This is a format-agnostic representation
   of shapes, paths, and groups.
5. **Geometry Processing**:
    - **Path Parsing**: SVG path data (the `d` attribute) is tokenized and
      converted into `PathInstruction` objects.
    - **Transformations**: Affine transformations (scale, rotate, translate,
      matrix) are applied to path data to "bake" them into the coordinates when
      possible, or mapped to Compose `Group` transformations.
    - **Bounds Calculation**: Bounding boxes are calculated for preview
      generation.
6. **Template Resolution (Optional)**: If an `s2c.template.toml` file is
   provided (via `--template` flag, Gradle DSL, or auto-discovered by walking
   up from the output directory), a `TemplateConfig` is parsed and used to
   customize the code generation output. See `docs/TEMPLATES.md` for the full
   schema.
7. **Code Generation**: The IR is passed to `CodeEmitter` (either the default
   `ImageVectorEmitter` or `TemplateEmitter` when a template is active), which
   generates the final Kotlin code. The `TemplateEmitter` resolves
   `${namespace:key}` placeholders against icon/path/group variables, template
   fragments, and import definitions.

## Key Components

- **Processor**: The orchestrator. Handles file I/O, configuration, and the
  high-level loop.
- **Lexer/Parser (CSS)**: A custom tokenizer and AST parser for handling CSS
  within SVG `<style>` tags or `style` attributes.
- **Geom**: Contains the math for path commands (Move, Line, Curve, Arc) and
  coordinate transformations.
- **IO**: Abstractions for file writing and temporary file management using
  `okio`.

## Known Limitations (Non-Goals)

The following features are currently unsupported or have limited support:

- **SVG Filters**: `<filter>`, `feGaussianBlur`, etc., are ignored.
- **Masking & Clipping**: Complex `<clipPath>` or `<mask>` elements may not
  render correctly in the generated `ImageVector`.
- **Animations**: Neither SVG `<animate>` nor AVG `<animated-vector>` are
  supported for conversion to Compose animations. Only static `ImageVector`s are
  generated.
- **External Resources**: External CSS files or linked images within SVGs are
  not supported.
- **Advanced CSS**: Only a subset of CSS selectors and properties relevant to
  vector graphics (fill, stroke, stop-color) are parsed.
- **Text**: The `<text>` element is not currently converted to Compose text
  paths.

## Technical Constraints

- **Kotlin Multiplatform**: All core logic must reside in `commonMain`.
- **No java.io**: Use `okio` for all file operations to ensure compatibility
  with Native targets (macOS, Linux, Windows).
- **Float vs Double**: Most coordinate math is currently being migrated from
  `Float` to `Double` for better precision.
