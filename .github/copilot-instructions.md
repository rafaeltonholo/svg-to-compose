# SVG-to-Compose — Copilot Instructions

CRITICAL: MUST read and follow `.ai/guidelines.md` for all code style,
architecture, testing, and commit conventions in this repository.

Read `AGENTS.md` for repository overview, module map, verification workflow, and
available skills.

## Key Rules

- **Kotlin Multiplatform** — core logic in `commonMain`, use Okio for file I/O,
  no `java.io` in common code.
- **Convention plugins** - build config lives in `build-logic/`, don't duplicate in
  module build files.
- **Detekt strict mode** — zero issues allowed. Config in `config/detekt.yml`.
- **Conventional Commits** — `<type>(<scope>): <subject>`.
- **kotlin-test** with Power Assert and Burst for parameterized tests.
- Max line length: 120 characters.

## Module Docs

Before modifying a module, read its `AGENTS.md`:

- `svg-to-compose/AGENTS.md` — core library
- `svg-to-compose-gradle-plugin/AGENTS.md` — Gradle plugin
- `build-logic/AGENTS.md` - convention plugins
- `playground/AGENTS.md` — Android demo
- `playground-kmp/AGENTS.md` — KMP demo
