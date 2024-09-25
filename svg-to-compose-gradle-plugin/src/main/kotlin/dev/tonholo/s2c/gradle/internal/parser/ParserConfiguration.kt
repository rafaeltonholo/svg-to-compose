package dev.tonholo.s2c.gradle.internal.parser

interface ParserConfiguration {
    var theme: String
    var optimize: Boolean
    var receiverType: String?
    var addToMaterial: Boolean
    var noPreview: Boolean
    var makeInternal: Boolean
    var minified: Boolean
}
