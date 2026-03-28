package dev.tonholo.s2c.gradle.internal.parser

import dev.tonholo.s2c.AppDefaults
import dev.tonholo.s2c.annotations.DelicateSvg2ComposeApi
import dev.tonholo.s2c.gradle.dsl.IconVisibility
import dev.tonholo.s2c.gradle.dsl.parser.IconParserConfiguration
import dev.tonholo.s2c.gradle.internal.Configuration
import dev.tonholo.s2c.gradle.internal.provider.setIfNotPresent
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Optional
import org.gradle.kotlin.dsl.property

private typealias IconMapper = (String) -> String

internal class IconParserConfigurationImpl(
    objectFactory: ObjectFactory,
    @get:Internal override val parentName: String,
) : IconParserConfiguration,
    Configuration {
    // Gradle does not inherit annotations from interfaces. These overrides exist solely
    // to attach @Internal so Gradle doesn't treat Named/Configuration properties as task inputs.
    @get:Internal
    override val name: String = "icons"

    @get:Internal
    override val visibleName: String
        get() = super.visibleName

    @get:Internal
    override val fullName: String
        get() = super.fullName

    @get:Input
    override val iconVisibility: Property<IconVisibility> = objectFactory.property<IconVisibility>()

    @get:Input
    @get:Optional
    internal val receiverType: Property<String> = objectFactory.property<String>()

    @get:Input
    @get:Optional
    internal val addToMaterialIcons: Property<Boolean> = objectFactory.property<Boolean>()

    @get:Input
    internal val minified: Property<Boolean> = objectFactory.property<Boolean>()

    @get:Input
    @get:Optional
    internal val noPreview: Property<Boolean> = objectFactory.property<Boolean>()

    @get:Input
    internal val theme: Property<String> = objectFactory.property<String>()

    @get:Internal
    @Suppress("UNCHECKED_CAST")
    internal val mapIconNameTo: Property<IconMapper> =
        objectFactory.property(IconMapper::class.java) as Property<IconMapper>

    @get:Internal
    internal val exclude: Property<Regex> = objectFactory.property<Regex>()

    @get:Input
    @get:Optional
    val excludePattern: String?
        get() = exclude.orNull?.pattern

    internal val isCodeGenerationPersistent: Property<Boolean> = objectFactory.property<Boolean>()

    @get:Input
    @get:Optional
    val codeGenerationPersistent: Boolean?
        get() = isCodeGenerationPersistent.orNull

    // Tracks presence only. Two different mapper lambdas will fingerprint identically.
    // This is an inherent limitation of non-serializable lambda properties.
    @get:Input
    val hasIconNameMapper: Boolean
        get() = mapIconNameTo.isPresent

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
            },
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

    internal fun merge(common: IconParserConfigurationImpl) {
        iconVisibility
            .setIfNotPresent(
                provider = common.iconVisibility,
                defaultValue = if (AppDefaults.MAKE_INTERNAL) {
                    IconVisibility.Internal
                } else {
                    IconVisibility.Public
                },
            )
        receiverType.setIfNotPresent(provider = common.receiverType)
        addToMaterialIcons.setIfNotPresent(
            provider = common.addToMaterialIcons,
            defaultValue = AppDefaults.ADD_TO_MATERIAL_ICONS,
        )
        minified.setIfNotPresent(provider = common.minified, defaultValue = AppDefaults.MINIFIED)
        noPreview.setIfNotPresent(provider = common.noPreview, defaultValue = AppDefaults.NO_PREVIEW)
        theme.setIfNotPresent(provider = common.theme, defaultValue = "")
        mapIconNameTo.setIfNotPresent(provider = common.mapIconNameTo)
        exclude.setIfNotPresent(provider = common.exclude)
    }

    override fun toString(): String = buildString {
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
