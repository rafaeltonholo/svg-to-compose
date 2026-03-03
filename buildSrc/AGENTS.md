# buildSrc — Convention Plugins

This module contains Gradle convention plugins that centralize and standardize
build configuration across all modules.

Read [.ai/guidelines.md](../.ai/guidelines.md) first.

## Overview

Convention plugins are precompiled Kotlin script plugins (`.gradle.kts` files in
`src/main/kotlin/`). They enforce consistent configuration for Kotlin
Multiplatform, testing, static analysis, documentation, and publishing.

## Plugin Hierarchy

```
dev.tonholo.s2c.conventions.common          # Base: detekt, app properties, group/version
├── dev.tonholo.s2c.conventions.detekt      # Detekt static analysis setup
│
dev.tonholo.s2c.conventions.kmp             # KMP targets, BuildConfig generation
├── extends: common, publication
│
dev.tonholo.s2c.conventions.testing         # kotlin-test, Power Assert, Burst
├── extends: kmp
│
dev.tonholo.s2c.conventions.publication     # Maven publishing (VannikTech)
dev.tonholo.s2c.conventions.dokka           # Dokka API documentation
dev.tonholo.s2c.conventions.gradle.plugin   # Gradle plugin-specific conventions
├── extends: common
```

## Key Files

| File                                                          | Purpose                                    |
|---------------------------------------------------------------|--------------------------------------------|
| `dev.tonholo.s2c.conventions.common.gradle.kts`               | Base convention applied to all modules     |
| `dev.tonholo.s2c.conventions.kmp.gradle.kts`                  | Kotlin Multiplatform target configuration  |
| `dev.tonholo.s2c.conventions.testing.gradle.kts`              | Test framework setup (Power Assert, Burst) |
| `dev.tonholo.s2c.conventions.detekt.gradle.kts`               | Detekt linting integration                 |
| `dev.tonholo.s2c.conventions.publication.gradle.kts`          | Maven Central publishing                   |
| `dev.tonholo.s2c.conventions.dokka.gradle.kts`                | Dokka documentation generation             |
| `dev.tonholo.s2c.conventions.gradle.plugin.gradle.kts`        | Conventions for Gradle plugin modules      |
| `dev/tonholo/s2c/conventions/AppProperties.kt`                | Loads `app.properties` (version, group)    |
| `dev/tonholo/s2c/conventions/VersionCatalogs.kt`              | Version catalog access utilities           |
| `dev/tonholo/s2c/conventions/kmp/targets/KmpTargets.kt`       | KMP target definitions                     |
| `dev/tonholo/s2c/conventions/detekt/DetektConfig.kt`          | Detekt configuration constants             |
| `dev/tonholo/s2c/conventions/detekt/MergeReports.kt`          | Detekt report merging task                 |
| `dev/tonholo/s2c/conventions/publication/PublicationTasks.kt` | Publish-all task registration              |

## Conventions When Modifying

- Do NOT duplicate build logic in module-level `build.gradle.kts` files. Add
  shared logic here.
- Convention plugins depend on plugins declared in `buildSrc/build.gradle.kts` —
  check that file when adding new plugin dependencies.
- Version catalog (`gradle/libs.versions.toml`) is the single source of truth
  for dependency versions.
- `app.properties` at the repository root defines `VERSION` and `GROUP` — read
  by `AppProperties.kt`.

## Build

```bash
# buildSrc is built automatically by Gradle before the main build.
# To verify convention plugins compile:
cd buildSrc && ../gradlew build
```
