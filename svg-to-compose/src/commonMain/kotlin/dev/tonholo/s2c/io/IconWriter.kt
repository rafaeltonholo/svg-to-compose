package dev.tonholo.s2c.io

import com.rsicarelli.fakt.Fake
import dev.tonholo.s2c.extensions.isDirectory
import dev.tonholo.s2c.extensions.pascalCase
import dev.tonholo.s2c.logger.Logger
import dev.zacsweers.metro.Inject
import okio.Path

@Fake
interface IconWriter {
    fun write(iconName: String, fileContents: String, output: Path): Path
}

@Inject
class DefaultIconWriter(private val logger: Logger, private val fileManager: FileManager) : IconWriter {
    override fun write(iconName: String, fileContents: String, output: Path): Path {
        logger.printEmpty()
        logger.output("📝 Writing icon file on $output")
        return logger.debugSection("Writing document") {
            val outputExists = fileManager.exists(output)
            logger.verbose("outputExists=$outputExists")
            if (output.isDirectory && outputExists.not()) {
                logger.printEmpty()
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

            logger.printEmpty()
            logger.output("✅ Done writing the file")
            targetFile
        }
    }
}
