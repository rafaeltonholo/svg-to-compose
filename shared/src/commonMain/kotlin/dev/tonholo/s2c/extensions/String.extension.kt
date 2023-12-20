package dev.tonholo.s2c.extensions

private fun String.replaceDividers(): String {
    val pattern = "([_\\- ])[a-zA-Z]".toRegex()
    return replace(pattern) { it.value.last().uppercase() }
}

fun String.camelCase(): String = replaceDividers()
    .replaceFirstChar { it.lowercaseChar() }

fun String.pascalCase(): String = replaceDividers()
    .replaceFirstChar { it.uppercaseChar() }