package dev.tonholo.s2c.domain.delegate

import dev.tonholo.s2c.domain.svg.SvgLength
import dev.tonholo.s2c.domain.svg.toSvgLengthOrNull
import dev.tonholo.s2c.domain.xml.XmlChildNode
import dev.tonholo.s2c.domain.xml.XmlParentNode
import dev.tonholo.s2c.domain.xml.XmlRootNode
import dev.tonholo.s2c.logger.debug
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
class AttributeDelegate<in TAttribute : Any?, out TTransform : Any?>(
    private val kClass: KClass<*>,
    private val isNullable: Boolean,
    private val inherited: Boolean,
    private val name: String? = null,
    private val namespace: String? = null,
    private val defaultValue: TTransform? = null,
    private val transform: (TAttribute) -> TTransform = { it as TTransform },
) {
    private fun key(property: KProperty<*>): String =
        namespace?.let { "$it:" }.orEmpty() + (name ?: property.name)

    operator fun getValue(element: XmlChildNode, property: KProperty<*>): TTransform {
        val key = key(property)
        val elementValue = element.attributes[key]
        val value = elementValue ?: if (inherited) {
            findInheritedValue(element, key)
        } else {
            null
        }

        if (isNullable.not() && value == null && defaultValue == null) {
            error("Required attribute '$key' on tag '${element.tagName}' was not found")
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
                else -> if (isNullable) {
                    null
                } else {
                    error("Required attribute '$key' on tag '${element.tagName}' has an invalid value '$value'")
                }
            } as TAttribute,
        )
    }

    /**
     * Sets the value of the attribute represented by this delegate on the given XML element.
     * The value is converted to a string and stored in the element's attributes' map.
     */
    operator fun setValue(element: XmlChildNode, property: KProperty<*>, value: Any?) {
        val key = key(property)
        element.attributes[key] = value.toString()
    }

    private fun findInheritedValue(element: XmlChildNode, key: String): String? {
        debug(
            "The current element '${element.tagName}' doesn't have the attribute '$key'. Looking for inherited value.",
        )
        var parent: XmlParentNode? = element.parent
        var attr: String?
        do {
            attr = parent?.attributes?.get(key)
            parent = (parent as? XmlChildNode)?.parent
        } while (attr == null && parent != null && parent !is XmlRootNode)

        return attr
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
    inherited: Boolean = false,
): AttributeDelegate<TAttribute, TAttribute> =
    AttributeDelegate(
        kClass = TAttribute::class,
        isNullable = null is TAttribute,
        name = name,
        namespace = namespace,
        defaultValue = defaultValue,
        inherited = inherited,
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
    inherited: Boolean = false,
    noinline transform: (TAttribute) -> TTransform,
): AttributeDelegate<TAttribute, TTransform> {
    return AttributeDelegate(
        kClass = TAttribute::class,
        isNullable = null is TAttribute,
        name = name,
        namespace = namespace,
        defaultValue = defaultValue,
        transform = transform,
        inherited = inherited,
    )
}
