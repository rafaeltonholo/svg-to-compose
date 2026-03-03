# Kotlin KMP Testing Reference

## Source Set Placement

- Default: `commonTest` for shared logic in `svg-to-compose`.
- Use platform-specific source sets only when APIs force it.
- Keep test package path aligned with production package path.

## Assertion Guidance

- Scalars: `assertEquals(expected, actual)`
- Arrays/collections with ordered equality:
  `assertContentEquals(expected, actual)`
- Boolean predicates: `assertTrue(condition)`
- Nullability: `assertNull(value)` / `assertNotNull(value)`
- Error paths: `assertFailsWith<ExpectedException> { ... }`

## Burst Pattern

```kotlin
import app.cash.burst.Burst
import app.cash.burst.burstValues
import kotlin.test.Test
import kotlin.test.assertEquals

class ExampleTest {
    @Test
    @Burst
    fun `parse should map valid inputs`(
        params: Pair<String, Int> = burstValues(
            "a" to 1,
            "bb" to 2,
        ),
    ) {
        val (input, expected) = params

        val actual = input.length

        assertEquals(expected, actual)
    }
}
```

## Gradle Commands

```bash
# Run one test class in core module
./gradlew :svg-to-compose:allTests --tests "dev.tonholo.s2c.lexer.css.CssTokenizerTest"

# Run one test class in plugin module
./gradlew :svg-to-compose-gradle-plugin:test --tests "<fully.qualified.TestClass>"

# Run all multiplatform tests
./gradlew allTests
```

## Practical Checklist

- Cover success path and at least one edge/error path.
- Keep fixtures local to the test unless reused widely.
- Avoid over-mocking pure logic.
- Prefer test data that makes failures obvious.
