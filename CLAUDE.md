# SVG-to-Compose — Claude Code Context

CRITICAL: MUST read and follow `.ai/guidelines.md` at all times. It contains
code style, architecture, testing, and commit conventions for this repository.

Read `AGENTS.md` for repository overview, module map, verification workflow, and
skills.

## Quick Reference

```bash
# Build
./gradlew build                        # Build everything
./gradlew :svg-to-compose:build        # Core library only
./gradlew -p modules/cli build         # CLI module (native + JVM)
./gradlew -p modules/cli shadowJar     # CLI JVM fat JAR only

# Test
./gradlew allTests                     # All KMP targets
./gradlew :svg-to-compose:allTests --tests "fully.qualified.TestClass"

# Static analysis
./gradlew detekt                       # Must pass (max issues = 0)

# CLI
./s2c --help                           # Run the CLI tool

# Publish locally
./gradlew publishAllToMavenLocal
```

## Key Constraints

- **Kotlin Multiplatform** — core logic in `commonMain`, use Okio for file I/O,
  no `java.io` in common code.
- **Convention plugins** - build config lives in `build-logic/`, don't duplicate in
  modules.
- **Detekt strict mode** — zero tolerance. Run before committing.
- **Conventional Commits** — `<type>(<scope>): <subject>` (see
  `.ai/guidelines.md`).

## Module CLAUDE.md Files

Each module has its own `CLAUDE.md` referencing module-specific `AGENTS.md`.
