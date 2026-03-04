from __future__ import annotations

"""MCP server that exposes vector-analysis utilities for svg-to-compose workflows.

This module provides three MCP tools:
1) analyze_vector: summarize structure and color usage from SVG/AVG/Kotlin files.
2) render_preview: render generated Kotlin ImageVector code to PNG (base64 output).
3) verify_conversion: compare source vectors against generated Kotlin output.

The server is intentionally lightweight and uses stdio transport for local MCP usage.
"""

import base64
import hashlib
import os
import re
import shlex
import subprocess
import tempfile
import xml.etree.ElementTree as et
from dataclasses import dataclass
from pathlib import Path
from typing import Any

from mcp.server.fastmcp import FastMCP

mcp = FastMCP("svg-to-compose-vector-tools")

FLOAT_RE = r"[-+]?(?:\d+(?:\.\d+)?|\.\d+)"
HEX_COLOR_RE = re.compile(r"#(?:[0-9a-fA-F]{3,8})\b")
KOTLIN_COLOR_RE = re.compile(r"Color\(0x([0-9A-Fa-f]{6,8})\)")


@dataclass(frozen=True)
class VectorSummary:
    """Normalized summary returned by `analyze_vector` and used by verification."""

    format: str
    viewport: dict[str, float] | None
    bounds: dict[str, float] | None
    path_count: int
    used_colors: list[str]
    source_path: str

    def as_dict(self) -> dict[str, Any]:
        return {
            "format": self.format,
            "viewport": self.viewport,
            "bounds": self.bounds,
            "pathCount": self.path_count,
            "usedColors": self.used_colors,
            "sourcePath": self.source_path,
        }


def _normalize_path(path: str) -> Path:
    """Resolve and validate an input path as an existing file."""

    resolved = Path(path).expanduser().resolve()
    if not resolved.exists():
        raise FileNotFoundError(f"File does not exist: {resolved}")
    if not resolved.is_file():
        raise ValueError(f"Path is not a file: {resolved}")
    return resolved


def _strip_unit(value: str | None) -> float | None:
    """Extract the first numeric value from unit-based strings like `24dp` or `95px`."""

    if value is None:
        return None
    match = re.search(FLOAT_RE, value.strip())
    return float(match.group(0)) if match else None


def _local_name(tag: str) -> str:
    """Return XML local tag name, ignoring namespace prefixes."""

    if "}" in tag:
        return tag.split("}", 1)[1]
    return tag


def _normalize_hex_color(color: str) -> str:
    """Normalize hex colors to uppercase long form when possible."""

    color = color.strip()
    if not color.startswith("#"):
        return color
    raw = color[1:]
    if len(raw) == 3:
        raw = "".join(ch * 2 for ch in raw)
    return f"#{raw.upper()}"


def _extract_svg_colors(root: et.Element, xml_text: str) -> set[str]:
    """Collect hex colors from common SVG color attributes and inline styles."""

    colors: set[str] = set()
    for node in root.iter():
        for attr_name in ("fill", "stroke", "stop-color"):
            attr_val = node.attrib.get(attr_name)
            if attr_val and attr_val.lower() != "none":
                colors.add(_normalize_hex_color(attr_val))

        style = node.attrib.get("style")
        if style:
            colors.update(_normalize_hex_color(match.group(0)) for match in HEX_COLOR_RE.finditer(style))

    colors.update(_normalize_hex_color(match.group(0)) for match in HEX_COLOR_RE.finditer(xml_text))
    return colors


def _parse_svg(path: Path) -> VectorSummary:
    """Parse a `.svg` file into a normalized `VectorSummary`."""

    xml_text = path.read_text(encoding="utf-8")
    root = et.fromstring(xml_text)

    view_box = root.attrib.get("viewBox")
    viewport: dict[str, float] | None = None
    if view_box:
        parts = view_box.replace(",", " ").split()
        if len(parts) == 4:
            viewport = {"width": float(parts[2]), "height": float(parts[3])}

    bounds = {
        "width": _strip_unit(root.attrib.get("width")) or 0.0,
        "height": _strip_unit(root.attrib.get("height")) or 0.0,
    }

    path_count = sum(1 for node in root.iter() if _local_name(node.tag) == "path")
    colors = sorted(_extract_svg_colors(root, xml_text))

    return VectorSummary(
        format="svg",
        viewport=viewport,
        bounds=bounds,
        path_count=path_count,
        used_colors=colors,
        source_path=str(path),
    )


