package dev.tonholo.s2c.website.state.playground

import dev.tonholo.s2c.website.worker.ConversionInput
import dev.tonholo.s2c.website.worker.ConversionOutput
import dev.tonholo.s2c.website.worker.IconConvertWorker

/**
 * A pool of [IconConvertWorker] instances that dispatches conversion
 * inputs to idle workers, enabling parallel batch processing.
 *
 * Each worker runs in a separate Web Worker thread. The pool
 * maintains a queue of pending inputs and assigns them to workers
 * as they become available.
 *
 * All callbacks fire on the JS main thread, so no synchronisation
 * is required for state updates.
 *
 * @param poolSize Number of worker instances to create.
 * @param onOutput Called for every [ConversionOutput] emitted by a
 *   worker. The [Int] parameter is the file index originally
 *   submitted via [submitAll].
 * @param onDrained Called when the pool has finished all work:
 *   the pending queue is empty and no workers are active.
 */
internal class WorkerPool(
    poolSize: Int = DEFAULT_POOL_SIZE,
    private val onOutput: (fileIndex: Int, ConversionOutput) -> Unit,
    private val onDrained: () -> Unit,
) {
    private val workers: List<IconConvertWorker>
    private val pendingQueue = ArrayDeque<IndexedInput>()
    private val activeAssignments = mutableMapOf<Int, Int>() // workerId -> fileIndex
    private val idleWorkers = ArrayDeque<Int>()
    private var cancelled = false
    private var terminated = false

    init {
        workers = List(poolSize) { workerId ->
            IconConvertWorker { output ->
                handleWorkerOutput(workerId, output)
            }
        }
        idleWorkers.addAll(workers.indices)
    }

    /**
     * Enqueues all [inputs] for conversion. Each entry maps a file
     * index (used in callbacks) to the [ConversionInput] payload.
     * Immediately dispatches up to [poolSize] files to idle workers.
     *
     * If [inputs] is empty, [onDrained] fires immediately.
     *
     * @throws IllegalStateException if the pool has been [terminate]d.
     */
    fun submitAll(inputs: List<IndexedInput>) {
        check(!terminated) { "WorkerPool has been terminated" }
        cancelled = false
        pendingQueue.clear()
        activeAssignments.clear()
        idleWorkers.clear()
        idleWorkers.addAll(workers.indices)

        if (inputs.isEmpty()) {
            onDrained()
            return
        }

        pendingQueue.addAll(inputs)
        dispatchPending()
    }

    /**
     * Cancels the current batch. Clears the pending queue and
     * ignores results from workers that are still active.
     * If no workers are active, [onDrained] fires immediately.
     */
    fun cancel() {
        cancelled = true
        pendingQueue.clear()
        if (activeAssignments.isEmpty()) {
            onDrained()
        }
    }

    /** Terminates all underlying Web Workers. */
    fun terminate() {
        terminated = true
        cancelled = true
        pendingQueue.clear()
        activeAssignments.clear()
        workers.forEach { it.terminate() }
    }

    /** @return The number of workers currently processing a file. */
    val activeCount: Int get() = activeAssignments.size

    private fun handleWorkerOutput(workerId: Int, output: ConversionOutput) {
        val fileIndex = activeAssignments[workerId] ?: return

        when (output) {
            is ConversionOutput.Progress -> {
                if (!cancelled) {
                    onOutput(fileIndex, output)
                }
            }

            is ConversionOutput.Success,
            is ConversionOutput.Error,
            -> {
                if (!cancelled) {
                    onOutput(fileIndex, output)
                }
                activeAssignments.remove(workerId)
                idleWorkers.addLast(workerId)
                if (!cancelled) {
                    dispatchPending()
                }
                checkDrain()
            }
        }
    }

    private fun checkDrain() {
        if (activeAssignments.isEmpty() && pendingQueue.isEmpty()) {
            onDrained()
        }
    }

    private fun dispatchPending() {
        while (idleWorkers.isNotEmpty() && pendingQueue.isNotEmpty()) {
            val workerId = idleWorkers.removeFirst()
            val (fileIndex, input) = pendingQueue.removeFirst()
            activeAssignments[workerId] = fileIndex
            workers[workerId].postInput(input)
        }
    }

    companion object {
        const val DEFAULT_POOL_SIZE = 4
    }
}

/** A file index paired with its [ConversionInput]. */
internal typealias IndexedInput = Pair<Int, ConversionInput>
