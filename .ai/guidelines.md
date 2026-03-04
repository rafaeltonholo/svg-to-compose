# SVG-to-Compose — AI Agent Guidelines

These guidelines MUST be followed at all times when working in this repository.

## Repository Overview

SVG-to-Compose is a Kotlin Multiplatform tool that converts SVG and Android
Vector Drawable (AVG/XML) files into Jetpack Compose `ImageVector` code. It
ships as:

1. **A CLI tool** (`svg-to-compose`) — native binaries for macOS, Linux,
   Windows, plus a JVM target.
2. **A Gradle plugin** (`svg-to-compose-gradle-plugin`) — automates conversion
   as part of the build.

**Current version**: defined in `app.properties` (`VERSION` key).

## Project Structure

```
.
├── AGENTS.md                          # This file (root)
├── .ai/
│   ├── guidelines.md                  # AI agent guidelines (you are here)
│   └── skills/                        # Reusable AI skill definitions
├── buildSrc/                          # Gradle convention plugins
├── config/detekt.yml                  # Detekt static analysis configuration
├── gradle/libs.versions.toml          # Dependency version catalog
├── playground/                        # Android demo app
├── playground-kmp/                    # KMP demo app
├── samples/                           # Example SVG/AVG input files
│   ├── svg/
│   └── avg/
├── svg-to-compose/                    # Core KMP library
└── svg-to-compose-gradle-plugin/      # Gradle plugin
```

## Build Commands

### Building

```bash
./gradlew build                        # Build everything
./gradlew :svg-to-compose:build        # Build only the core library
./gradlew :svg-to-compose-gradle-plugin:build  # Build only the Gradle plugin
```

### Testing

```bash
./gradlew test                         # Run tests (host platform)
./gradlew allTests                     # Run tests for all KMP targets
./gradlew cleanAllTests allTests       # Clean and run all tests (CI)
```

To run a specific test class:

```bash
./gradlew :svg-to-compose:allTests --tests "dev.tonholo.s2c.lexer.css.CssTokenizerTest"
```

### Static Analysis

```bash
./gradlew detekt                       # Run Detekt on all modules
./gradlew :svg-to-compose:detektMetadataCommonMain  # Detekt on core library
./gradlew :svg-to-compose-gradle-plugin:detekt      # Detekt on Gradle plugin
./gradlew mergeDetektReport            # Merge all Detekt reports
```

### Publishing

```bash
./gradlew publishAllToMavenLocal       # Publish to local Maven for testing
```

### Documentation

```bash
./gradlew dokkaHtml                    # Generate API docs
```

### CLI

```bash
./s2c --help                           # Show CLI usage
```

## Code Style and Quality

- **Kotlin code style**: Official (`kotlin.code.style=official` in
  `gradle.properties`).
- **Static analysis**: Detekt with strict rules (max issues = 0). Configuration
  in `config/detekt.yml`.
- **Max line length**: 120 characters.
- **Detekt must pass** before any PR can be merged. Run `./gradlew detekt`
  locally.
- **Tests must pass**: Run `./gradlew allTests` to verify across all targets.

### Key Detekt Rules

- Cognitive complexity threshold: 15
- Cyclomatic complexity threshold: 20
- Max method length: 120 lines
- Max class size: 600 lines
- Max parameters: checked (LongParameterList)
- Max return statements: 4
- `TODO`/`FIXME`/`STOPSHIP` comments must link to a GitHub issue

## Commit Conventions

