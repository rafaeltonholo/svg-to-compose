---
name: kotlin-gradle-standalone-plugin
description: Develop and maintain standalone Gradle plugins implemented in Kotlin. Use when tasks involve creating a plugin module, defining plugin IDs, adding extensions/tasks, wiring lazy configuration with Provider APIs, writing Gradle TestKit coverage, or preparing plugin publication and compatibility updates.
---

# Kotlin Gradle Standalone Plugin

## Overview

Build production-grade standalone Gradle plugins in Kotlin with clear public DSL, lazy configuration, and testable task behavior. Prefer stable plugin APIs and Gradle TestKit for behavioral verification.

## Core Workflow

1. Confirm scope: new plugin, feature change, bug fix, or publishing update.
2. Define plugin surface first:
   - Plugin ID(s)
   - Public extension DSL
   - Registered tasks and conventions
3. Implement plugin wiring with lazy configuration:
   - Use `Property<T>`, `DirectoryProperty`, `RegularFileProperty`, `ListProperty<T>`.
   - Avoid eager `get()` during configuration phase.
4. Keep API boundaries explicit:
   - Public DSL under dedicated package (e.g., `dsl/`).
   - Internal implementation in `internal/`.
5. Add/adjust tests:
   - Unit tests for pure logic.
   - Gradle TestKit tests for plugin behavior against sample builds.
6. Run narrow verification first, then broader checks.

## Implementation Rules

- Use `Plugin<Project>` entrypoint with small `apply` body.
- Register tasks using `tasks.register(...)`, not `tasks.create(...)`.
- Configure tasks via providers (`map`, `flatMap`, `convention`) rather than immediate value resolution.
- Prefer deterministic task inputs/outputs and annotate task properties correctly.
- Use typed extensions for user-facing configuration.
- Do not leak internal types through public DSL.

## Testing Rules

- Verify plugin behavior through real Gradle builds with TestKit.
- Assert task graph effects and generated outputs, not internal implementation details.
- Cover:
  - default configuration behavior
  - user-overridden DSL values
  - failure mode for invalid config
- Keep fixture builds minimal and explicit.

See these references as needed:
- [references/gradle-plugin-kotlin-reference.md](references/gradle-plugin-kotlin-reference.md)
- [references/gradle-worker-api.md](references/gradle-worker-api.md)
- [references/task-caching.md](references/task-caching.md)

## Verification Commands

Run the smallest relevant command first:

```bash
./gradlew :<plugin-module>:test --tests "<FQN>"
./gradlew :<plugin-module>:functionalTest
./gradlew :<plugin-module>:check
./gradlew check
```

## Completion Criteria

- Plugin ID, DSL, and task behavior are coherent and documented in code.
- Configuration is lazy and cache-friendly.
- TestKit coverage validates expected behavior and failure paths.
- Targeted checks pass for the changed plugin module.
