package dev.tonholo.s2c.gradle.internal.service

import dev.tonholo.s2c.Processor
import okio.Path
import java.util.UUID
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertSame

class S2cWorkerBridgeTest {

    /** Minimal stub that records its identity for assertion. */
    private class StubProcessorFactory(val id: String) : Processor.Factory {
        override fun create(tempDirectory: Path?): Processor = error("Not expected to be called in this test")
    }

    @Test
    fun `register and get returns the same factory`() {
        val token = UUID.randomUUID().toString()
        val factory = StubProcessorFactory("single")
        S2cWorkerBridge.register(token, factory)
        try {
            assertSame(factory, S2cWorkerBridge.get(token))
        } finally {
            S2cWorkerBridge.unregister(token)
        }
    }

    @Test
    fun `get with unknown token throws IllegalArgumentException`() {
        val ex = assertFailsWith<IllegalArgumentException> {
            S2cWorkerBridge.get("nonexistent")
        }
        assertEquals(
            "No Processor.Factory registered for token 'nonexistent'. " +
                "Ensure the task calls S2cWorkerBridge.register() before submitting workers.",
            ex.message,
        )
    }

    @Test
    fun `unregister is idempotent`() {
        val token = UUID.randomUUID().toString()
        S2cWorkerBridge.register(token, StubProcessorFactory("idem"))
        S2cWorkerBridge.unregister(token)
        S2cWorkerBridge.unregister(token) // second call must not throw
    }

    @Test
    fun `concurrent register-get-unregister isolates each token`() {
        val threadCount = 20
        val latch = CountDownLatch(threadCount)
        val errors = mutableListOf<Throwable>()

        val threads = (0 until threadCount).map { i ->
            thread {
                try {
                    val token = UUID.randomUUID().toString()
                    val factory = StubProcessorFactory("thread-$i")
                    S2cWorkerBridge.register(token, factory)
                    try {
                        repeat(100) {
                            val retrieved = S2cWorkerBridge.get(token)
                            assertSame(factory, retrieved, "Thread $i got a foreign factory")
                            assertEquals("thread-$i", (retrieved as StubProcessorFactory).id)
                        }
                    } finally {
                        S2cWorkerBridge.unregister(token)
                    }
                } catch (t: Throwable) {
                    synchronized(errors) { errors.add(t) }
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()
        threads.forEach { it.join() }

        if (errors.isNotEmpty()) {
            throw AssertionError(
                "${errors.size} thread(s) failed",
                errors.first(),
            )
        }
    }

    @Test
    fun `one token remains stable while another registers and unregisters`() {
        val stableToken = UUID.randomUUID().toString()
        val stableFactory = StubProcessorFactory("stable")
        S2cWorkerBridge.register(stableToken, stableFactory)

        val iterations = 500
        val errors = mutableListOf<Throwable>()
        val latch = CountDownLatch(2)

        val churner = thread {
            try {
                repeat(iterations) {
                    val token = UUID.randomUUID().toString()
                    S2cWorkerBridge.register(token, StubProcessorFactory("churn-$it"))
                    S2cWorkerBridge.unregister(token)
                }
            } catch (t: Throwable) {
                synchronized(errors) { errors.add(t) }
            } finally {
                latch.countDown()
            }
        }

        val reader = thread {
            try {
                repeat(iterations) {
                    val retrieved = S2cWorkerBridge.get(stableToken)
                    assertSame(stableFactory, retrieved, "Stable token returned wrong factory at iteration $it")
                }
            } catch (t: Throwable) {
                synchronized(errors) { errors.add(t) }
            } finally {
                latch.countDown()
            }
        }

        latch.await()
        churner.join()
        reader.join()
        S2cWorkerBridge.unregister(stableToken)

        if (errors.isNotEmpty()) {
            throw AssertionError(
                "${errors.size} thread(s) failed",
                errors.first(),
            )
        }
    }
}
