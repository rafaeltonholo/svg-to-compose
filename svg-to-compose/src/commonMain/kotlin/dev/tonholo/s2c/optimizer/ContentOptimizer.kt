package dev.tonholo.s2c.optimizer

import dev.tonholo.s2c.domain.FileType

/**
 * Optimizes raw vector markup content (SVG or AVG) before parsing.
 *
 * This is the content-based counterpart to [Optimizer], which operates on files
 * via external CLI tools. Implementations process in-memory string content,
 * for example by invoking SVGO's browser API in a web worker.
 *
 * @see Optimizer
 * @see FileType
 */
fun interface ContentOptimizer {
    /**
     * Optimizes vector markup content for the given [fileType].
     *
     * Optimization may include simplifying paths, removing unnecessary metadata,
     * adjusting precision, or other transformations that reduce complexity while
     * preserving visual fidelity.
     *
     * @param content the raw SVG or AVG markup to optimize.
     * @param fileType determines which optimization strategy to apply.
     * @return the optimized markup content.
     */
    suspend fun optimize(content: String, fileType: FileType): String
}
