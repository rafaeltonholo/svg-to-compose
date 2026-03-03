# playground — Android Demo App

This module is an Android sample app used to manually validate generated Compose
vectors and demonstrate library usage.

Read [.ai/guidelines.md](../.ai/guidelines.md) first.

## Purpose

- Demonstrate integration of generated `ImageVector` code in a real Android app.
- Provide a quick smoke-test target for UI/manual checks.

## Conventions

- Keep this module focused on demonstration code. Core parsing/conversion logic
  belongs in `svg-to-compose`.
- Do not introduce shared build logic here. Put reusable build configuration in
  `buildSrc` convention plugins.
- Prefer minimal, readable sample code over production abstractions.

## Useful Commands

```bash
./gradlew :playground:assemble
./gradlew :playground:lint
./gradlew :playground:test
```
