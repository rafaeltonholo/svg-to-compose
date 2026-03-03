# svg-to-compose-gradle-plugin — Gradle Plugin

This module provides a Gradle plugin that automates SVG/AVG to Compose
`ImageVector` conversion as part of the build process.

Read [.ai/guidelines.md](../.ai/guidelines.md) first.

## Source Structure

```
src/main/kotlin/dev/tonholo/s2c/gradle/
├── Svg2ComposePlugin.kt             # Main plugin class (entry point)
├── dsl/                             # Plugin DSL
│   ├── SvgToComposeExtension.kt     # Extension configuration block
│   ├── source/                      # Source set configuration
│   └── parser/                      # Parser configuration DSL
├── tasks/                           # Gradle task definitions
│   └── worker/                      # Gradle Worker API implementations
├── internal/
│   ├── cache/                       # Incremental build cache integration
│   ├── inject/                      # Dependency injection
│   ├── logger/                      # Logging bridge
│   └── provider/                    # Gradle Provider-based value providers
└── annotations/                     # API stability annotations

src/test/                            # Integration tests (Gradle TestKit)
src/functionalTest/                  # Functional tests
```

## Key Concepts

- **Svg2ComposePlugin**: Registers the `svgToCompose` extension and conversion
  tasks.
- **SvgToComposeExtension**: DSL for users to configure input directories,
  output packages, and parser options.
- **Worker API**: Conversion runs via Gradle's Worker API for parallel, isolated
  execution.
- **Build cache**: Supports Gradle's incremental build and build cache for
  efficient re-builds.
- The plugin depends on `svg-to-compose` (the core library) for the actual
  conversion logic.

## Key Dependencies

- `com.android.tools.build:gradle` — Android plugin APIs (for Android project
  integration)
- `org.jetbrains.kotlin:kotlin-gradle-plugin` — Kotlin plugin APIs
- `projects.svgToCompose` — Core conversion library

## Build and Test

```bash
./gradlew :svg-to-compose-gradle-plugin:build
./gradlew :svg-to-compose-gradle-plugin:test
./gradlew :svg-to-compose-gradle-plugin:detekt
```

## Conventions

- Plugin DSL classes in `dsl/` define the public API surface — changes here are
  breaking changes.
- Internal implementation details go in `internal/` — these are not part of the
  public API.
- Use Gradle's `Provider` API for lazy configuration; avoid eagerly resolving
  values.
- Integration tests use Gradle TestKit — place test fixtures in
  `src/test/resources/`.