def _parse_avg(path: Path) -> VectorSummary:
    """Parse Android Vector Drawable XML (`.xml`) into `VectorSummary`."""

    xml_text = path.read_text(encoding="utf-8")
    root = et.fromstring(xml_text)
    attributes = root.attrib

    viewport = {
        "width": _strip_unit(attributes.get("{http://schemas.android.com/apk/res/android}viewportWidth")) or 0.0,
        "height": _strip_unit(attributes.get("{http://schemas.android.com/apk/res/android}viewportHeight")) or 0.0,
    }
    bounds = {
        "width": _strip_unit(attributes.get("{http://schemas.android.com/apk/res/android}width")) or 0.0,
        "height": _strip_unit(attributes.get("{http://schemas.android.com/apk/res/android}height")) or 0.0,
    }

    path_count = sum(1 for node in root.iter() if _local_name(node.tag) == "path")

    colors: set[str] = set()
    for node in root.iter():
        for attr_name in (
            "{http://schemas.android.com/apk/res/android}fillColor",
            "{http://schemas.android.com/apk/res/android}strokeColor",
            "{http://schemas.android.com/apk/res/android}color",
        ):
            attr = node.attrib.get(attr_name)
            if attr and attr.lower() != "none":
                colors.add(_normalize_hex_color(attr))
    colors.update(_normalize_hex_color(match.group(0)) for match in HEX_COLOR_RE.finditer(xml_text))

    return VectorSummary(
        format="avg",
        viewport=viewport,
        bounds=bounds,
        path_count=path_count,
        used_colors=sorted(colors),
        source_path=str(path),
    )


def _extract_float(text: str, pattern: str) -> float | None:
    """Return first float captured by a regex pattern in arbitrary text."""

    match = re.search(pattern, text)
    return float(match.group(1)) if match else None


def _parse_kotlin(path: Path) -> VectorSummary:
    """Parse generated Kotlin ImageVector source (`.kt`) into `VectorSummary`."""

    text = path.read_text(encoding="utf-8")
    viewport = {
        "width": _extract_float(text, rf"viewportWidth\s*=\s*({FLOAT_RE})f"),
        "height": _extract_float(text, rf"viewportHeight\s*=\s*({FLOAT_RE})f"),
    }
    if viewport["width"] is None and viewport["height"] is None:
        viewport = None

    bounds = {
        "width": _extract_float(text, rf"defaultWidth\s*=\s*({FLOAT_RE})\.dp"),
        "height": _extract_float(text, rf"defaultHeight\s*=\s*({FLOAT_RE})\.dp"),
    }
    if bounds["width"] is None and bounds["height"] is None:
        bounds = None

    path_count = len(re.findall(r"\bpath\s*\(", text))

    colors: set[str] = set()
    for color in HEX_COLOR_RE.findall(text):
        colors.add(_normalize_hex_color(color))
    for match in KOTLIN_COLOR_RE.finditer(text):
        value = match.group(1)
        if len(value) == 8:
            colors.add(f"#{value.upper()}")
        elif len(value) == 6:
            colors.add(f"#FF{value.upper()}")

    return VectorSummary(
        format="kotlin",
        viewport=viewport,
        bounds=bounds,
        path_count=path_count,
        used_colors=sorted(colors),
        source_path=str(path),
    )


def summarize_vector(path: Path) -> VectorSummary:
    """Dispatch parsing logic by file type and return a normalized vector summary."""

    suffix = path.suffix.lower()
    if suffix == ".svg":
        return _parse_svg(path)
    if suffix == ".kt":
        return _parse_kotlin(path)
    if suffix == ".xml":
        text = path.read_text(encoding="utf-8")
        if "<vector" in text:
            return _parse_avg(path)
        if "<svg" in text:
            return _parse_svg(path)
        raise ValueError(f"Unknown XML vector format for file: {path}")
    raise ValueError(f"Unsupported file extension: {suffix}. Expected .svg, .xml, or .kt")


