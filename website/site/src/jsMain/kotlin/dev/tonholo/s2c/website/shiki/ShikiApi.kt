package dev.tonholo.s2c.website.shiki

import kotlin.js.Promise

external interface ShikiApi {
    @JsName("codeToHtml")
    fun codeToHtmlPromise(code: String, options: dynamic): Promise<String>
}

fun codeToHtmlOptions(
    lang: String,
    isDark: Boolean,
    lightTheme: String,
    darkTheme: String,
    colorReplacements: Map<String, String>? = null,
): dynamic {
    val options = js("({})")
    options.lang = lang
    options.theme = if (isDark) darkTheme else lightTheme
    if (colorReplacements != null) {
        val replacements = js("({})")
        for ((from, to) in colorReplacements) {
            replacements[from] = to
        }
        options.colorReplacements = replacements
    }
    return options
}
