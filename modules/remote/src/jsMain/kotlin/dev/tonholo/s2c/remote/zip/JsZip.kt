package dev.tonholo.s2c.remote.zip

import org.khronos.webgl.Uint8Array
import kotlin.js.Promise

@JsModule("jszip")
@JsNonModule
internal external class JsZip {
    fun forEach(action: (relativePath: String, entry: JsZipEntry) -> Unit)

    companion object {
        fun loadAsync(data: Uint8Array): Promise<JsZip>
    }
}

internal external interface JsZipEntry {
    val name: String
    val dir: Boolean

    fun async(type: String): Promise<Uint8Array>
}
