/** Returns the JS platform implementation. */
actual fun getPlatform(): Platform = object : Platform {
    override val name: String = "JS"
}
