---
name: svg-spec
description: Resolve SVG, Android Vector Drawable (AVG), and Compose ImageVector conversion questions for this repository. Use when work involves SVG path commands, transforms, gradients, viewBox/units, CSS style cascade/specificity, shape-to-path conversion, clipping/masking, or spec-compliance debugging in parsing and code generation.
---

# SVG Spec, Paths & Transforms

## Overview

Use this skill to implement or debug spec-compliant SVG/AVG behavior in
`svg-to-compose`. Prioritize W3C/Android/Compose source semantics and map the
issue to the project pipeline before changing code.

## Workflow

1. Classify the issue domain:
    - path data and command semantics
    - transforms and coordinate systems
    - gradients, paint, clipping/masking
    - CSS styling and specificity
    - AVG compatibility
2. Locate the corresponding project layer:
    - parser/AST (`parser/`, `lexer/`)
    - domain model (`domain/svg`, `domain/avg`)
    - geometry/transform/bounds (`geom/`)
    - Compose output mapping (`domain/compose`, generation)
3. Confirm expected behavior from canonical references.
4. Validate against nearby tests; add/update tests for behavior changes.
5. Run focused verification first (`--tests <FQN>`), then broader checks as
   needed.

## Practical Rules

- Treat spec links as source of truth when behavior is ambiguous.
- Preserve normalization order for paths and transforms.
- Keep conversions deterministic for stable generated output.
- Prefer fixing root semantic issues over output-only patches.

## Reference Files

Read only what is needed:

- [references/spec-reference.md](references/spec-reference.md): Complete
  W3C/Android/Compose references, pipeline map, and key files.
