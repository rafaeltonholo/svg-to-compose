package dev.tonholo.s2c.parser.ast.css.elements

import dev.tonholo.s2c.parser.ast.css.CssComponent
import dev.tonholo.s2c.parser.ast.css.CssComponentType
import dev.tonholo.s2c.parser.ast.css.CssQualifiedRule

data class CssSpecificity(
    val a: Int = 0,
    val b: Int = 0,
    val c: Int = 0,
) : Comparable<CssSpecificity> {
    operator fun get(index: Int): Int = when (index) {
        0 -> a
        1 -> b
        2 -> c
        else -> throw IndexOutOfBoundsException("Index $index is out of bounds.")
    }

    operator fun plus(other: CssSpecificity): CssSpecificity {
        return CssSpecificity(
            a = a + other.a,
            b = b + other.b,
            c = c + other.c,
        )
    }

    override operator fun compareTo(other: CssSpecificity): Int {
        return when {
            a != other.a -> a.compareTo(other.a)
            b != other.b -> b.compareTo(other.b)
            c != other.c -> c.compareTo(other.c)
            else -> 0
        }
    }

    override fun toString(): String {
        return "($a, $b, $c)"
    }
}

fun CssSpecificity(rule: CssQualifiedRule): CssSpecificity {
    return rule.components.calculateSpecificity()
}

private fun List<CssComponent>.calculateSpecificity(): CssSpecificity {
    return fold(CssSpecificity()) { specificity, component ->
        when (component) {
            is CssComponent.Single -> specificity + component.calculateSpecificity()
            is CssComponent.Multiple -> specificity + component.components.calculateSpecificity()
            is CssComponent.AtRule -> specificity
        }
    }
}

private fun CssComponent.Single.calculateSpecificity(): CssSpecificity {
    var a = 0
    var b = 0
    var c = 0
    when (type) {
        CssComponentType.Id -> a = 1
        CssComponentType.Class -> b = 1
        CssComponentType.Tag -> c = 1
        CssComponentType.Universal -> Unit
        is CssComponentType.PseudoClass -> {
            when (value) {
                ":not",
                ":is",
                ":has",
                    -> {
                    type.parameters
                        .mapNotNull { component ->
                            when (component) {
                                is CssComponent.Single -> component.calculateSpecificity()
                                is CssComponent.Multiple -> component.components.calculateSpecificity()
                                else -> null
                            }
                        }
                        .maxOf { it }
                        .also {
                            a = it.a
                            b = it.b
                            c = it.c
                        }
                }

                else -> b = 1
            }
        }

        CssComponentType.PseudoElement -> c = 1
    }

    return CssSpecificity(a = a, b = b, c = c)
}
