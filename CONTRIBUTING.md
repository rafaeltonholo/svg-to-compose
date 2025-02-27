<!-- omit toc -->

# Contributing to svg-to-compose

First off, thanks for taking the time to contribute! ❤️

All types of contributions are encouraged and valued. See
the [Table of Contents](#table-of-contents) for different ways to help and
details about how this project handles them. Please make sure to read the
relevant section before making your contribution. It will make it a lot easier
for us maintainers and smooth out the experience for all involved. The community
looks forward to your contributions. 🎉

> And if you like the project, but just don't have time to contribute, that's
> fine. There are other easy ways to support the project and show your
> appreciation, which we would also be very happy about:
> - Star the project
> - Tweet about it
> - Refer this project in your project's readme
> - Mention the project at local meetups and tell your friends/colleagues

<!-- omit toc -->

## Table of Contents

* [How to Contribute](#how-to-contribute)
* [Quick Start / Development Setup](#quick-start--development-setup)
  * [Prerequisites](#prerequisites)
  * [Clone and build](#clone-and-build)
  * [Linting and code style](#linting-and-code-style)
* [I Have a Question](#i-have-a-question)
* [Reporting Bugs](#reporting-bugs)
  * [Before Submitting a Bug Report](#before-submitting-a-bug-report)
  * [How Do I Submit a Good Bug Report?](#how-do-i-submit-a-good-bug-report)
* [Suggesting Enhancements](#suggesting-enhancements)
  * [Before Submitting an Enhancement](#before-submitting-an-enhancement)
  * [How Do I Submit a Good Enhancement Suggestion?](#how-do-i-submit-a-good-enhancement-suggestion)
* [Working on Code](#working-on-code)
  * [Branching Strategy and PR Workflow](#branching-strategy-and-pr-workflow)
  * [Commit Message Conventions](#commit-message-conventions)
  * [Code Style and CI Expectations](#code-style-and-ci-expectations)
  * [Updating Docs and Samples](#updating-docs-and-samples)
* [Security Disclosure](#security-disclosure)
* [Pull Request Checklist](#pull-request-checklist)
* [Code of Conduct](#code-of-conduct)
* [Attribution](#attribution)

## How to Contribute

There are several ways to help:

- Have a question? See [I Have a Question](#i-have-a-question).
- Found a bug? See [Reporting Bugs](#reporting-bugs) and use the issue templates.
- Have an idea? See [Suggesting Enhancements](#suggesting-enhancements).
- Want to improve docs? See [Updating Docs and Samples](#updating-docs-and-samples).
- Want to contribute code? See [Working on Code](#working-on-code).

## Quick Start / Development Setup

### Prerequisites

- Operating systems: macOS, Linux, or Windows
- Java Development Kit (JDK): 11 or newer (17 recommended). The build targets
  Java 8 bytecode in places, but using JDK 11+ is fine. If you face toolchain
  issues with Android tooling, switch to JDK 17.
- Git
- An IDE with Kotlin support (IntelliJ IDEA recommended)

### Clone and build

```bash
git clone https://github.com/rafaeltonholo/svg-to-compose.git
cd svg-to-compose
./gradlew build
```

Modules of interest:

- svg-to-compose — Kotlin Multiplatform CLI library/tooling
- svg-to-compose-gradle-plugin — Gradle plugin

Useful tasks:

- Build everything: `./gradlew build`
- Run tests: `./gradlew test`
- Static analysis (Detekt): `./gradlew detekt`
- Merge Detekt reports (CI helper): `./gradlew mergeDetektReport` (if available)
- Generate docs (Dokka): `./gradlew dokkaHtml`
- Publish to Maven Local (for local testing): `./gradlew publishAllToMavenLocal`
- Run CLI (help): `./s2c --help`

If you only work on one module, use the module qualified task name (e.g.,
`:svg-to-compose:build`).

### Linting and code style

- The project uses Detekt with formatting rules
  (see `config/detekt.yml`). Run `./gradlew detekt` before pushing.
- Follow Kotlin Official code style (use IDE's Kotlin style formatter).
- Fix reported issues until the build is green. PRs should be detekt‑clean.

## I Have a Question

> If you want to ask a question, we assume that you have read the
> available: [Library Documentation](./svg-to-compose/README.md)
> and [Gradle Plugin Documentation](./svg-to-compose-gradle-plugin/README.md).

Before you ask a question, it is best to search for
existing [Issues](https://github.com/rafaeltonholo/svg-to-compose/issues) that
might help you. In case you have found a suitable issue and still need
clarification, you can write your question in this issue.

If you then still feel the need to ask a question and need clarification, we
recommend the following:

- Open an [Issue](https://github.com/rafaeltonholo/svg-to-compose/issues/new).
- Provide as much context as you can about what you're running into.
- Provide project and platform versions (OS, svg-to-compose version, etc),
  depending on what seems relevant.

We will then take care of the issue as soon as possible.

## Reporting Bugs

### Before Submitting a Bug Report

A good bug report shouldn't leave others needing to chase you up for more
information. Therefore, we ask you to investigate carefully, collect information
and describe the issue in detail in your report. Please complete the following
steps in advance to help us fix any potential bug as fast as possible.

> [!IMPORTANT]
> To see if other users have experienced (and potentially already solved) the
> same issue you are having, check if there is not already a bug report existing
> for your bug or error in the [bug tracker](https://github.com/rafaeltonholo/svg-to-compose/issues?q=label%3Abug).

- Make sure that you are using the latest version.
- Determine if your bug is really a bug and not an error on your side e.g. using
  incompatible versions, wrong configuration, etc (Make sure that you have read
  the [documentation](https://github.com/rafaeltonholo/svg-to-compose). If you
  are looking for support, you might want to check [this section](#i-have-a-question)).
- Collect information about the bug:
  - Stack trace (Traceback)
  - OS, Platform and Version (Windows, Linux, macOS, x86, ARM)
  - Version of the svg-to-compose you are using
  - Versions of Android SDK you are targeting
  - Possibly your input and the output
  - Can you reliably reproduce the issue? And can you also reproduce it with older
    versions?

### How Do I Submit a Good Bug Report?

> You must never report security related issues, vulnerabilities or bugs
> including sensitive information to the issue tracker, or elsewhere in public.
> Instead sensitive bugs must be sent by email to <svg-to-compose@tonholo.dev>.
<!-- You may add a PGP key to allow the messages to be sent encrypted as well. -->

We use GitHub issues to track bugs and errors. If you run into an issue with the
project:

- Open an [Issue](https://github.com/rafaeltonholo/svg-to-compose/issues/new). (
  Since we can't be sure at this point whether it is a bug or not, we ask you
  not to talk about a bug yet and not to label the issue.)
- Explain the behavior you would expect and the actual behavior.
- Please provide as much context as possible and describe the *reproduction
  steps* that someone else can follow to recreate the issue on their own. This
  usually includes your input/output and/or yours Gradle Plugin configuration. 
  For good bug reports you should isolate the problem and create a reduced
  test case.
- Provide the information you collected in the previous section.

Once it's filed:

- The project team will label the issue accordingly.
- A team member will try to reproduce the issue with your provided steps. If
  there are no reproduction steps or no obvious way to reproduce the issue, the
  team will ask you for those steps and mark the issue as `needs-repro`. Bugs
  with the `needs-repro` tag will not be addressed until they are reproduced.
- If the team is able to reproduce the issue, it will be marked `needs-fix`, as
  well as possibly other tags (such as `critical`), and the issue will be left
  to be [implemented by someone](#working-on-code).

<!-- You might want to create an issue template for bugs and errors that can be used as a guide and that defines the structure of the information to be included. If you do so, reference it here in the description. -->

## Suggesting Enhancements

This section guides you through submitting an enhancement suggestion for
svg-to-compose, **including completely new features and minor improvements to
existing functionality**. Following these guidelines will help maintainers and
the community to understand your suggestion and find related suggestions.

### Before Submitting an Enhancement

- Make sure that you are using the latest version.
- Read the [documentation](https://github.com/rafaeltonholo/svg-to-compose)
  carefully and find out if the functionality is already covered, maybe by an
  individual configuration.
- Perform a [search](https://github.com/rafaeltonholo/svg-to-compose/issues) to
  see if the enhancement has already been suggested. If it has, add a comment to
  the existing issue instead of opening a new one.
- Find out whether your idea fits with the scope and aims of the project. It's
  up to you to make a strong case to convince the project's developers of the
  merits of this feature. Keep in mind that we want features that will be useful
  to the majority of our users and not just a small subset. If you're just
  targeting a minority of users, consider writing an add-on/plugin library.

### How Do I Submit a Good Enhancement Suggestion?

Enhancement suggestions are tracked
as [GitHub issues](https://github.com/rafaeltonholo/svg-to-compose/issues).

- Use a **clear and descriptive title** for the issue to identify the
  suggestion.
- Provide a **step-by-step description of the suggested enhancement** in as many
  details as possible.
- **Describe the current behavior** and **explain which behavior you expected to
  see instead** and why. At this point you can also tell which alternatives do
  not work for you.
- You may want to **include screenshots or screen recordings** which help you
  demonstrate the steps or point out the part which the suggestion is related
  to. You can use [LICEcap](https://www.cockos.com/licecap/) to record GIFs on
  macOS and Windows, and the
  built-in [screen recorder in GNOME](https://help.gnome.org/users/gnome-help/stable/screen-shot-record.html.en)
  or [SimpleScreenRecorder](https://github.com/MaartenBaert/ssr) on
  Linux. <!-- this should only be included if the project has a GUI -->
- **Explain why this enhancement would be useful** to most svg-to-compose users.
  You may also want to point out the other projects that solved it better and
  which could serve as inspiration.

<!-- You might want to create an issue template for enhancement suggestions that can be used as a guide and that defines the structure of the information to be included. If you do so, reference it here in the description. -->

## Working on Code

### Branching Strategy and PR Workflow

- Fork the repo and create a branch from `main`.
- Branch names: `feat/<short-topic>`, `fix/<short-topic>`, `docs/<short-topic>`,
  `chore/<short-topic>`
- Keep changes focused and reasonably small. Large changes should be discussed
  in an issue first.
- Keep your branch up to date by rebasing on `main` (prefer rebase over merge).
- Open a Pull Request targeting `main`. Fill out the PR description and link the
  related issue (e.g., `Closes #123`).
- Ensure CI is green: build, tests, and detekt must pass.

### Commit Message Conventions

Use Conventional Commits:

- `feat:` a new feature
- `fix:` a bug fix
- `docs:` documentation only changes
- `refactor:` code change that neither fixes a bug nor adds a feature
- `perf:` performance improvement
- `test:` adding or correcting tests
- `chore:` changes to build process, tooling, or dependencies

Examples:

- `feat(cli): add --opt flag to enable SVGO optimization`
- `fix(parser): handle path commands with scientific notation`
- `docs(readme): clarify plugin configuration example`

Include scope when it helps (module or area), keep subject under ~72 chars, and
use the imperative mood.

### Code Style and CI Expectations

- Kotlin style: Kotlin Official
- Static analysis: Detekt must pass with no new warnings
- Tests: add/adjust tests for new behavior; unit tests must pass
- Docs: update README or module docs when behavior or flags change
- Build: `./gradlew build` must succeed

### Updating Docs and Samples

- Module docs live under each module's README. Root README provides overview.
- API docs are generated via Dokka: `./gradlew dokkaHtml`.
- If you add new flags/behaviors, update examples in `README.md` and module
  READMEs. If you add example assets, place them under `samples/`.

## Security Disclosure

Please report vulnerabilities privately to
[svg-to-compose@tonholo.dev](mailto:svg-to-compose@tonholo.dev). Do not open a
public issue. We will acknowledge receipt within a reasonable time and work with
you on a coordinated disclosure.

## Pull Request Checklist

- [ ] Linked issue (e.g., `Closes #123`) and clear description of changes
- [ ] `./gradlew build` passes locally
- [ ] `./gradlew test` passes; tests added/updated as needed
- [ ] `./gradlew detekt` is clean (no new violations)
- [ ] Docs updated (README, module docs, samples) if behavior changed
- [ ] Screenshots or logs included if UI/CLI output changed
- [ ] No unrelated changes in the same PR

## Code of Conduct

By participating in this project, you agree to abide by our
[Code of Conduct](./CODE_OF_CONDUCT.md). To report unacceptable behavior, please
email [svg-to-compose@tonholo.dev](mailto:svg-to-compose@tonholo.dev).

## Attribution

This guide is based on the [contributing.md](https://contributing.md/generator)!
