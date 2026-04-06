# svg-to-compose-cli (CLI Module)

CRITICAL: MUST read and follow `AGENTS.md` in this directory for module-specific architecture and conventions.

This is the CLI entry point for the SVG-to-Compose tool. It depends on the core `svg-to-compose` library.

## Build Commands

```bash
# Native binary (host platform)
./gradlew -p modules/cli buildDebugMacosArm64
./gradlew -p modules/cli buildReleaseMacosArm64
./gradlew -p modules/cli buildReleaseLinuxx64
./gradlew -p modules/cli buildReleaseMingwx64

# JVM fat JAR
./gradlew -p modules/cli shadowJar

# Run locally
./s2c --help
```
