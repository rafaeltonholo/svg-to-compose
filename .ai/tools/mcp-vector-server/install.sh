#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
VENV_DIR="${ROOT_DIR}/.venv"

if ! command -v python3 >/dev/null 2>&1; then
  echo "error: python3 not found in PATH" >&2
  exit 1
fi

echo "Creating virtual environment at ${VENV_DIR}"
python3 -m venv "${VENV_DIR}"

echo "Installing package in editable mode"
"${VENV_DIR}/bin/pip" install -e "${ROOT_DIR}"

echo
echo "Install completed."
echo "MCP server command:"
echo "  ${VENV_DIR}/bin/s2c-mcp-vector-server"
echo
echo "Optional: export render command before running:"
echo "  export S2C_KOTLIN_RENDER_CMD='<your-command-that-renders-kotlin> {input} {output}'"
