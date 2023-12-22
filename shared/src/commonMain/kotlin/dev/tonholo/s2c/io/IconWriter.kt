package dev.tonholo.s2c.io

import dev.tonholo.s2c.extensions.isDirectory
import dev.tonholo.s2c.extensions.pascalCase
import dev.tonholo.s2c.logger.debug
import dev.tonholo.s2c.logger.debugEndSection
import dev.tonholo.s2c.logger.debugSection
import dev.tonholo.s2c.logger.output
import dev.tonholo.s2c.logger.printEmpty
import dev.tonholo.s2c.logger.verbose
import okio.FileSystem
import okio.Path

class IconWriter(
    private val fileSystem: FileSystem = FileSystem.SYSTEM
) {
    fun write(
        iconName: String,
        fileContents: String,
        output: Path,
    ) {
        printEmpty()
        output("📝 Writing icon file on $output")
        debugSection("Writing document")

        val outputExists = fileSystem.exists(output)
        verbose("outputExists=$outputExists")
        if (output.isDirectory && outputExists.not()) {
            printEmpty()
            output("📢 Output directory is missing. Creating it automatically.")
            fileSystem.createDirectory(output)
        }

        val targetFile = if (output.isDirectory) {
            (output / "${iconName.pascalCase()}.kt").also {
                debug("Output is directory. Appending icon name to path. Target output = $it}")
            }
        } else {
            output
        }

        debug("Writing..")

        fileSystem.write(file = targetFile, mustCreate = false) {
            writeUtf8(fileContents)
        }

        printEmpty()
        output("✅ Done writing the file")
        debugEndSection()
    }
}
