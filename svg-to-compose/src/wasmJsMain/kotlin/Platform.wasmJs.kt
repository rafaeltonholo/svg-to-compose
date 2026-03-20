/** Returns the WasmJs platform implementation. */
actual fun getPlatform(): Platform = object : Platform {
    override val name: String = "WasmJs"
}