Use [Conventional Commits](https://www.conventionalcommits.org/):

```
<type>(<scope>): <subject>
```

**Types**: `feat`, `fix`, `docs`, `refactor`, `perf`, `test`, `chore`

**Scopes** (optional): `cli`, `parser`, `gradle-plugin`, `build`, `ci`,
`readme`, etc.

**Rules**:

- Subject under ~72 characters, imperative mood.
- Reference GitHub issues when applicable (`Closes #123`).
- Keep non-functional changes in separate commits.

**Examples**:

- `feat(cli): add --opt flag to enable SVGO optimization`
- `fix(parser): handle path commands with scientific notation`

## Branch Naming

```
feat/<short-topic>
fix/<short-topic>
docs/<short-topic>
chore/<short-topic>
```

## Testing Guidelines

- **Framework**: `kotlin-test` (common source set).
- **Power Assert**: Enabled for `assert`, `assertTrue`, `assertEquals`,
  `assertNull` — provides rich failure messages.
- **Burst**: Used for parameterized tests.
- Tests live in `commonTest/` and mirror the source package structure.
- Add or update tests for any behavioral change.

## Module-Specific Guidelines

Before modifying code in a module, read its `AGENTS.md`:

| Module                         | Docs                                                                                |
|--------------------------------|-------------------------------------------------------------------------------------|
| `svg-to-compose`               | [svg-to-compose/AGENTS.md](../svg-to-compose/AGENTS.md)                             |
| `svg-to-compose-gradle-plugin` | [svg-to-compose-gradle-plugin/AGENTS.md](../svg-to-compose-gradle-plugin/AGENTS.md) |
| `buildSrc`                     | [buildSrc/AGENTS.md](../buildSrc/AGENTS.md)                                         |
| `playground`                   | [playground/AGENTS.md](../playground/AGENTS.md)                                     |
| `playground-kmp`               | [playground-kmp/AGENTS.md](../playground-kmp/AGENTS.md)                             |

## Key Architectural Decisions

- **Kotlin Multiplatform**: The core library targets JVM and native (macOS
  arm64/x64, Linux x64, Windows mingwX64). Platform-specific code goes in the
  respective source sets.
- **Convention Plugins**: All build configuration is centralized in `buildSrc/`
  as precompiled script plugins. Do not duplicate build logic in module
  `build.gradle.kts` files.
- **Typesafe Project Accessors**: Enabled via `TYPESAFE_PROJECT_ACCESSORS`
  feature preview. Use `projects.svgToCompose` instead of
  `project(":svg-to-compose")`.
- **Version Catalog**: All dependency versions are managed in
  `gradle/libs.versions.toml`.

## Multiplatform Constraints

- **Source Set Isolation**: Core logic MUST stay in `commonMain`. Do not
  introduce JVM-specific dependencies (like `java.io`, `java.nio`, or `javax.*`)
  into common code.
- **File I/O**: ALWAYS use **Square's Okio** (`okio.Path`, `okio.FileSystem`)
  for file operations. Native targets do not support `java.io.File`.
- **Platform-Specific Entry Points**: Use `jvmMain` or `nativeMain` only for
  genuinely platform-specific logic (e.g., CLI entry points, external process
  execution via `clikt`).
- **Memory Management**: Be mindful of Kotlin/Native's memory model. Avoid
  global state or singletons that might lead to freezing issues in native
  binaries.
- **Dependency Compatibility**: Before adding a library, verify it supports all
  current targets: `jvm`, `macosX64`, `macosArm64`, `linuxX64`, and `mingwX64`.

## CI/CD

- **PR checks** (`.github/workflows/pull_request.yml`): Builds native binaries
  on macOS/Linux/Windows, runs Detekt, runs all tests, and performs CLI
  integrity checks.
- **Releases** (`.github/workflows/release.yml`,
  `.github/workflows/release.v2.yml`): Build release binaries and create draft
  GitHub releases.
- Maven Central publication is handled by Gradle publish tasks and repository
  configuration, not by the release workflows above.
- All CI checks must pass before merging.

## Golden Samples

The `samples/` directory contains reference ("golden") files for regression testing.

### Structure

- **Source inputs**: `samples/svg/` (SVG files) and `samples/avg/` (AVG/XML files)
- **Golden outputs**: `samples/*.kt` files

### Naming Convention

```
<IconName>.<format>.<optimized|nonoptimized>.kt
```

Examples: `Illustration.svg.optimized.kt`, `ShieldSolid.avg.nonoptimized.kt`

### Comparing Against Golden Files

After modifying the parser or generator, regenerate output and diff against golden files:

```bash
./s2c -o /tmp/Test.kt -p dev.test samples/svg/attention-filled.svg
diff /tmp/Test.kt samples/AttentionFilled.svg.nonoptimized.kt
```

### Updating Golden Files

When a change intentionally alters output, regenerate and commit:

```bash
./s2c -o samples/Illustration.svg.nonoptimized.kt -p dev.tonholo.s2c.parser.ast samples/svg/illustration.svg
# Review the diff, then commit the updated .kt file
```

## Prerequisites

- JDK 11+ (17 recommended)
- Git
- IDE with Kotlin support (IntelliJ IDEA recommended)