def _run_command(command: str) -> subprocess.CompletedProcess[str]:
    """Execute a shell command used by external render adapters."""

    return subprocess.run(
        command,
        shell=True,
        check=False,
        capture_output=True,
        text=True,
    )


def _render_kotlin_to_png(input_path: Path, output_path: Path) -> tuple[bool, str]:
    """Render generated Kotlin file to PNG using `S2C_KOTLIN_RENDER_CMD` template."""

    command_template = os.getenv("S2C_KOTLIN_RENDER_CMD", "").strip()
    if not command_template:
        return (
            False,
            (
                "S2C_KOTLIN_RENDER_CMD is not configured. "
                "Set it to a command template using {input} and {output} placeholders."
            ),
        )
    command = command_template.format(input=shlex.quote(str(input_path)), output=shlex.quote(str(output_path)))
    result = _run_command(command)
    if result.returncode != 0:
        return False, f"Render command failed ({result.returncode}): {result.stderr.strip() or result.stdout.strip()}"
    if not output_path.exists():
        return False, f"Render command completed but output PNG was not created: {output_path}"
    return True, ""


def _render_svg_to_png(input_path: Path, output_path: Path) -> tuple[bool, str]:
    """Render SVG to PNG via `rsvg-convert`."""

    try:
        result = subprocess.run(
            ["rsvg-convert", str(input_path), "-o", str(output_path)],
            check=False,
            capture_output=True,
            text=True,
        )
    except FileNotFoundError:
        return False, "rsvg-convert is not installed or not found in PATH."
    if result.returncode != 0:
        return False, f"rsvg-convert failed ({result.returncode}): {result.stderr.strip() or result.stdout.strip()}"
    if not output_path.exists():
        return False, f"rsvg-convert completed but output PNG was not created: {output_path}"
    return True, ""


def _vector_diff_report(source: VectorSummary, generated: VectorSummary) -> dict[str, Any]:
    """Build a structural fidelity report between source and generated summaries."""

    checks: list[dict[str, Any]] = []

    def compare_dims(field: str, left: dict[str, float] | None, right: dict[str, float] | None, tolerance: float = 0.01) -> None:
        if left is None or right is None:
            checks.append({"check": field, "passed": False, "reason": "missing dimension data"})
            return
        lw = left.get("width")
        lh = left.get("height")
        rw = right.get("width")
        rh = right.get("height")
        if None in (lw, lh, rw, rh):
            checks.append({"check": field, "passed": False, "reason": "incomplete dimension data"})
            return
        width_delta = abs(float(lw) - float(rw))
        height_delta = abs(float(lh) - float(rh))
        checks.append(
            {
                "check": field,
                "passed": width_delta <= tolerance and height_delta <= tolerance,
                "source": {"width": lw, "height": lh},
                "generated": {"width": rw, "height": rh},
                "delta": {"width": width_delta, "height": height_delta},
            },
        )

    compare_dims("viewport", source.viewport, generated.viewport)
    compare_dims("bounds", source.bounds, generated.bounds)

    source_paths = source.path_count
    generated_paths = generated.path_count
    path_delta = abs(source_paths - generated_paths)
    path_ratio = 1.0 if source_paths == 0 else generated_paths / max(source_paths, 1)
    path_passed = source_paths == generated_paths
    checks.append(
        {
            "check": "path_count",
            "passed": path_passed,
            "source": source_paths,
            "generated": generated_paths,
            "delta": path_delta,
            "ratio": path_ratio,
        },
    )

    source_colors = set(source.used_colors)
    generated_colors = set(generated.used_colors)
    missing_colors = sorted(source_colors - generated_colors)
    checks.append(
        {
            "check": "colors",
            "passed": len(missing_colors) == 0,
            "missingFromGenerated": missing_colors,
            "sourceCount": len(source_colors),
            "generatedCount": len(generated_colors),
        },
    )

    faithful = all(item["passed"] for item in checks)
    confidence = "high" if faithful else "medium"

    return {
        "faithful": faithful,
        "confidence": confidence,
        "checks": checks,
    }


