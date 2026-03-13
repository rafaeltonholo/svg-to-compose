# Template System for Output Customization

The template system lets you control the shape of the generated Kotlin code
without modifying the tool. Place an `s2c.template.toml` file in your project
and configure builder functions, receiver types, imports, and property patterns.

## Quick Start

Create `s2c.template.toml` in your project root (or next to the output
directory):

```toml
[definitions]
receiver = { name = "Icons", package = "com.example.icons" }

[definitions.imports]
icon_builder = "com.example.icons.icon"
icon_path = "com.example.icons.iconPath"
theme = "com.example.theme.AppTheme"

[templates]
file_header = """
// Copyright 2026 My Company. All rights reserved.
// SPDX-License-Identifier: Apache-2.0
"""

icon_template = """
${icon:visibility} val ${icon:property_name}: ImageVector by lazy {
    ${template:icon_builder} {
        ${icon:body}
    }
}
"""

[templates.preview]
template = """
@Preview(name = "${icon:name}", showBackground = true)
@Composable
private fun ${icon:name}Preview() {
    ${def:theme} {
        Image(
            imageVector = ${icon:name},
            contentDescription = null,
        )
    }
}
"""

[fragments]
icon_builder = "${def:icon_builder}(name = ${icon:name}, viewportWidth = ${icon:viewport_width}, viewportHeight = ${icon:viewport_height})"
path_builder = "${def:icon_path}(fill = ${path:fill}, fillAlpha = ${path:fill_alpha}, strokeAlpha = ${path:stroke_alpha}, pathFillType = ${path:fill_type})"
group_builder = "group(rotate = ${group:rotate}, pivotX = ${group:pivot_x}, pivotY = ${group:pivot_y})"
```

Run the CLI with `--template`:

```bash
./s2c --template s2c.template.toml -p com.example -t MyTheme input.svg -o output/
```

Or configure via the Gradle plugin:

```kotlin
svgToCompose {
    processor {
        val icons by creating {
            from(layout.projectDirectory.dir("assets/icons"))
            destinationPackage("com.example.icons")
            icons {
                theme("com.example.theme.AppTheme")
                templateFile(layout.projectDirectory.file("s2c.template.toml"))
            }
        }
    }
}
```

## Schema Reference

### `[definitions]`

#### `receiver`

Optional receiver type for the icon property. Adds
`val <Receiver>.<IconName>: ImageVector` and auto-imports the receiver.

```toml
[definitions]
receiver = { name = "Icons", package = "com.example.icons" }
```

| Field     | Type   | Description                   |
|-----------|--------|-------------------------------|
| `name`    | String | Receiver name (e.g., `Icons`) |
| `package` | String | Package to auto-import        |

#### `[definitions.imports]`

Keyed imports referenced in templates via `${def:<key>}`. Each key maps to a
fully qualified import path. During resolution, `${def:key}` is replaced with
the simple name (last segment), and the full path is added to the file's import
list.

```toml
[definitions.imports]
icon_builder = "com.example.icons.icon"
icon_path = "com.example.icons.iconPath"
theme = "com.example.theme.AppTheme"
```

#### `[[definitions.color_mapping]]`

Optional array of color mappings. When a generated `Color(<hex>)` expression
matches a mapping's `value`, it is replaced with the mapping's `name` and the
full import (`<import>.<name>`) is added to the file.

```toml
[[definitions.color_mapping]]
name = "BLACK"
import = "com.example.theme.colors"
value = "0xFF121212"

[[definitions.color_mapping]]
name = "PRIMARY"
import = "com.example.theme.colors"
value = "0xFF0066FF"
```

| Field    | Type   | Description                                          |
|----------|--------|------------------------------------------------------|
| `name`   | String | Constant name used in generated code (e.g., `BLACK`) |
| `import` | String | Package containing the constant                      |
| `value`  | String | Hex color value to match (e.g., `0xFF121212`)        |

**Example:** Given `fill="#121212"` in the SVG, the emitter generates
`SolidColor(Color(0xFF121212))`. With the mapping above, this becomes
`SolidColor(BLACK)` and `import com.example.theme.colors.BLACK` is added.

### `[templates]`

#### `file_header`

Optional file header placed before the `package` statement. Use for license
headers, `@file:Suppress(...)`, or `@file:OptIn(...)` annotations. Supports
all icon-level placeholders.

