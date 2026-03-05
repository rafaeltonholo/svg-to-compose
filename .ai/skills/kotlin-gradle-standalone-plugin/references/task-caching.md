# Gradle Task Caching Reference

## Preconditions for Cacheable Tasks

- Task inputs are fully declared with `@Input*` annotations.
- Task outputs are fully declared with `@Output*` annotations.
- Task action is deterministic for a given set of inputs.
- Task avoids reading/writing undeclared files.

## Typical Task Properties

```kotlin
@CacheableTask
abstract class GenerateTask : DefaultTask() {
    @get:InputFile
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val sourceFile: RegularFileProperty

    @get:Input
    abstract val packageName: Property<String>

    @get:OutputFile
    abstract val outputFile: RegularFileProperty

    @TaskAction
    fun run() {
        // Generate deterministic output from declared inputs
    }
}
```

## Annotation Checklist

- File input: `@InputFile` / `@InputFiles` with `@PathSensitive(...)`
- Directory input: `@InputDirectory`
- Scalar input: `@Input`
- Optional input: `@Optional`
- Non-input runtime state: `@Internal`
- Output file/dir: `@OutputFile` / `@OutputDirectory`

## Common Cache Breakers

- Using absolute paths in output content (prefer relative or normalized paths).
- Reading system time/random values in task action.
- Reading files not declared as inputs.
- Writing extra files not declared as outputs.
- Environment-dependent behavior without modeled inputs.

## Verification Ideas

- Run task twice and expect second execution `UP_TO_DATE`.
- Enable build cache and verify repeated builds can use cached outputs.
- Validate behavior on clean checkout with same inputs.
