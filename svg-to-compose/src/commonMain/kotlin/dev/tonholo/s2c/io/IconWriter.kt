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
                        fileManager.createDirectories(parent, mustCreate = true)
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
