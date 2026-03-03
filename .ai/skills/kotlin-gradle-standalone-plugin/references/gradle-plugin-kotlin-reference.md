# Gradle Plugin Kotlin Reference

## Skeleton Structure

```text
my-plugin/
  src/main/kotlin/.../MyPlugin.kt
  src/main/kotlin/.../dsl/MyExtension.kt
  src/main/kotlin/.../tasks/MyTask.kt
  src/test/kotlin/... (unit)
  src/functionalTest/kotlin/... (TestKit)
```

## Plugin Entrypoint Pattern

```kotlin
class MyPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("myPlugin", MyExtension::class.java)

        project.tasks.register("myTask", MyTask::class.java) {
            inputDir.convention(extension.inputDir)
            outputFile.convention(extension.outputFile)
        }
    }
}
```

## Extension Pattern

```kotlin
abstract class MyExtension @Inject constructor(objects: ObjectFactory) {
    val inputDir: DirectoryProperty = objects.directoryProperty()
    val outputFile: RegularFileProperty = objects.fileProperty()
    val enabled: Property<Boolean> = objects.property(Boolean::class.java).convention(true)
}
```

## Task Pattern

```kotlin
abstract class MyTask : DefaultTask() {
    @get:InputDirectory
    abstract val inputDir: DirectoryProperty

    @get:OutputFile
    abstract val outputFile: RegularFileProperty

    @TaskAction
    fun run() {
        // Read inputDir, write outputFile deterministically
    }
}
```

## TestKit Pattern

```kotlin
val result = GradleRunner.create()
    .withProjectDir(testProjectDir)
    .withArguments("myTask", "--stacktrace")
    .withPluginClasspath()
    .forwardOutput()
    .build()
```

Assertions usually verify:

- task outcome (`SUCCESS`, `UP_TO_DATE`, `FROM_CACHE` when relevant)
- generated files/content
- failure messages for invalid configuration

## Common Pitfalls

- Calling `.get()` on properties during configuration.
- Using eager task creation.
- Mixing internal and public DSL packages.
- Missing input/output annotations, hurting incrementality/caching.
- Relying only on unit tests without TestKit behavior tests.

## Related References

- [gradle-worker-api.md](gradle-worker-api.md)
- [task-caching.md](task-caching.md)
