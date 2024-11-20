package dev.tonholo.s2c.gradle.internal.parser

import dev.tonholo.s2c.AppDefaults
import dev.tonholo.s2c.gradle.dsl.IconVisibility
import dev.tonholo.s2c.gradle.dsl.parser.IconParserConfiguration
import dev.tonholo.s2c.gradle.internal.Configuration
import dev.tonholo.s2c.gradle.internal.provider.setIfNotPresent
import okio.ByteString.Companion.toByteString
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.property
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream
import java.util.Base64

private typealias IconMapper = (String) -> String

internal class IconParserConfigurationImpl(
    project: Project,
    override val parentName: String,
) : IconParserConfiguration, Configuration {
    override val name: String = "icons"
    override val iconVisibility: Property<IconVisibility> = project
        .objects
        .property<IconVisibility>()

    internal val receiverType: Property<String?> = project
        .objects
        .property<String?>()

    internal val addToMaterialIcons: Property<Boolean> = project
        .objects
        .property<Boolean>()

    internal val minified: Property<Boolean> = project
        .objects
        .property<Boolean>()

    internal val noPreview: Property<Boolean> = project
        .objects
        .property<Boolean>()

    internal val theme: Property<String> = project
        .objects
        .property<String>()

    internal val mapIconNameTo: Property<IconMapper?> = project
        .objects
        .property<IconMapper?>()

    internal val exclude: Property<Regex?> = project
        .objects
        .property<Regex?>()

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
        }
        return raw.toByteArray().toByteString().sha256().hex()
    }

    internal fun merge(common: IconParserConfigurationImpl) {
        iconVisibility
            .setIfNotPresent(
                provider = common.iconVisibility,
                defaultValue = if (AppDefaults.MAKE_INTERNAL) {
                    IconVisibility.Internal
                } else {
                    IconVisibility.Public
                }
            )
        receiverType.setIfNotPresent(
            provider = common.receiverType,
            defaultValue = null,
        )
        addToMaterialIcons.setIfNotPresent(
            provider = common.addToMaterialIcons,
            defaultValue = AppDefaults.ADD_TO_MATERIAL_ICONS,
        )
        minified.setIfNotPresent(provider = common.minified, defaultValue = AppDefaults.MINIFIED)
        noPreview.setIfNotPresent(provider = common.noPreview, defaultValue = AppDefaults.NO_PREVIEW)
        theme.setIfNotPresent(provider = common.theme, defaultValue = "")
        mapIconNameTo.setIfNotPresent(provider = common.mapIconNameTo, defaultValue = null)
        exclude.setIfNotPresent(provider = common.exclude, defaultValue = null)
    }

    override fun toString(): String {
        return buildString {
            appendLine("IconParserConfigurationImpl(")
            appendLine("  name='$name', ")
            appendLine("  iconVisibility=${iconVisibility.orNull}, ")
            appendLine("  receiverType='${receiverType.orNull}', ")
            appendLine("  addToMaterialIcons=${addToMaterialIcons.orNull}, ")
            appendLine("  minified=${minified.orNull}, ")
            appendLine("  noPreview=${noPreview.orNull}, ")
            appendLine("  theme='${theme.orNull}', ")
            appendLine("  mapIconNameTo=${mapIconNameTo.takeIf { it.isPresent }?.let { "lambda" } ?: "null"}, ")
            appendLine("  exclude=${exclude.orNull}, ")
            append(")")
        }
    }
}
