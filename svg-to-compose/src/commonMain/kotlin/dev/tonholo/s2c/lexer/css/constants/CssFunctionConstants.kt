package dev.tonholo.s2c.lexer.css.constants

object CssFunctionConstants {
    private val TransformationFunctions = setOf(
        "translateX(",
        "translateY(",
        "translateZ(",
        "translate(",
        "translate3d(",
    )
    private val RotationFunctions = setOf(
        "rotateX(",
        "rotateY(",
        "rotateZ(",
        "rotate(",
        "rotate3d(",
    )
    private val ScaleFunctions = setOf(
        "scaleX(",
        "scaleY(",
        "scaleZ(",
        "scale(",
        "scale3d(",
    )
    private val SkewFunctions = setOf(
        "skewX(",
        "skewY(",
        "skew(",
    )
    private val MatrixFunctions = setOf(
        "matrix(",
        "matrix3d(",
    )
    private val PerspectiveFunctions = setOf(
        "perspective(",
    )
    private val MathFunctions = setOf(
        "calc(",
        "calc-size(",
        "min(",
        "max(",
        "clamp(",
        "round(",
        "mod(",
        "rem(",
        "sin(",
        "cos(",
        "tan(",
        "asin(",
        "acos(",
        "atan(",
        "atan2(",
        "pow(",
        "sqrt(",
        "hypot(",
        "log(",
        "exp(",
        "abs(",
        "sign(",
    )
    private val FilterFunctions = setOf(
        "blur(",
        "brightness(",
        "contrast(",
        "drop-shadow(",
        "grayscale(",
        "hue-rotate(",
        "invert(",
        "opacity(",
        "saturate(",
        "sepia(",
    )
    private val ColorFunctions = setOf(
        "rgb(",
        "rgba(",
        "hsl(",
        "hwb(",
        "lch(",
        "oklch(",
        "lab(",
        "oklab(",
        "color(",
        "color-mix(",
        "device-cmyk(",
        "light-dark(",
    )
    private val GradientFunctions = setOf(
        "linear-gradient(",
        "radial-gradient(",
        "conic-gradient(",
        "repeating-linear-gradient(",
        "repeating-radial-gradient(",
        "repeating-conic-gradient(",
    )
    private val ImageFunctions = setOf(
        "image(",
        "image-set(",
        "cross-fade(",
        "element(",
        "paint(",
    )
    private val CounterFunctions = setOf(
        "counter(",
        "counters(",
        "symbols(",
    )
    private val ShapeFunctions = setOf(
        "circle(",
        "ellipse(",
        "inset(",
        "rect(",
        "xywh(",
        "polygon(",
        "path(",
        "shape(",
        "ray(",
    )
    private val ReferenceFunctions = setOf(
        "attr(",
        "env(",
        "url(",
        "var(",
    )
    private val GridFunctions = setOf(
        "fit-content(",
        "minmax(",
        "repeat(",
    )
    private val FontFunctions = setOf(
        "stylistic(",
        "styleset(",
        "character-variant(",
        "swash(",
        "ornaments(",
        "annotation(",
    )
    private val EasingFunctions = setOf(
        "linear(",
        "cubic-bezier(",
        "steps(",
    )
    private val AnimationFunctions = setOf(
        "scroll(",
        "view(",
    )
    private val AnchorPositioningFunctions = setOf(
        "anchor(",
        "anchor-size(",
    )
    val AllFunctions = TransformationFunctions +
        RotationFunctions +
        ScaleFunctions +
        SkewFunctions +
        MatrixFunctions +
        PerspectiveFunctions +
        MathFunctions +
        FilterFunctions +
        ColorFunctions +
        GradientFunctions +
        ImageFunctions +
        CounterFunctions +
        ShapeFunctions +
        ReferenceFunctions +
        GridFunctions +
        FontFunctions +
        EasingFunctions +
        AnimationFunctions +
        AnchorPositioningFunctions
}
