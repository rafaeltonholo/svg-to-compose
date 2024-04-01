package dev.tonholo.s2c.parser.method

/**
 * Kotlin and Java impose a method bytecode size limit of `65535` (64 KiB).
 *
 * When generating large icons, it's crucial to consider this constraint.
 *
 * For a deeper understanding, refer to the section
 * [Limitations of the Java Virtual Machine](https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.11)
 * and [The Java® Virtual Machine Specification](https://docs.oracle.com/javase/specs/jvms/se21/html/index.html).
 */
interface MethodSizeAccountable {
    /**
     * Represents the approximate bytecode size accounted for by the current type or
     * instruction within a method.
     *
     * Each bytecode instruction contributes to this calculation, with different
     * types accounting for varying amounts of bytes.
     *
     * Due to the dynamic nature of bytecode generation, it's not possible to precisely
     * calculate the bytecode size without having a `.class` file.
     *
     * Therefore, we provide an approximate size based on the bytecode generation process.
     */
    val approximateByteSize: Int

    companion object {
        /**
         * We set a threshold at half the method size limit.
         *
         * Preview functions typically add around 13 KiB behind the scenes.
         * Exceeding this threshold may result in icons that can't be previewed
         * in Android Studio or IntelliJ, although they can compile and render on Android.
         */
        const val METHOD_SIZE_THRESHOLD = 32767
        /**
         * Represents the approximate byte size accounted for by a floating-point number instruction.
         */
        const val FLOAT_APPROXIMATE_BYTE_SIZE = 4

        /**
         * Represents the approximate byte size accounted for by a boolean value instruction.
         */
        const val BOOLEAN_APPROXIMATE_BYTE_SIZE = 2
    }
}
