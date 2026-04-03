# Release Checklist

> Copy this checklist into the release tracking issue for each release,
> replacing `X.Y.Z` with the actual version number.

## Prerequisites

- [ ] All PRs targeted for this release are merged to `main`
- [ ] `main` branch CI is green

## Phase 1: Prepare Release Branch

- [ ] Create release branch from `main`:
  ```bash
  git checkout main && git pull
  git checkout -b release/X.Y.Z
  ```
- [ ] Bump version in `app.properties`:
  ```properties
  VERSION=X.Y.Z
  ```
- [ ] Commit and push:
  ```bash
  git commit -am "chore: bump version to X.Y.Z"
  git push -u origin release/X.Y.Z
  ```

## Phase 2: Automated Verification

Run all checks on the release branch. Every command must exit with code 0.

### Static Analysis

- [ ] `./gradlew detekt`

### Unit Tests

- [ ] `./gradlew :svg-to-compose:allTests`

### Gradle Plugin Functional Tests

- [ ] `./gradlew :svg-to-compose-gradle-plugin:functionalTest`

### Native Binary Build

Build the native binary for your platform (required for integrity checks):

- [ ] `./gradlew releaseMacOS` (macOS)
- [ ] `./gradlew releaseLinux` (Linux)
- [ ] `./gradlew releaseWindows` (Windows)

### CLI Integrity Checks

These diff the CLI output against golden files in `integrity-check/expected/`:

- [ ] `./.github/actions/cli-integrity-check/script.sh . svg`
- [ ] `./.github/actions/cli-integrity-check/script.sh . xml`

With optimization (requires [svgo](https://github.com/svg/svgo) and
[avocado](https://github.com/alexjlockwood/avocado) installed):

- [ ] `./.github/actions/cli-integrity-check/script.sh . svg optimize`
- [ ] `./.github/actions/cli-integrity-check/script.sh . xml optimize`

## Phase 3: Integration Validation

These test real-world Gradle plugin behaviour that functional tests cannot fully
cover.

- [ ] Bootstrap the test repository (needed by included builds):
  ```bash
  export CI=true
  ./gradlew publishAllPublicationsToTestMavenRepository
  ```
- [ ] Build the playground app:
  ```bash
  ./gradlew -p playground :app:assembleDebug
  ```
- [ ] Run again and confirm tasks show `UP-TO-DATE` (incremental cache works):
  ```bash
  ./gradlew -p playground :app:assembleDebug --info 2>&1 | grep -E "UP-TO-DATE|is not up-to-date"
  ```
- [ ] Modify a sample file and rebuild; only the changed file should be
  reprocessed (Gradle uses content hashing, so `touch` alone is not enough):
  ```bash
  sed -i.bak '2i\
  <!-- release test -->' samples/svg/android.svg
  ./gradlew -p playground :app:assembleDebug --info 2>&1 | grep -E "parseSvgToComposeIcon.*(not up-to-date)|android\.svg has changed"
  git checkout samples/svg/android.svg && rm -f samples/svg/android.svg.bak
  ```
- [ ] Clean build after incremental (verifies clean state recovery):
  ```bash
  ./gradlew -p playground :app:clean :app:assembleDebug
  ```
- [ ] Build the playground-kmp app (all KMP targets: Android, iOS, JVM, JS,
  WasmJS):
  ```bash
  ./gradlew -p playground-kmp :composeApp:assemble
  ```
- [ ] Run again and confirm tasks show `UP-TO-DATE` (incremental cache works):
  ```bash
  ./gradlew -p playground-kmp :composeApp:assemble --info 2>&1 | grep -E "UP-TO-DATE|is not up-to-date"
  ```
- [ ] Modify a sample file and rebuild; only the changed file should be
  reprocessed:
  ```bash
  sed -i.bak '2i\
  <!-- release test -->' samples/svg/android.svg
  ./gradlew -p playground-kmp :composeApp:assemble --info 2>&1 | grep -E "parseSvgToComposeIcon.*(not up-to-date)|android\.svg has changed"
  git checkout samples/svg/android.svg && rm -f samples/svg/android.svg.bak
  ```
- [ ] Clean build after incremental (verifies clean state recovery):
  ```bash
  ./gradlew -p playground-kmp :composeApp:clean :composeApp:assemble
  ```

## Phase 4: Publish Smoke Test

Publish to Maven Local and verify the artifacts are generated correctly.

- [ ] Bootstrap the test repository (skip if Phase 3 already ran in this
  session):
  ```bash
  export CI=true
  ./gradlew publishAllPublicationsToTestMavenRepository
  ```
- [ ] Publish to Maven Local:
  ```bash
  ./gradlew publishAllToMavenLocal
  ```
- [ ] Verify artifacts:
  ```bash
  ls ~/.m2/repository/dev/tonholo/s2c/svg-to-compose/X.Y.Z/
  ls ~/.m2/repository/dev/tonholo/s2c/svg-to-compose-gradle-plugin/X.Y.Z/
  ```

## Phase 5: Tag and Release

- [ ] Tag and push:
  ```bash
  git tag X.Y.Z
  git push origin X.Y.Z
  ```
- [ ] Wait for the
  [`release.v2.yml`](../.github/workflows/release.v2.yml) workflow to
  complete. It builds native binaries for all platforms and creates a
  **draft** GitHub release.
- [ ] Review the draft release notes on GitHub
- [ ] **Publish** the release

## Phase 6: Post-Release Verification

Publishing the release triggers two additional workflows automatically:

| Workflow                                                                            | What it does                                        |
|-------------------------------------------------------------------------------------|-----------------------------------------------------|
| [`publish.yml`](../.github/workflows/publish.yml)                                   | Publishes to Maven Central and Gradle Plugin Portal |
| [`publish-package-managers.yml`](../.github/workflows/publish-package-managers.yml) | Dispatches Homebrew tap and Scoop bucket updates    |

- [ ] Verify `publish.yml` workflow completed successfully
- [ ] Verify `publish-package-managers.yml` workflow completed successfully
- [ ] Verify artifacts
  on [Maven Central](https://central.sonatype.com/artifact/dev.tonholo.s2c/svg-to-compose)
- [ ] Verify plugin
  on [Gradle Plugin Portal](https://plugins.gradle.org/plugin/dev.tonholo.s2c)
- [ ] Verify `brew install s2c` works (after tap update propagates)
- [ ] Verify `scoop install s2c` works (after bucket update propagates)

## Quick Reference

Full local validation in a single script:

```bash
# Automated checks
./gradlew detekt
./gradlew :svg-to-compose:allTests
./gradlew :svg-to-compose-gradle-plugin:functionalTest
./gradlew releaseMacOS
./.github/actions/cli-integrity-check/script.sh . svg
./.github/actions/cli-integrity-check/script.sh . xml

# Integration
./gradlew -p playground :app:assembleDebug
./gradlew -p playground-kmp :composeApp:assemble

# Publish smoke test
./gradlew publishAllToMavenLocal
```
