# svg-to-compose-remote — Remote Sources Library

Kotlin Multiplatform module that resolves remote icon sources for
SVG-to-Compose: direct URLs, ZIP archives, and icon fonts. It exists so that
networking code stays out of the core `svg-to-compose` module.

Read [.ai/guidelines.md](../../.ai/guidelines.md) first.

## Platform Targets

Same as the core module: JVM, macOS (arm64), Linux (x64), Windows (mingwX64),
JS, and WasmJS.

## Source Structure

```text
src/
├── commonMain/        # Sources, resolvers, and the single Ktor HttpTransport
├── jvmAndNativeMain/  # Okio openZip(), no-op engine failure translation
├── webMain/           # JS/WasmJS engine failure translation
├── jsMain/            # JSZip for ZIP extraction
└── wasmJsMain/        # ZIP not implemented yet (throws)
```

Only platform groups are declared. `jvmAndNative` is the one custom group;
`web` comes from the default hierarchy template. Never add a group named after
a library.

## Dependency Rules

- **HTTP**: Ktor for every target, one `KtorHttpTransport` in `commonMain`.
  Platform source sets add nothing but an engine: `ktor-client-okhttp` on JVM,
  CIO on apple and linux, WinHttp on mingw, and the JS engine that already
  ships inside `ktor-client-core` for JS and WasmJS. The client is built with
  the no-arg `HttpClient { }` so the engine comes from discovery. There is no
  `expect fun createHttpTransport`.
- **Engine failures**: translated to `HttpTransportException` at the transport
  boundary so the common retry layer works on every target. Ktor on JS and
  WasmJS throws a bare `kotlin.Error` instead of an `IOException`, which is the
  only reason `translateEngineFailure` is expect/actual; JVM and native already
  throw `kotlinx.io.IOException`, so their actual returns `null`.
- **Large downloads**: use `HttpTransport.download` (streams to an okio
  `Sink`); `get` buffers the whole body and is only for small responses.
- **File I/O and ZIP**: Okio (`openZip()` on JVM and native); JSZip on JS only.
- **WasmJS ZIP**: stubbed and throws. Not a platform limitation. JSZip works
  through npm on wasm, but the port needs `JsAny` externals and real byte
  copies, and `wasmJsNodeTest` is switched off repo-wide by #243, so nothing on
  wasm can be verified yet.
- **Core dependency**: this module depends on `svg-to-compose` (`api`), never
  the other way around. Core must stay free of networking dependencies.
- **DI**: bindings live in `RemoteBindings`, contributed to `AppScope`. JS and
  WasmJS graphs must include the container explicitly because Metro hint
  generation is disabled for those platforms (KT-82395).

## Verification

```bash
./gradlew :svg-to-compose-remote:build
./gradlew :svg-to-compose-remote:allTests
./gradlew :svg-to-compose-remote:detekt
```

Part of the Remote Sources epic
([#264](https://github.com/rafaeltonholo/svg-to-compose/issues/264)).