```toml
[templates]
file_header = """
// Copyright 2026 My Company. All rights reserved.
// SPDX-License-Identifier: Apache-2.0
@file:Suppress("ktlint")
"""
```

#### `icon_template`

The outer icon property template. Controls the generated property/function
shape. If absent, the default backing-field pattern from `ImageVectorEmitter` is
used.

Use `${icon:visibility}` to place the visibility modifier (e.g., `internal`)
where you want it. When the icon is not internal, this resolves to an empty
string and the leading whitespace is trimmed.

```toml
[templates]
icon_template = """
${icon:visibility} val ${icon:property_name}: ImageVector by lazy {
    ${template:icon_builder} {
        ${icon:body}
    }
}
"""
```

#### `[templates.preview]`

Full control over the preview function. If absent, default preview behavior
applies (controlled by `--no-preview` / `--theme`). If present with an empty
`template`, preview generation is suppressed.

```toml
# Custom preview
[templates.preview]
template = """
@Preview(name = "${icon:name}")
@Composable
private fun ${icon:name}Preview() {
    ${def:theme} {
        Image(imageVector = ${icon:name}, contentDescription = null)
    }
}
"""

# Suppress preview entirely
# [templates.preview]
# template = ""
```

### `[fragments]`

Named template fragments for builder call shapes. The engine applies
`path_builder` per-path node, `group_builder` per-group node, and
`icon_builder` once per icon. If a fragment is absent, the default
`ImageVector.Builder` DSL call is used.

Two optional fragments control chunk function generation for large icons:

- `chunk_function_name` — Controls the function name. Receives
  `${icon:name}` (camelCase) and `${icon:chunk_index}`.
  Default: `{name}Chunk{index}`.
- `chunk_function_definition` — Controls the full function signature and body.
  Receives `${icon:chunk_name}` (the resolved name) and `${icon:chunk_body}`
  (the emitted node code). Default:
  `private fun ImageVector.Builder.{name}() { ... }`.

```toml
[fragments]
icon_builder = "${def:icon_builder}(name = ${icon:name}, viewportWidth = ${icon:viewport_width}, viewportHeight = ${icon:viewport_height})"
path_builder = "${def:icon_path}(fill = ${path:fill}, fillAlpha = ${path:fill_alpha})"
group_builder = "group(rotate = ${group:rotate}, pivotX = ${group:pivot_x}, pivotY = ${group:pivot_y})"
# Optional: customize chunk function names
chunk_function_name = "${icon:name}Part${icon:chunk_index}"
# Optional: customize the entire chunk function definition
chunk_function_definition = "private fun ${def:custom_builder}.${icon:chunk_name}() {\n${icon:chunk_body}\n}"
```

## Placeholder Grammar

**Syntax:** `${namespace:key}`

**Regex:** `\$\{(icon|path|group|template|def):([a-z][a-z0-9_.]*)\}`

### Namespaces

| Namespace  | Syntax               | Resolves to                      | Scope                           |
|------------|----------------------|----------------------------------|---------------------------------|
| `icon`     | `${icon:<field>}`    | Value from icon metadata         | `icon_template`, `icon_builder` |
| `path`     | `${path:<field>}`    | Value from path node parameters  | `path_builder` fragment only    |
| `group`    | `${group:<field>}`   | Value from group node parameters | `group_builder` fragment only   |
| `template` | `${template:<name>}` | Rendered fragment output         | `icon_template`                 |
| `def`      | `${def:<key>}`       | Simple name of import            | Any template or fragment        |

### Icon Variables

| Variable                  | Description                                            |
|---------------------------|--------------------------------------------------------|
| `${icon:name}`            | PascalCase icon name                                   |
| `${icon:property_name}`   | Full property name with receiver prefix                |
| `${icon:receiver}`        | Receiver from definitions or CLI                       |
| `${icon:theme}`           | Theme name                                             |
| `${icon:width}`           | Width in dp                                            |
| `${icon:height}`          | Height in dp                                           |
| `${icon:viewport_width}`  | Viewport width (float with `f` suffix)                 |
| `${icon:viewport_height}` | Viewport height (float with `f` suffix)                |
| `${icon:body}`            | Engine-generated body (all nodes)                      |
| `${icon:package}`         | Package name                                           |
| `${icon:visibility}`      | `"internal"` or `""` based on CLI flag                 |
| `${icon:chunk_index}`     | Chunk index (`chunk_function_name` only)               |
| `${icon:chunk_name}`      | Resolved chunk name (`chunk_function_definition` only) |
| `${icon:chunk_body}`      | Chunk node code (`chunk_function_definition` only)     |

