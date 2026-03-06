package dev.tonholo.s2c.gradle.internal.provider

import org.gradle.api.provider.Property

fun <T : Any> Property<T>.setIfNotPresent(
    provider: Property<T>,
    defaultValue: T? = null,
) {
    if (!isPresent) {
        value(
            provider.orNull ?: defaultValue
        )
    }
}
