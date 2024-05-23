actual fun getPlatform(): Platform = NativePlatform

object NativePlatform: Platform {
    override val name: String = "Native"
}
