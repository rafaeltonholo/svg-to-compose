package dev.tonholo.s2c.io

import dev.tonholo.s2c.extensions.isDirectory
import dev.tonholo.s2c.extensions.pascalCase
import dev.tonholo.s2c.logger.Logger
import dev.tonholo.s2c.logger.printEmpty
import okio.Path

class IconWriter(
    private val logger: Logger,
    private val fileManager: FileManager,
) {
    /**
     * Writes an icon Kotlin file to the given output path, creating missing directories as needed.
     *
     * If `output` is a directory, the file named "<IconNameInPascalCase>.kt" will be created inside it;
     * if `output` is a file, that path will be used directly. Missing directories are created (idempotently
     * for parent creation to tolerate concurrent workers).
     *
     * @param iconName The icon name; converted to PascalCase and used as the file name when `output` is a directory.
     * @param fileContents The Kotlin source content to write into the target file.
     * @param output The target file or directory path. If a directory, the final file will be created inside it.
     * @return The path of the file that was written.
     */
    fun write(
        iconName: String,
        fileContents: String,
        output: Path,
    ): Path {
        printEmpty()
        logger.output("📝 Writing icon file on $output")
        return logger.debugSection("Writing document") {
            val outputExists = fileManager.exists(output)
            logger.verbose("outputExists=$outputExists")
            if (output.isDirectory && outputExists.not()) {
                printEmpty()
                logger.output("📢 Output directory is missing. Creating it automatically.")
                fileManager.createDirectories(output)
            } else if (output.isDirectory.not()) {
                output.parent?.let { parent ->
                    logger.verbose("Checking if parent directory exists.")
                    if (fileManager.exists(parent).not()) {
                        logger.output("Output parent's directory doesn't exists. Creating.")
                        // Use idempotent directory creation to avoid races under parallel workers
                        // If another worker creates the directory between the exists() check
                        // and this call, createDirectories with mustCreate=false is a no-op.
                        fileManager.createDirectories(parent, mustCreate = false)
                    }
                }
            }

            val targetFile = if (output.isDirectory) {
                (output / "${iconName.pascalCase()}.kt").also {
                    logger.debug("Output is directory. Appending icon name to path. Target output = $it}")
                }
            } else {
                output
            }

            logger.debug("Writing..")

            fileManager.write(file = targetFile, mustCreate = false) {
                writeUtf8(fileContents)
            }

            printEmpty()
            logger.output("✅ Done writing the file")
            targetFile
        }
    }
}