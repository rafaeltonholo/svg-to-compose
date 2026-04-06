## Repository Overview

- **Primary language**: Kotlin
- **Build system**: Gradle (Kotlin DSL)
- **Repository structure**: Multi-module Gradle project
- **Architecture & Pipeline**: [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md)

> CRITICAL:
> All Agents MUST follow the `.ai/guidelines.md` at all times for code style,
> architecture, and design principles.

## Module-Specific Context

| Module                         | Description                                                                | AGENTS.md                                                                        |
|--------------------------------|----------------------------------------------------------------------------|----------------------------------------------------------------------------------|
| `svg-to-compose`               | Core KMP library - SVG/AVG parsing and Compose ImageVector code generation | [svg-to-compose/AGENTS.md](svg-to-compose/AGENTS.md)                             |
| `modules/cli`                  | CLI entry point (Clikt), produces native executables (composite build)     | [modules/cli/AGENTS.md](modules/cli/AGENTS.md)                                   |
| `svg-to-compose-gradle-plugin` | Gradle plugin for automated SVG/AVG to Compose conversion in builds        | [svg-to-compose-gradle-plugin/AGENTS.md](svg-to-compose-gradle-plugin/AGENTS.md) |
| `build-logic`                  | Convention plugins and shared build logic (included build)                 | [build-logic/AGENTS.md](build-logic/AGENTS.md)                                   |
| `playground`                   | Android demo app                                                           | [playground/AGENTS.md](playground/AGENTS.md)                                     |
| `playground-kmp`               | Kotlin Multiplatform demo app                                              | [playground-kmp/AGENTS.md](playground-kmp/AGENTS.md)                             |
| `samples`                      | Example SVG and AVG files for testing                                      | -                                                                                |

## The `s2c` Wrapper Script

The root `./s2c` script is the primary entry point for manual testing and local
execution. It resolves binaries from `modules/cli/build/bin/`.

- **Native-first**: On supported platforms (macOS arm64, Linux x64, Windows
  mingwX64), the script uses the native binary (`.kexe`/`.exe`).
- **JVM fallback**: On unsupported native platforms (e.g. macOS x64), the script
  falls back to `java -jar` using the Shadow fat JAR built by the CLI module.
- **Auto-Build**: If binaries are missing, the script invokes Gradle to build
  them (`./gradlew -p modules/cli <task>`).
- **Auto-Download**: If not in a project directory, it downloads pre-built
  binaries from GitHub.
- **Debugging**: Use `./s2c --debug` or `./s2c --verbose` to see detailed logs.
- **Upgrade**: Use `./s2c --upgrade` to force a rebuild or re-download.

## Verification Workflow

After making changes to the parser or generator, perform an **Empirical
Validation**:

1. **Build**: Run `./gradlew -p modules/cli buildDebugMacosarm64` (or the
   appropriate platform target).
2. **Generate**: Use the `./s2c` script on a sample file:
   ```bash
   ./s2c -o output/TestIcon.kt -p dev.test samples/svg/attention-filled.svg
   ```
3. **Compare**: Diff the generated `TestIcon.kt` against the original "Golden"
   sample in `samples/` to ensure no unexpected changes occurred in formatting
   or logic.
4. **Test**: Run `./gradlew :svg-to-compose:allTests`.

## Skills

| Skill                                                                                  | Description                                                                                                                                                          |
|----------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [SVG Spec, Paths & Transforms](.ai/skills/svg-spec/SKILL.md)                           | SVG/AVG specification references, path commands, affine transforms, gradients, CSS styling, and W3C links                                                            |
| [Unit Test Author](.ai/skills/unit-test-author/SKILL.md)                               | Creates and updates Kotlin unit tests following this repository's KMP testing conventions, including Burst parameterized patterns and targeted Gradle execution.     |
| [Kotlin Gradle Standalone Plugin](.ai/skills/kotlin-gradle-standalone-plugin/SKILL.md) | Develops standalone Gradle plugins in Kotlin with lazy Provider-based configuration, typed DSL/extensions, TestKit coverage, and module-level verification workflow. |
| [Self-Review](.ai/skills/self-review/SKILL.md)                                        | Performs a thorough self-review of pending changes against project conventions, checking code quality, tests, Detekt, and golden samples before commit/PR.            |

## Recommended Tools & MCP

- **`diff`**: Essential for comparing generated code output with samples.
- **`grep`**: Use to quickly find element handlers in `SvgParser.kt` or
  `AvgParser.kt`.
- **MCP Suggestion**: If available, use an MCP tool that can render PNGs from
  SVGs to visually verify input files before parsing.
