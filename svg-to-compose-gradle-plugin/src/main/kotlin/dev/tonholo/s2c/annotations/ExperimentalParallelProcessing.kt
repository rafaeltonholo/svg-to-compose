package dev.tonholo.s2c.annotations

@RequiresOptIn(
    message = "The icon processing in parallel is experimental and might contains bugs.",
    level = RequiresOptIn.Level.WARNING,
)
@Retention(AnnotationRetention.BINARY)
annotation class ExperimentalParallelProcessing
