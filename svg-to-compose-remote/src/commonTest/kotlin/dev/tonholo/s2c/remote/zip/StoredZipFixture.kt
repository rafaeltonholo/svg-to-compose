package dev.tonholo.s2c.remote.zip

import okio.Buffer

private const val LOCAL_FILE_HEADER_SIGNATURE = 0x04034b50
private const val CENTRAL_DIRECTORY_SIGNATURE = 0x02014b50
private const val END_OF_CENTRAL_DIRECTORY_SIGNATURE = 0x06054b50
private const val ZIP_VERSION = 20
private const val DOS_DATE_1980_01_01 = 0x21
private const val CRC32_POLYNOMIAL = -0x12477CE0 // 0xEDB88320
private const val BITS_PER_BYTE = 8

private val crc32Table = IntArray(size = 256) { index ->
    var value = index
    repeat(times = BITS_PER_BYTE) {
        value = if (value and 1 != 0) (value ushr 1) xor CRC32_POLYNOMIAL else value ushr 1
    }
    value
}

private fun crc32(bytes: ByteArray): Int {
    var crc = -1
    for (byte in bytes) {
        crc = crc32Table[(crc xor byte.toInt()) and 0xFF] xor (crc ushr 8)
    }
    return crc.inv()
}

/**
 * Builds an uncompressed (stored) ZIP archive from entry name to text content,
 * so extraction tests do not depend on binary fixture files.
 */
fun storedZip(entries: Map<String, String>): ByteArray {
    val buffer = Buffer()
    val centralRecords = entries.map { (name, content) ->
        writeLocalEntry(buffer = buffer, name = name, content = content)
    }
    val centralDirectoryOffset = buffer.size.toInt()
    centralRecords.forEach { record -> record.writeCentralEntry(buffer) }
    val centralDirectorySize = buffer.size.toInt() - centralDirectoryOffset
    buffer.writeEndOfCentralDirectory(
        entryCount = centralRecords.size,
        centralDirectorySize = centralDirectorySize,
        centralDirectoryOffset = centralDirectoryOffset,
    )
    return buffer.readByteArray()
}

private class CentralRecord(
    val nameBytes: ByteArray,
    val crc: Int,
    val size: Int,
    val localHeaderOffset: Int,
) {
    fun writeCentralEntry(buffer: Buffer) {
        buffer.writeIntLe(CENTRAL_DIRECTORY_SIGNATURE)
        buffer.writeShortLe(ZIP_VERSION)
        buffer.writeShortLe(ZIP_VERSION)
        buffer.writeShortLe(0)
        buffer.writeShortLe(0)
        buffer.writeShortLe(0)
        buffer.writeShortLe(DOS_DATE_1980_01_01)
        buffer.writeIntLe(crc)
        buffer.writeIntLe(size)
        buffer.writeIntLe(size)
        buffer.writeShortLe(nameBytes.size)
        buffer.writeShortLe(0)
        buffer.writeShortLe(0)
        buffer.writeShortLe(0)
        buffer.writeShortLe(0)
        buffer.writeIntLe(0)
        buffer.writeIntLe(localHeaderOffset)
        buffer.write(nameBytes)
    }
}

private fun writeLocalEntry(buffer: Buffer, name: String, content: String): CentralRecord {
    val nameBytes = name.encodeToByteArray()
    val data = content.encodeToByteArray()
    val record = CentralRecord(
        nameBytes = nameBytes,
        crc = crc32(data),
        size = data.size,
        localHeaderOffset = buffer.size.toInt(),
    )
    buffer.writeIntLe(LOCAL_FILE_HEADER_SIGNATURE)
    buffer.writeShortLe(ZIP_VERSION)
    buffer.writeShortLe(0)
    buffer.writeShortLe(0)
    buffer.writeShortLe(0)
    buffer.writeShortLe(DOS_DATE_1980_01_01)
    buffer.writeIntLe(record.crc)
    buffer.writeIntLe(record.size)
    buffer.writeIntLe(record.size)
    buffer.writeShortLe(nameBytes.size)
    buffer.writeShortLe(0)
    buffer.write(nameBytes)
    buffer.write(data)
    return record
}

private fun Buffer.writeEndOfCentralDirectory(
    entryCount: Int,
    centralDirectorySize: Int,
    centralDirectoryOffset: Int,
) {
    writeIntLe(END_OF_CENTRAL_DIRECTORY_SIGNATURE)
    writeShortLe(0)
    writeShortLe(0)
    writeShortLe(entryCount)
    writeShortLe(entryCount)
    writeIntLe(centralDirectorySize)
    writeIntLe(centralDirectoryOffset)
    writeShortLe(0)
}
