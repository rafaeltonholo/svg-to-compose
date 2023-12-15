from config import isdebug
from utils.string_utils import camel_case, pascal_case

def template(
    package: str,
    icon_name: str,
    context_provider: str,
    add_to_material: bool,
    theme: str,
    width: float,
    height: float,
    viewport_width: float,
    viewport_height: float,
    has_group: bool,
    paths: str,
) -> str:
    if isdebug():
        print()
        print("========================= Generating file =========================")
        print(f"""Parameters:
            package={package}
            icon_name={icon_name}
            context_provider={context_provider}
            add_to_material={add_to_material}
            theme={theme}
            width={width}
            height={height}
            viewport_width={viewport_width}
            viewport_height={viewport_height}
        """)

    # if user specifies both context_provider and add_to_material, the context_provide will
    # override the material option.
    if context_provider == None and add_to_material:
        context_provider = "Icons.Filled"

    if context_provider != None:
        # as we add the dot in the next line, remove it in case the user adds a left over dot 
        # to avoid compile issues.
        context_provider = context_provider.removesuffix(".")

    icon_name_pascal_case = (f"{context_provider}." if context_provider and context_provider != "" else "") + pascal_case(icon_name)
    icon_name_camel_case = camel_case(icon_name)
    add_to_material_import = """
import androidx.compose.material.icons.Icons""" if add_to_material and context_provider == None else ""

    group_import = """
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.group""" if has_group else ""
    
    return f"""
package {package}

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size{add_to_material_import}
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector{group_import}
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val {icon_name_pascal_case}: ImageVector
    get() {{
        val current = _{icon_name_camel_case}
        if (current != null) return current

        return ImageVector.Builder(
            name = \"{theme}.{icon_name_pascal_case}\",
            defaultWidth = {width}.dp,
            defaultHeight = {height}.dp,
            viewportWidth = {viewport_width}f,
            viewportHeight = {viewport_height}f,
        ).apply {{
            {paths}
        }}.build().also {{ _{icon_name_camel_case} = it }}
    }}

@Preview
@Composable
private fun IconPreview() {{
    {theme} {{
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {{
            Image(
                imageVector = {icon_name_pascal_case},
                contentDescription = null,
                modifier = Modifier.size(100.dp),
            )
        }}
    }}
}}

@Suppress("ObjectPropertyName")
private var _{icon_name_camel_case}: ImageVector? = null
"""