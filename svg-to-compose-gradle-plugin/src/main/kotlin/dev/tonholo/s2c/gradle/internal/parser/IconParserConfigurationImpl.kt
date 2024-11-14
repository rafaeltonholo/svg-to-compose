package dev.tonholo.s2c.gradle.internal.parser

import dev.tonholo.s2c.AppDefaults
import dev.tonholo.s2c.gradle.dsl.parser.IconParserConfiguration
import dev.tonholo.s2c.gradle.dsl.IconVisibility
import dev.tonholo.s2c.gradle.internal.Configuration
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.property
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream
import java.security.MessageDigest
import java.util.Base64
import kotlin.experimental.and

internal class IconParserConfigurationImpl(
    project: Project,
    override val parentName: String,
) : IconParserConfiguration, Configuration {
    override val name: String = "icons"
    override val iconVisibility: Property<IconVisibility> = project
        .objects
        .property<IconVisibility>()
        .convention(
            if (AppDefaults.MAKE_INTERNAL) {
                IconVisibility.Internal
            } else {
                IconVisibility.Public
            }
        )

    internal val receiverType: Property<String?> = project
        .objects
        .property<String?>()
        .convention(null)

    internal val addToMaterialIcons: Property<Boolean> = project
        .objects
        .property<Boolean>()
        .convention(AppDefaults.ADD_TO_MATERIAL_ICONS)

    internal val minified: Property<Boolean> = project
        .objects
        .property<Boolean>()
        .convention(AppDefaults.MINIFIED)

    internal val noPreview: Property<Boolean> = project
        .objects
        .property<Boolean>()
        .convention(AppDefaults.NO_PREVIEW)

    internal val theme: Property<String> = project
        .objects
        .property<String>()
        .convention("")

    internal val mapIconNameTo: Property<((String) -> String)?> = project
        .objects
        .property<((String) -> String)?>()
        .convention(null)

    internal val exclude: Property<Regex?> = project
        .objects
        .property<Regex?>()
        .convention(null)

    override fun makeInternal() {
        iconVisibility.set(IconVisibility.Internal)
    }

    override fun receiverType(fullQualifier: String) {
        receiverType.set(fullQualifier)
    }

    override fun addToMaterialIcons() {
        addToMaterialIcons.set(true)
    }

    override fun minify() {
        minified.set(true)
    }

    override fun noPreview() {
        noPreview.set(true)
    }

    override fun theme(fullQualifier: String) {
        theme.set(fullQualifier)
    }

    override fun mapIconNameTo(mapper: (String) -> String) {
        mapIconNameTo.set(mapper)
    }

    override fun exclude(vararg patterns: Regex) {
        exclude.set(
            if (patterns.size > 1) {
                Regex(patterns.joinToString("|") { "(?>${it.pattern})" })
            } else {
                patterns.single()
            }
        )
    }

    override fun validate(): List<String> {
        val errors = mutableListOf<String>()
        val noPreview = noPreview.get()
        if (noPreview.not() && (theme.isPresent.not() || theme.get().isBlank())) {
            errors.add("${configurationName()}: Theme name cannot be empty when Preview is enabled.")
        }

        return errors
    }

    internal fun calculateHash(): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val raw = buildString {
            append(receiverType.orNull)
            append("|")
            append(addToMaterialIcons.get())
            append("|")
            append(noPreview.get())
            append("|")
            append(iconVisibility.get())
            append("|")
            append(minified.get())
            append("|")
            append(exclude.orNull)
            append("|")
            append(
                mapIconNameTo
                    .orNull
                    ?.let { mapper ->
                        ByteArrayOutputStream()
                            .use { byteStream ->
                                ObjectOutputStream(byteStream).use { outputStream ->
                                    outputStream.writeObject(mapper)
                                }
                                byteStream.toByteArray()
                            }
                            .let { Base64.getEncoder().encodeToString(it) }
                    },
            )
            append("|")
        }.also { println(it) }
        return digest.digest(raw.toByteArray()).joinToString("") { "%02x".format(it and 0xff.toByte()) }
    }
}
