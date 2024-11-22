package dev.tonholo.s2c.gradle.internal.cache

internal interface Cacheable {
    fun calculateHash(): Sha256Hash
}
