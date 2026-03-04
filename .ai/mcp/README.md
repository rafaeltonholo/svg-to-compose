# Model Context Protocol (MCP) Tools

This folder contains MCP client/server wiring for `svg-to-compose`.

## Active Server

The active local MCP server is located at:

- `.ai/tools/mcp-vector-server`

It exposes:

1. `analyze_vector`
2. `render_preview`
3. `verify_conversion`

## Setup

1. Create and activate a virtual environment inside `.ai/tools/mcp-vector-server`.
2. Install the package in editable mode (`pip install -e .`).
3. Copy `mcp-config.json` into your MCP client's config location, or merge its
   `mcpServers` entry.

## Notes

- `render_preview` and render-based verification require `S2C_KOTLIN_RENDER_CMD`.
- No default Kotlin render command is configured in this repo; set `S2C_KOTLIN_RENDER_CMD`
  in your MCP client config based on your local rendering pipeline.
- Source SVG render checks use `rsvg-convert` when available.
