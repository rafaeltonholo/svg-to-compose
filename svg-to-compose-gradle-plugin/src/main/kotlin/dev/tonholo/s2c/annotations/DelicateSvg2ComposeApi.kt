package dev.tonholo.s2c.annotations

@RequiresOptIn(
    message = "This is a delicate API and its use requires care. Make sure you fully read and understand " +
        "documentation of the declaration that is marked as a delicate API.",
    level = RequiresOptIn.Level.WARNING,
)
@Retention(AnnotationRetention.BINARY)
annotation class DelicateSvg2ComposeApi
