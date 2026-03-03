# SVG/AVG/Compose Specification Reference

## Table of Contents

- [W3C SVG Specification References](#w3c-svg-specification-references)
- [Android Vector Drawable (AVG) References](#android-vector-drawable-avg-references)
- [Jetpack Compose ImageVector References](#jetpack-compose-imagevector-references)
- [Codebase Architecture for SVG Processing](#codebase-architecture-for-svg-processing)
- [Common Pitfalls](#common-pitfalls)

## Skill: SVG Specification, Path Manipulation & Transformations

This skill covers the SVG and Android Vector Drawable specifications as they
relate to
parsing, path data manipulation, coordinate transformations, gradients, and CSS
styling
in the `svg-to-compose` project.

---

## W3C SVG Specification References

All SVG handling in this project follows the W3C SVG specifications. When in
doubt, consult these authoritative sources:

### Core Specifications

| Topic                    | Reference                           |
|--------------------------|-------------------------------------|
| SVG 2 (latest)           | https://www.w3.org/TR/SVG2/         |
| SVG 1.1 (Second Edition) | https://www.w3.org/TR/SVG11/        |
| CSS Values and Units     | https://www.w3.org/TR/css-values-4/ |
| CSS Selectors            | https://www.w3.org/TR/selectors-4/  |

### Coordinate Systems & Viewports

| Topic                                      | Reference                                                           |
|--------------------------------------------|---------------------------------------------------------------------|
| Coordinate systems, transformations, units | https://www.w3.org/TR/SVG2/coords.html                              |
| The `viewBox` attribute                    | https://www.w3.org/TR/SVG2/coords.html#ViewBoxAttribute             |
| The `preserveAspectRatio` attribute        | https://www.w3.org/TR/SVG2/coords.html#PreserveAspectRatioAttribute |
| Units (px, em, %, cm, mm, pt, etc.)        | https://www.w3.org/TR/SVG2/coords.html#Units                        |
| Initial coordinate system                  | https://www.w3.org/TR/SVG2/coords.html#InitialCoordinateSystem      |

**In the codebase**: `SvgRootNode.kt` handles `viewBox` parsing; `SvgLength.kt`
handles unit conversions.

### Path Data

| Topic                               | Reference                                                              |
|-------------------------------------|------------------------------------------------------------------------|
| Path data specification             | https://www.w3.org/TR/SVG2/paths.html#PathData                         |
| Path commands summary               | https://www.w3.org/TR/SVG2/paths.html#PathDataBNF                      |
| MoveTo (`M`/`m`)                    | https://www.w3.org/TR/SVG2/paths.html#PathDataMovetoCommands           |
| LineTo (`L`/`l`, `H`/`h`, `V`/`v`)  | https://www.w3.org/TR/SVG2/paths.html#PathDataLinetoCommands           |
| ClosePath (`Z`/`z`)                 | https://www.w3.org/TR/SVG2/paths.html#PathDataClosePathCommand         |
| Cubic Bézier (`C`/`c`, `S`/`s`)     | https://www.w3.org/TR/SVG2/paths.html#PathDataCubicBezierCommands      |
| Quadratic Bézier (`Q`/`q`, `T`/`t`) | https://www.w3.org/TR/SVG2/paths.html#PathDataQuadraticBezierCommands  |
| Elliptical Arc (`A`/`a`)            | https://www.w3.org/TR/SVG2/paths.html#PathDataEllipticalArcCommands    |
| Arc implementation notes            | https://www.w3.org/TR/SVG2/implnote.html#ArcImplementationNotes        |
| Arc endpoint-to-center conversion   | https://www.w3.org/TR/SVG2/implnote.html#ArcConversionEndpointToCenter |
| Arc out-of-range corrections        | https://www.w3.org/TR/SVG2/implnote.html#ArcOutOfRangeParameters       |

- Command instruction in the upper-case means absolute path command.
- Command instruction in the lower-case means relative path command.
- `S`/`s` and `T`/`t` are shorthand for `C`/`c` and `Q`/`q`.

**In the codebase**: `PathNodes.kt` defines the sealed class hierarchy for all
path commands. `PathNodesBuilder.kt` provides DSL construction.

### Transforms

| Topic                          | Reference                                                |
|--------------------------------|----------------------------------------------------------|
| Transform attribute            | https://www.w3.org/TR/SVG2/coords.html#TransformProperty |
| Transform functions list       | https://www.w3.org/TR/SVG2/coords.html#TransformProperty |
| `translate(tx, ty)`            | https://www.w3.org/TR/SVG2/coords.html#TranslateFunction |
| `scale(sx, sy)`                | https://www.w3.org/TR/SVG2/coords.html#ScaleFunction     |
| `rotate(angle, cx, cy)`        | https://www.w3.org/TR/SVG2/coords.html#RotateFunction    |
| `skewX(angle)`, `skewY(angle)` | https://www.w3.org/TR/SVG2/coords.html#SkewFunction      |
| `matrix(a, b, c, d, e, f)`     | https://www.w3.org/TR/SVG2/coords.html#MatrixFunction    |
| CSS Transforms (general)       | https://www.w3.org/TR/css-transforms-1/                  |

**In the codebase**: `AffineTransformation` sealed class in `ApplyTransforms.kt`
represents all transform types. Transform composition uses matrix
multiplication. `PathTransformation.kt` and its subclasses apply transforms to
individual path nodes.

### Basic Shapes

| Topic                 | Reference                                              |
|-----------------------|--------------------------------------------------------|
| Basic shapes overview | https://www.w3.org/TR/SVG2/shapes.html                 |
| `<rect>`              | https://www.w3.org/TR/SVG2/shapes.html#RectElement     |
| `<circle>`            | https://www.w3.org/TR/SVG2/shapes.html#CircleElement   |
| `<ellipse>`           | https://www.w3.org/TR/SVG2/shapes.html#EllipseElement  |
| `<line>`              | https://www.w3.org/TR/SVG2/shapes.html#LineElement     |
| `<polyline>`          | https://www.w3.org/TR/SVG2/shapes.html#PolylineElement |
| `<polygon>`           | https://www.w3.org/TR/SVG2/shapes.html#PolygonElement  |

**In the codebase**: Each shape has a corresponding `Svg*Node.kt` in
`domain/svg/`. Shapes are converted to path commands for uniform processing (
e.g., `SvgRectNode` generates `MoveTo`, `HorizontalLineTo`, `VerticalLineTo`,
`ArcTo` for rounded corners).

### Document Structure & Containers

| Topic                 | Reference                                            |
|-----------------------|------------------------------------------------------|
| `<svg>` element       | https://www.w3.org/TR/SVG2/struct.html#SVGElement    |
| `<g>` (group) element | https://www.w3.org/TR/SVG2/struct.html#GElement      |
| `<defs>` element      | https://www.w3.org/TR/SVG2/struct.html#DefsElement   |
| `<symbol>` element    | https://www.w3.org/TR/SVG2/struct.html#SymbolElement |
| `<use>` element       | https://www.w3.org/TR/SVG2/struct.html#UseElement    |

**In the codebase**: `SvgRootNode.kt`, `SvgGroupNode.kt`, `SvgDefsNode.kt`,
`SvgSymbolNode.kt`, `SvgUseNode.kt`.

### Clipping & Masking

| Topic                     | Reference                                                 |
|---------------------------|-----------------------------------------------------------|
| Clipping overview         | https://www.w3.org/TR/SVG2/render.html#ClippingAndMasking |
| `<clipPath>` element      | https://www.w3.org/TR/SVG2/render.html#ClipPathElement    |
| `<mask>` element          | https://www.w3.org/TR/SVG2/render.html#MaskElement        |
| CSS Masking specification | https://www.w3.org/TR/css-masking-1/                      |

**In the codebase**: `SvgClipPath.kt` and `SvgMaskNode.kt`.

### Paint: Fill & Stroke

| Topic                                   | Reference                                                              |
|-----------------------------------------|------------------------------------------------------------------------|
| Painting overview                       | https://www.w3.org/TR/SVG2/painting.html                               |
| `fill` property                         | https://www.w3.org/TR/SVG2/painting.html#FillProperty                  |
| `fill-rule` (nonzero, evenodd)          | https://www.w3.org/TR/SVG2/painting.html#FillRuleProperty              |
| `fill-opacity`                          | https://www.w3.org/TR/SVG2/painting.html#FillOpacityProperty           |
| `stroke` property                       | https://www.w3.org/TR/SVG2/painting.html#StrokeProperty                |
| `stroke-width`                          | https://www.w3.org/TR/SVG2/painting.html#StrokeWidthProperty           |
| `stroke-linecap` (butt, round, square)  | https://www.w3.org/TR/SVG2/painting.html#StrokeLinecapProperty         |
| `stroke-linejoin` (miter, round, bevel) | https://www.w3.org/TR/SVG2/painting.html#StrokeLinejoinProperty        |
| `stroke-miterlimit`                     | https://www.w3.org/TR/SVG2/painting.html#StrokeMiterlimitProperty      |
| `stroke-dasharray`                      | https://www.w3.org/TR/SVG2/painting.html#StrokeDasharrayProperty       |
| `opacity`                               | https://www.w3.org/TR/SVG2/render.html#ObjectAndGroupOpacityProperties |

**In the codebase**: `SvgGraphicNode.kt` holds all paint attributes. Fill/stroke
can reference gradients via `url(#id)`.

### Gradients

| Topic                                   | Reference                                                                                |
|-----------------------------------------|------------------------------------------------------------------------------------------|
| Gradients overview                      | https://www.w3.org/TR/SVG2/pservers.html#Gradients                                       |
| `<linearGradient>`                      | https://www.w3.org/TR/SVG2/pservers.html#LinearGradientElement                           |
| `<radialGradient>`                      | https://www.w3.org/TR/SVG2/pservers.html#RadialGradientElement                           |
| `<stop>` element                        | https://www.w3.org/TR/SVG2/pservers.html#StopElement                                     |
| `gradientUnits`                         | https://www.w3.org/TR/SVG2/pservers.html#LinearGradientElementGradientUnitsAttribute     |
| `gradientTransform`                     | https://www.w3.org/TR/SVG2/pservers.html#LinearGradientElementGradientTransformAttribute |
| `spreadMethod` (pad, reflect, repeat)   | https://www.w3.org/TR/SVG2/pservers.html#LinearGradientElementSpreadMethodAttribute      |
| `objectBoundingBox` vs `userSpaceOnUse` | https://www.w3.org/TR/SVG2/coords.html#ObjectBoundingBox                                 |

**In the codebase**: `SvgLinearGradientNode.kt`, `SvgRadialGradientNode.kt`,
`SvgGradientStopNode.kt`, and base classes in `domain/svg/gradient/`. Bounding
box calculation in `geom/bounds/` is required for `objectBoundingBox` gradient
units.

### Colors

| Topic                          | Reference                                           |
|--------------------------------|-----------------------------------------------------|
| SVG recognized color keywords  | https://www.w3.org/TR/SVG2/types.html#ColorKeywords |
| CSS Color Level 4              | https://www.w3.org/TR/css-color-4/                  |
| Named colors list (148 colors) | https://www.w3.org/TR/css-color-4/#named-colors     |

**In the codebase**: `SvgColor.kt` maps all 140+ W3C named colors.
`ComposeColor.kt` generates Compose `Color(0xAARRGGBB)` code.

### CSS Styling in SVG

| Topic                     | Reference                                                      |
|---------------------------|----------------------------------------------------------------|
| Styling SVG content       | https://www.w3.org/TR/SVG2/styling.html                        |
| `<style>` element         | https://www.w3.org/TR/SVG2/styling.html#StyleElement           |
| Presentation attributes   | https://www.w3.org/TR/SVG2/styling.html#PresentationAttributes |
| CSS cascade & specificity | https://www.w3.org/TR/css-cascade-5/                           |
| Selector specificity      | https://www.w3.org/TR/selectors-4/#specificity-rules           |

**In the codebase**: `CssTokenizer.kt` tokenizes CSS; `CssParser.kt` builds an
AST; `CssSpecificity.kt` calculates selector specificity. `SvgStyleNode.kt`
processes `<style>` elements.

---

## Android Vector Drawable (AVG) References

| Topic                   | Reference                                                                                             |
|-------------------------|-------------------------------------------------------------------------------------------------------|
| VectorDrawable overview | https://developer.android.com/reference/android/graphics/drawable/VectorDrawable                      |
| `<vector>` element      | https://developer.android.com/reference/android/graphics/drawable/VectorDrawable                      |
| `<group>` element       | https://developer.android.com/reference/android/graphics/drawable/VectorDrawable (Groups section)     |
| `<path>` element        | https://developer.android.com/reference/android/graphics/drawable/VectorDrawable (Paths section)      |
| `<clip-path>` element   | https://developer.android.com/reference/android/graphics/drawable/VectorDrawable (Clip paths section) |
| Adaptive icons          | https://developer.android.com/develop/ui/views/launch/icon_design_adaptive                            |
| Path data (same as SVG) | https://www.w3.org/TR/SVG2/paths.html#PathData                                                        |

**In the codebase**: `domain/avg/` contains the AVG domain model. `AvgParser.kt`
handles XML parsing.

---

## Jetpack Compose ImageVector References

| Topic                 | Reference                                                                                              |
|-----------------------|--------------------------------------------------------------------------------------------------------|
| ImageVector overview  | https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/vector/ImageVector         |
| ImageVector.Builder   | https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/vector/ImageVector.Builder |
| PathBuilder           | https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/vector/PathBuilder         |
| Brush (gradients)     | https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/Brush                      |
| StrokeCap, StrokeJoin | https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/StrokeCap                  |
| PathFillType          | https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/PathFillType               |
| TileMode              | https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/TileMode                   |

**In the codebase**: `domain/compose/` maps SVG/AVG concepts to Compose types.
`ImageVectorNode` in the domain layer generates `ImageVector.Builder` DSL calls.

---

## Codebase Architecture for SVG Processing

### Processing Pipeline

```
SVG/AVG Input (XML file)
       │
       ▼
┌──────────────┐
│  XML Parser  │  ksoup-based parsing
│  (XmlParser) │  SvgParser / AvgParser
└──────┬───────┘
       │
       ▼
┌──────────────────┐
│  SVG Node Tree   │  SvgRootNode → SvgGroupNode → SvgPathNode, etc.
│  (domain/svg/)   │  Attribute delegation, ID resolution
└──────┬───────────┘
       │
       ▼
┌────────────────────┐
│  CSS Resolution    │  <style> → CssTokenizer → CssParser → CssSpecificity
│  (lexer/, parser/) │  Specificity-sorted rules applied to nodes
└──────┬─────────────┘
       │
       ▼
┌───────────────────┐
│  Node Resolution  │  Resolve <use>/<symbol> references
│                   │  Build bounding boxes (geom/bounds/)
└──────┬────────────┘
       │
       ▼
┌──────────────────────────────┐
│  Path Normalization          │
│  1. Relative → Absolute      │  (geom/path/PathInstructionToAbsolute.kt)
│  2. Remove shorthands (S→C)  │  (geom/path/PathInstructionRemoveShorthand.kt)
│  3. Apply transforms         │  (geom/transform/ApplyTransforms.kt)
└──────┬───────────────────────┘
       │
       ▼
┌───────────────────┐
│  Gradient Calc    │  objectBoundingBox → offset calculation
│  (domain/svg/     │  ComposeBrush.Gradient.Linear / Radial
│   gradient/)      │
└──────┬────────────┘
       │
       ▼
┌───────────────────┐
│  Code Generation  │  ImageVectorNode → Kotlin source code
│  (Processor.kt,   │  ImageVector.Builder DSL with path() calls
│   IconWriter)     │
└───────────────────┘
```

### Key Files by Concern

| Concern                 | Key Files                                                                                                       |
|-------------------------|-----------------------------------------------------------------------------------------------------------------|
| SVG path commands       | `domain/PathNodes.kt`, `domain/PathCommand.kt`                                                                  |
| Shape → path conversion | `domain/svg/SvgRectNode.kt`, `SvgCircleNode.kt`, `SvgEllipseNode.kt`, `SvgPolygonNode.kt`, `SvgPolylineNode.kt` |
| Affine transforms       | `geom/transform/ApplyTransforms.kt` (AffineTransformation sealed class)                                         |
| Transform application   | `geom/transform/PathTransformation.kt` + per-command subclasses                                                 |
| Arc math                | `geom/transform/ArcTransformation.kt`, `geom/bounds/ArcBoundingBoxCalculator.kt`                                |
| Bézier bounds           | `geom/bounds/BezierBoundingBoxCalculator.kt`                                                                    |
| Bounding box            | `geom/bounds/BoundingBox.kt`                                                                                    |
| Path normalization      | `geom/path/PathInstructionToAbsolute.kt`, `PathInstructionRemoveShorthand.kt`                                   |
| CSS tokenizer           | `lexer/css/CssTokenizer.kt`, `CssTokenKind.kt`                                                                  |
| CSS parser              | `parser/ast/css/CssParser.kt`, `CssConsumers.kt`                                                                |
| CSS specificity         | `parser/ast/css/CssSpecificity.kt`                                                                              |
| SVG gradients           | `domain/svg/SvgLinearGradientNode.kt`, `SvgRadialGradientNode.kt`, `SvgGradientStopNode.kt`                     |
| AVG gradients           | `domain/avg/gradient/AvgLinearGradient.kt`, `AvgRadianGradient.kt`                                              |
| Compose output          | `domain/compose/ComposeBrush.kt`, `ComposeColor.kt`, `ComposeOffset.kt`                                         |
| Named colors            | `domain/svg/SvgColor.kt` (140+ W3C named colors)                                                                |

### Path Normalization Steps (Required Order)

Path data must be normalized before code generation. The steps MUST run in this
order:

1. **`toAbsolute()`** — Convert all relative commands (`m`, `l`, `c`, etc.) to
   absolute (`M`, `L`, `C`, etc.) by tracking the cursor position and adding it
   to relative coordinates.

2. **`removeShorthandNodes()`** — Replace shorthand/reflective commands:
    - `S`/`s` (ReflectiveCurveTo) → `C` (CurveTo) — reflects the previous cubic
      control point.
    - `T`/`t` (ReflectiveQuadTo) → `Q` (QuadTo) — reflects the previous
      quadratic control point.
    - **Precondition**: All nodes must already be absolute.

3. **`applyTransformations()`** — Multiply each node's coordinates by the
   accumulated affine transform matrix. Handles special cases like arcs (SVD
   decomposition for radii/angle).

### Elliptical Arc Implementation Notes

Arc handling is one of the most complex parts of SVG. Key references:

- **Endpoint-to-center parameterization**: https://www.w3.org/TR/SVG2/implnote.html#ArcConversionEndpointToCenter
- **Out-of-range parameters**: https://www.w3.org/TR/SVG2/implnote.html#ArcOutOfRangeParameters
- **Bounding box of arc**: Requires finding extrema at cardinal angles (0, 90,
  180, 270 degrees) within the arc's angular range.
- **Transform application**: Uses SVD (Singular Value Decomposition) to
  decompose the transformed ellipse into new radii and rotation angle.

In the codebase, arc math is in:

- `geom/transform/ArcTransformation.kt` — transform application with SVD
- `geom/bounds/ArcBoundingBoxCalculator.kt` — bounding box with
  endpoint-to-center conversion

### Gradient Coordinate Spaces

Two coordinate systems for gradients (critical for correct rendering):

- **`objectBoundingBox`** (default): Gradient coordinates are fractions (0–1) of
  the element's bounding box. The bounding box must be computed from the path
  data.
- **`userSpaceOnUse`**: Gradient coordinates are in the current user coordinate
  system (absolute).

In the codebase, `SvgGradient.kt` base class handles coordinate calculation
based on `gradientUnits`, using `BoundingBox` from `geom/bounds/`.

---

## Common Pitfalls

1. **Arc flags are single digits**: The `large-arc-flag` and `sweep-flag` in arc
   commands are `0` or `1` with no separator required before the next number.
   Parsers must handle `A 25 25 0 0125 50` (flags are `0` and `1`, endpoint is
   `25, 50`).

2. **Implicit repeated commands**: After a `MoveTo`, subsequent coordinate pairs
   are treated as implicit `LineTo` commands. Similarly for other commands.

3. **Relative vs absolute**: Relative commands offset from the current point.
   After converting to absolute, the current point must be tracked correctly
   through every command including `Z` (which resets to the last `M`).

4. **Shorthand reflection**: `S` reflects the second control point of the
   previous `C`; if the previous command was not `C` or `S`, the control point
   equals the current point. Same logic applies to `T`/`Q`.

5. **Transform stacking**: Child elements inherit ancestor transforms. The
   effective transform is the product of all ancestor transforms in document
   order.

6. **`viewBox` offset**: If `viewBox` has non-zero min-x or min-y, an implicit
   translation must be applied.

7. **Gradient inheritance**: Gradients can reference other gradients via `href`/
   `xlink:href` to inherit stops and attributes.
