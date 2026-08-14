# svg-to-compose-remote (Remote Sources Library)

CRITICAL: MUST read and follow `AGENTS.md` in this directory for module-specific architecture and conventions.

This KMP module resolves remote icon sources (URL, ZIP, icon fonts) for the CLI and Gradle plugin. It depends on the core `svg-to-compose` library.

```bash
./gradlew :svg-to-compose-remote:build
./gradlew :svg-to-compose-remote:allTests
./gradlew :svg-to-compose-remote:detekt
```
