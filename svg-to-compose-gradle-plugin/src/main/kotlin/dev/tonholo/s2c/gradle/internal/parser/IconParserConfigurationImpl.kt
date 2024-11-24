package dev.tonholo.s2c.gradle.internal.parser

import dev.tonholo.s2c.AppDefaults
import dev.tonholo.s2c.annotations.DelicateSvg2ComposeApi
import dev.tonholo.s2c.gradle.dsl.IconVisibility
import dev.tonholo.s2c.gradle.dsl.parser.IconParserConfiguration
import dev.tonholo.s2c.gradle.internal.Configuration
import dev.tonholo.s2c.gradle.internal.cache.Cacheable
import dev.tonholo.s2c.gradle.internal.cache.Sha256Hash
import dev.tonholo.s2c.gradle.internal.cache.sha256
import dev.tonholo.s2c.gradle.internal.provider.setIfNotPresent
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.property

private typealias IconMapper = (String) -> String

internal class IconParserConfigurationImpl(
    project: Project,
    override val parentName: String,
) : IconParserConfiguration, Configuration, Cacheable {
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

    internal val isCodeGenerationPersistent: Property<Boolean?> = project
        .objects
        .property<Boolean?>()

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

    @DelicateSvg2ComposeApi
    override fun persist() {
        isCodeGenerationPersistent.set(true)
    }

    override fun validate(): List<String> {
        val errors = mutableListOf<String>()
        val noPreview = noPreview.get()
        if (noPreview.not() && theme.orNull.isNullOrBlank()) {
            errors.add("${configurationName()}: Theme name cannot be empty when Preview is enabled.")
        }

        return errors
    }

    override fun calculateHash(): Sha256Hash {
        val raw = buildString {
            append(receiverType.orNull)
            append("|")
            append(addToMaterialIcons.orNull)
            append("|")
            append(noPreview.orNull)
            append("|")
            append(iconVisibility.orNull)
            append("|")
            append(minified.orNull)
            append("|")
            append(exclude.orNull)
            append("|")
            append(mapIconNameTo.orNull?.let { "fn()" })
            append("|")
            append(isCodeGenerationPersistent.orNull)
        }
        return raw.sha256()
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
