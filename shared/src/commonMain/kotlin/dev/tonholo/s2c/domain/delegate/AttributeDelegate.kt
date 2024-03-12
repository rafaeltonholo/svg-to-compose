package dev.tonholo.s2c.domain.delegate

import dev.tonholo.s2c.domain.svg.SvgLength
import dev.tonholo.s2c.domain.svg.toSvgLengthOrNull
import dev.tonholo.s2c.domain.xml.XmlChildNodeWithAttributes
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
class AttributeDelegate<in TAttribute : Any?, out TTransform : Any?>(
    private val kClass: KClass<*>,
    private val isNullable: Boolean,
    private val name: String? = null,
    private val namespace: String? = null,
    private val defaultValue: TTransform? = null,
    private val transform: (TAttribute) -> TTransform = { it as TTransform },
) {
    private fun key(property: KProperty<*>): String =
        namespace?.let { "$it:" }.orEmpty() + (name ?: property.name)

    operator fun getValue(element: XmlChildNodeWithAttributes, property: KProperty<*>): TTransform {
        val key = key(property)
        val value = element.attributes[key]
        if (isNullable.not() && value == null && defaultValue == null) {
            error("Required attribute '$key' on tag '${element.name}' was not found")
        }

        if (isNullable.not() && value == null && defaultValue != null) {
            return defaultValue
        }

        return transform(
            when (kClass) {
                SvgLength::class -> value?.toSvgLengthOrNull()
                Int::class -> value?.toIntOrNull()
                String::class -> value
                Float::class -> value?.toFloatOrNull()
                Double::class -> value?.toDoubleOrNull()
                else -> null // throw an unsupported type?
            } as TAttribute,
        )
    }

    operator fun setValue(element: XmlChildNodeWithAttributes, property: KProperty<*>, value: Any?) {
        val key = key(property)
        element.attributes[key] = value.toString()
    }
}

/**
 * Creates an attribute delegate for an XML element property.
 *
 * This function creates a delegate that can be used to access and modify attributes on an
 * XML element using properties. The delegate ensures type safety and provides null-safety
 * checks for required attributes. This variant assumes the attribute value doesn't require
 * any transformation and directly uses the raw value.
 *
 * @param TAttribute The type of the attribute value (reified).
 * @param name An optional custom name for the attribute (defaults to the property name).
 * @param namespace An optional namespace for the attribute.
 * @return An [AttributeDelegate] instance for the specified types.
 * @throws IllegalStateException if a required attribute (non-nullable [TAttribute]) is not found on the XML element.
 */
inline fun <reified TAttribute : Any?> attribute(
    name: String? = null,
    namespace: String? = null,
    defaultValue: TAttribute? = null,
): AttributeDelegate<TAttribute, TAttribute> =
    AttributeDelegate(
        kClass = TAttribute::class,
        isNullable = null is TAttribute,
        name = name,
        namespace = namespace,
        defaultValue = defaultValue,
    )

/**
 * Creates an attribute delegate for an XML element property.
 *
 * This function creates a delegate that can be used to access and modify attributes on an
 * XML element using properties. The delegate ensures type safety and provides null-safety
 * checks for required attributes.
 *
 * @param TAttribute The type of the attribute value before transformation (reified).
 * @param TTransform The type of the attribute value after transformation (reified).
 * @param name An optional custom name for the attribute (defaults to the property name).
 * @param namespace An optional namespace for the attribute.
 * @param transform A transformation function applied to the raw attribute value before returning.
 * @return An [AttributeDelegate] instance for the specified types.
 * @throws IllegalStateException if a required attribute (non-nullable [TAttribute]) is not found on the XML element.
 */
inline fun <reified TAttribute : Any?, reified TTransform : Any?> attribute(
    name: String? = null,
    namespace: String? = null,
    defaultValue: TTransform? = null,
    noinline transform: (TAttribute) -> TTransform,
): AttributeDelegate<TAttribute, TTransform> {
    return AttributeDelegate(
        kClass = TAttribute::class,
        isNullable = null is TAttribute,
        name = name,
        namespace = namespace,
        defaultValue = defaultValue,
        transform = transform,
    )
}
