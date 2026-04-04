package dev.tonholo.s2c.website.shiki

import kotlinx.browser.document
import kotlinx.coroutines.await
import kotlinx.coroutines.suspendCancellableCoroutine
import org.w3c.dom.HTMLScriptElement
import org.w3c.dom.events.Event
import kotlin.coroutines.resume

@JsExport
@OptIn(ExperimentalJsExport::class)
class Shiki internal constructor(api: ShikiApi) : ShikiApi by api {
    @JsExport.Ignore
    suspend fun codeToHtml(code: String, options: dynamic): String = codeToHtmlPromise(code, options).await()

    companion object {
        private const val SHIKI_VERSION = "4.0.2"
        private const val SHIKI_SCRIPT_ID = "shiki-loader"
        private const val SHIKI_LOADED_EVENT = "shiki-module-loaded"

        lateinit var instance: Shiki

        operator fun invoke(api: ShikiApi) {
            if (::instance.isInitialized) return
            instance = Shiki(api)
            document.dispatchEvent(Event(SHIKI_LOADED_EVENT))
        }

        @JsExport.Ignore
        suspend fun initialize() = suspendCancellableCoroutine { continuation ->
            if (::instance.isInitialized) {
                continuation.resume(instance)
                return@suspendCancellableCoroutine
            }

            val globalThis = js("globalThis")
            if (globalThis.KotlinWrapperShiki == undefined) {
                globalThis.KotlinWrapperShiki = Shiki
            }

            document.addEventListener(
                SHIKI_LOADED_EVENT,
                callback = {
                    continuation.resume(instance)
                },
                false,
            )

            val isLoaderNotPresent =
                document.querySelector("script[id='$SHIKI_SCRIPT_ID']") == null
            if (isLoaderNotPresent) {
                (document.createElement("script") as HTMLScriptElement).apply {
                    id = SHIKI_SCRIPT_ID
                    type = "module"
                    textContent = """
                        |import * as shiki from 'https://esm.sh/shiki@$SHIKI_VERSION'
                        |import * as shikiTransformers from 'https://esm.sh/@shikijs/transformers@$SHIKI_VERSION'
                        |
                        |globalThis.shikiTransformers = shikiTransformers
                        |KotlinWrapperShiki.invoke(shiki)
                    """.trimMargin()
                }.also { script ->
                    document.body?.appendChild(script)
                }
            }
        }
    }
}
