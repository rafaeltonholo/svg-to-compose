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
├── jvmAndNativeMain/  # Okio openZip()
├── jsMain/            # JSZip for ZIP extraction
└── wasmJsMain/        # ZIP not implemented yet (throws)
```

`jvmAndNative` is the only custom group and it is named after platforms. Never
add a group named after a library.

## Dependency Rules

- **HTTP**: Ktor for every target, one `KtorHttpTransport` in `commonMain`.
  Platform source sets add nothing but an engine: `ktor-client-okhttp` on JVM,
  CIO on apple and linux, WinHttp on mingw, and the JS engine that already
  ships inside `ktor-client-core` for JS and WasmJS. The client is built with
  the no-arg `HttpClient { }` so the engine comes from discovery. There is no
  `expect fun createHttpTransport`.
- **Engine failures**: never match them by type. Each engine reports a failed
  connection differently: `IOException` on CIO and OkHttp,
  `IllegalStateException` on WinHttp, a bare `kotlin.Error` on the JS engines,
  and `JsError` on the WasmJS browser path. `KtorHttpTransport` wraps anything
  that escapes a request call (except `CancellationException`) in
  `HttpTransportException`, so the common retry layer sees every failure on
  every target. An earlier type-matching version passed on macOS and Linux and
  failed on Windows.
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
