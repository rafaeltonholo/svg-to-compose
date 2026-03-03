# Gradle Worker API Reference

## When to Use

Use Worker API when task work is CPU-intensive, parallelizable, or benefits from
process/classloader isolation.

## Worker Executor Injection

```kotlin
abstract class MyTask : DefaultTask() {
    @get:Inject
    abstract val workerExecutor: WorkerExecutor

    @TaskAction
    fun run() {
        val queue = workerExecutor.noIsolation()
        queue.submit(MyWorkAction::class.java) {
            inputFile.set(this@MyTask.inputFile)
            outputDir.set(this@MyTask.outputDir)
        }
    }
}
```

## Work Parameters

```kotlin
interface MyWorkParameters : WorkParameters {
    val inputFile: RegularFileProperty
    val outputDir: DirectoryProperty
}
```

## Work Action

```kotlin
abstract class MyWorkAction : WorkAction<MyWorkParameters> {
    override fun execute() {
        val input = parameters.inputFile.get().asFile
        val output = parameters.outputDir.get().asFile
        // Perform deterministic work
    }
}
```

## Isolation Modes

- `noIsolation()`: fastest, shared classloader/process.
- `classLoaderIsolation { ... }`: isolated classpath when dependencies conflict.
- `processIsolation { ... }`: strongest isolation with separate JVM process.

## Practical Guidance

- Pass only declared parameters into work actions.
- Keep work actions deterministic and side-effect scoped to declared outputs.
- Prefer submitting multiple independent work items for parallelism.
- Combine Worker API with proper task input/output annotations for incremental
  and cacheable behavior.
