package dev.tonholo.s2c.gradle.internal.provider

import org.gradle.api.provider.Property

fun <T> Property<T>.setIfNotPresent(
    provider: Property<T>,
    defaultValue: T? = null,
) {
    if (!isPresent) {
        value(
            provider.orNull ?: defaultValue
        )
    }
}
