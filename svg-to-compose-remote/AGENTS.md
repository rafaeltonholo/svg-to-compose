# svg-to-compose-remote — Remote Sources Library

Kotlin Multiplatform module that resolves remote icon sources for
SVG-to-Compose: direct URLs, ZIP archives, and icon fonts. It exists so that
networking code stays out of the core `svg-to-compose` module and out of the
JVM-only Gradle plugin.

Read [.ai/guidelines.md](../.ai/guidelines.md) first.

## Platform Targets

Same as the core module: JVM, macOS (arm64), Linux (x64), Windows (mingwX64),
JS, and WasmJS.

## Source Structure

```
src/
├── commonMain/    # Source abstraction, resolvers, shared logic
├── jvmMain/       # OkHttp transport, Okio openZip()
├── nativeMain/    # Ktor transport, Okio openZip()
├── jsMain/        # Ktor JS transport, JSZip for ZIP extraction
└── wasmJsMain/    # JSZip for ZIP extraction
```

## Dependency Rules

- **JVM HTTP**: OkHttp only. The Gradle plugin consumes this module on its
  classpath, and Ktor's coroutine dependency conflicts with Gradle's bundled
  Kotlin runtime. OkHttp's synchronous API avoids that.
- **Native/JS HTTP**: Ktor client.
- **File I/O and ZIP**: Okio (`openZip()` on JVM and native); JSZip on JS and
  WasmJS.
- **Core dependency**: this module depends on `svg-to-compose` (`api`), never
  the other way around. Core must stay free of networking dependencies.

## Verification

```bash
./gradlew :svg-to-compose-remote:build
./gradlew :svg-to-compose-remote:allTests
./gradlew :svg-to-compose-remote:detekt
```

Part of the Remote Sources epic
([#264](https://github.com/rafaeltonholo/svg-to-compose/issues/264)).
