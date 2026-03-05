# svg-to-compose MCP Vector Server

Local MCP server for AI agents working on `svg-to-compose`.

## Tools

### `analyze_vector`
- Input: `path` to `.svg`, `.xml` (AVG/vector drawable), or generated `.kt`.
- Output: JSON summary with:
  - `viewport`
  - `bounds`
  - `pathCount`
  - `usedColors`

### `render_preview`
- Input: `path` to generated Kotlin `ImageVector` file (`.kt`).
- Output: base64 PNG payload.
- Requirement: `S2C_KOTLIN_RENDER_CMD` must be set.

`S2C_KOTLIN_RENDER_CMD` is a command template with `{input}` and `{output}` placeholders, for example:

```bash
export S2C_KOTLIN_RENDER_CMD='/absolute/path/to/your-render-script.sh {input} {output}'
```

### `verify_conversion`
- Input: `source_path` (`.svg`/`.xml`) and `generated_kotlin_path` (`.kt`).
- Output: fidelity report with:
  - structural checks (viewport/bounds/pathCount/colors)
  - optional render check

Render check behavior:
- source SVG render uses `rsvg-convert` when available.
- Kotlin render uses `S2C_KOTLIN_RENDER_CMD`.
- if both renders succeed, PNG bytes are compared and SHA-256 hashes are included.

## Installation

From the repository root:

```bash
cd .ai/tools/mcp-vector-server
./install.sh
```

Manual installation:

```bash
cd .ai/tools/mcp-vector-server
python -m venv .venv
source .venv/bin/activate
pip install -e .
```

## Run

```bash
s2c-mcp-vector-server
```

Server uses `stdio` transport.

## Connect as Local MCP Server

Example generic local server config:

```json
{
  "mcpServers": {
    "s2c-vector-tools": {
      "command": "/absolute/path/to/.ai/tools/mcp-vector-server/.venv/bin/s2c-mcp-vector-server",
      "env": {
        "S2C_KOTLIN_RENDER_CMD": "/absolute/path/to/your-render-script.sh {input} {output}"
      }
    }
  }
}
```

Use absolute command paths when possible.

## Minimal Python MCP Client Example

```python
import anyio
from mcp import ClientSession, StdioServerParameters
from mcp.client.stdio import stdio_client


async def main():
    server = StdioServerParameters(
        command="/absolute/path/to/.ai/tools/mcp-vector-server/.venv/bin/s2c-mcp-vector-server"
    )

    async with stdio_client(server) as (reader, writer):
        async with ClientSession(reader, writer) as session:
            await session.initialize()
            tools = await session.list_tools()
            print([tool.name for tool in tools.tools])

            result = await session.call_tool(
                "analyze_vector",
                {"path": "/absolute/path/to/samples/svg/attention-filled.svg"},
            )
            print(result)


anyio.run(main)
```