### Path Variables

| Variable                     | Description          |
|------------------------------|----------------------|
| `${path:fill}`               | Fill brush/color     |
| `${path:fill_alpha}`         | Fill alpha (float)   |
| `${path:fill_type}`          | Path fill type       |
| `${path:stroke}`             | Stroke brush/color   |
| `${path:stroke_alpha}`       | Stroke alpha (float) |
| `${path:stroke_line_cap}`    | Stroke line cap      |
| `${path:stroke_line_join}`   | Stroke line join     |
| `${path:stroke_miter_limit}` | Stroke miter limit   |
| `${path:stroke_line_width}`  | Stroke line width    |

### Group Variables

| Variable                 | Description            |
|--------------------------|------------------------|
| `${group:rotate}`        | Rotation angle (float) |
| `${group:pivot_x}`       | Pivot X (float)        |
| `${group:pivot_y}`       | Pivot Y (float)        |
| `${group:scale_x}`       | Scale X (float)        |
| `${group:scale_y}`       | Scale Y (float)        |
| `${group:translation_x}` | Translation X (float)  |
| `${group:translation_y}` | Translation Y (float)  |

## Null Handling

When a variable resolves to null, the entire line containing only that parameter
assignment is elided from the output. For example, if `${path:fill_alpha}` is
null:

```
path(
    fill = SolidColor(Color.Black),
    fillAlpha = ${path:fill_alpha},    ← this line is removed
    pathFillType = EvenOdd,
)
```

## Fallback Behavior

Every template piece is independently optional. Missing pieces fall back to the
default `ImageVectorEmitter` output:

| Component                   | Template present       | Template absent                            |
|-----------------------------|------------------------|--------------------------------------------|
| `file_header`               | Emitted before package | No header                                  |
| `icon_template`             | Use template           | Default backing-field pattern              |
| `path_builder`              | Use fragment           | Default `path(...)` call                   |
| `group_builder`             | Use fragment           | Default `group(...)` call                  |
| `icon_builder`              | Use fragment           | Default `ImageVector.Builder(...)`         |
| `chunk_function_name`       | Use fragment           | `{name}Chunk{index}`                       |
| `chunk_function_definition` | Use fragment           | `private fun ImageVector.Builder.{name}()` |
| `preview`                   | Use preview template   | Use CLI/Gradle flags                       |
| `receiver`                  | Template definition    | CLI `--receiver-type`                      |

### Precedence (highest to lowest)

1. CLI flags / Gradle DSL explicit values
2. Template file definitions
3. Hardcoded defaults

Exception: `definitions.receiver` is used only when no CLI `--receiver-type` is
provided.

## Auto-Discovery

When no explicit template path is given, the tool walks up from the output
directory looking for `s2c.template.toml`. This matches the behavior of
`.editorconfig` discovery.

Disable auto-discovery with `--no-template` (CLI) or by omitting
`templateFile()` from the Gradle DSL.

## Gradle Plugin: Per-Configuration Templates

Each `ProcessorConfiguration` can specify its own template file, allowing
different icon sets to use different output shapes:

```kotlin
svgToCompose {
    processor {
        common {
            icons {
                // Default template for all processors
                templateFile(layout.projectDirectory.file("templates/default.template.toml"))
            }
        }

        val outlined by creating {
            from(layout.projectDirectory.dir("icons/outlined"))
            destinationPackage("com.example.icons.outlined")
            icons {
                theme("com.example.theme.AppTheme")
                // Override with a different template
                templateFile(layout.projectDirectory.file("templates/outlined.template.toml"))
            }
        }

        val filled by creating {
            from(layout.projectDirectory.dir("icons/filled"))
            destinationPackage("com.example.icons.filled")
            icons {
                theme("com.example.theme.AppTheme")
                // No templateFile() → inherits from common or auto-discovery
            }
        }
    }
}
```