@mcp.tool(
    name="analyze_vector",
    description="Analyze SVG, AVG/XML, or generated Kotlin ImageVector source and return structural summary.",
)
def analyze_vector(path: str) -> dict[str, Any]:
    """Return structural summary of an SVG/AVG/Kotlin vector file."""

    resolved = _normalize_path(path)
    summary = summarize_vector(resolved)
    return summary.as_dict()


@mcp.tool(
    name="render_preview",
    description=(
        "Render a generated Kotlin ImageVector to PNG and return base64 output. "
        "Requires S2C_KOTLIN_RENDER_CMD env var with {input} and {output} placeholders."
    ),
)
def render_preview(path: str) -> dict[str, Any]:
    """Render a generated Kotlin ImageVector file and return base64 PNG output."""

    resolved = _normalize_path(path)
    if resolved.suffix.lower() != ".kt":
        raise ValueError("render_preview expects a generated Kotlin .kt file.")

    with tempfile.TemporaryDirectory(prefix="s2c_mcp_preview_") as temp_dir:
        output = Path(temp_dir) / "preview.png"
        ok, error = _render_kotlin_to_png(resolved, output)
        if not ok:
            return {
                "ok": False,
                "error": error,
                "hint": "Configure S2C_KOTLIN_RENDER_CMD, then call the tool again.",
            }

        encoded = base64.b64encode(output.read_bytes()).decode("ascii")
        return {
            "ok": True,
            "mimeType": "image/png",
            "base64Png": encoded,
            "sourcePath": str(resolved),
        }


@mcp.tool(
    name="verify_conversion",
    description=(
        "Verify whether generated Kotlin ImageVector faithfully represents the source SVG/AVG "
        "using structural and optional render-based checks."
    ),
)
def verify_conversion(source_path: str, generated_kotlin_path: str) -> dict[str, Any]:
    """Check structural (and optionally rendered) fidelity of conversion output."""

    source_file = _normalize_path(source_path)
    generated_file = _normalize_path(generated_kotlin_path)
    if generated_file.suffix.lower() != ".kt":
        raise ValueError("generated_kotlin_path must be a Kotlin .kt file.")

    source_summary = summarize_vector(source_file)
    generated_summary = summarize_vector(generated_file)
    report = _vector_diff_report(source_summary, generated_summary)

    render_check: dict[str, Any] = {"enabled": False, "passed": None, "details": "render check not executed"}
    with tempfile.TemporaryDirectory(prefix="s2c_mcp_verify_") as temp_dir:
        temp = Path(temp_dir)
        source_png = temp / "source.png"
        generated_png = temp / "generated.png"

        source_ok = False
        source_error = ""
        if source_file.suffix.lower() == ".svg":
            source_ok, source_error = _render_svg_to_png(source_file, source_png)
        else:
            source_error = "Source render currently supports SVG input via rsvg-convert only."

        generated_ok, generated_error = _render_kotlin_to_png(generated_file, generated_png)
        render_check["enabled"] = source_ok and generated_ok
        if source_ok and generated_ok:
            src_bytes = source_png.read_bytes()
            gen_bytes = generated_png.read_bytes()
            render_check["passed"] = src_bytes == gen_bytes
            render_check["details"] = "Byte-level PNG comparison executed."
            render_check["sourcePngSha256"] = hashlib.sha256(src_bytes).hexdigest()
            render_check["generatedPngSha256"] = hashlib.sha256(gen_bytes).hexdigest()
        else:
            render_check["passed"] = None
            render_check["details"] = {
                "sourceRender": "ok" if source_ok else source_error,
                "generatedRender": "ok" if generated_ok else generated_error,
            }

    if render_check["passed"] is False:
        report["faithful"] = False
        report["confidence"] = "high"

    return {
        "source": source_summary.as_dict(),
        "generated": generated_summary.as_dict(),
        "report": report,
        "renderCheck": render_check,
    }


def main() -> None:
    """Start the MCP server over stdio transport."""

    mcp.run(transport="stdio")


if __name__ == "__main__":
    main()
