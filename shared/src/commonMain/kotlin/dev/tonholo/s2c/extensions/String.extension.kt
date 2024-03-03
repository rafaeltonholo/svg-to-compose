package dev.tonholo.s2c.extensions

private fun String.replaceDividers(): String {
    val pattern = "([_\\-. ])[a-zA-Z0-9]".toRegex()
    return replace(pattern) { it.value.last().uppercase() }
}

fun String.camelCase(): String = replaceDividers()
    .replaceFirstChar { it.lowercaseChar() }

fun String.pascalCase(): String = replaceDividers()
    .replaceFirstChar { it.uppercaseChar() }

fun String.indented(indentSize: Int) = " ".repeat(indentSize) + this
