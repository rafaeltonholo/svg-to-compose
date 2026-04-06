# playground-kmp — Kotlin Multiplatform Demo App

This module is a Kotlin Multiplatform sample app used to validate and
demonstrate generated Compose vectors in a KMP setup.

Read [.ai/guidelines.md](../.ai/guidelines.md) first.

## Purpose

- Demonstrate multiplatform usage patterns for generated vector code.
- Provide a practical integration target for manual verification.

## Conventions

- Keep sample code concise and focused on usage patterns.
- Do not move core parser/converter behavior into this module.
- Keep shared build behavior in `build-logic` convention plugins.

## Useful Commands

```bash
./gradlew :playground-kmp:assemble
./gradlew :playground-kmp:test
```
