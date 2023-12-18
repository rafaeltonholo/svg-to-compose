object NativePlatform: Platform {
    override val name: String = "Native"
}

actual fun getPlatform(): Platform = NativePlatform