package com.emulator.switch.model

import java.io.File

data class RomEntry(
    val path: String,
    val title: String,
    val extension: String,
    val sizeBytes: Long,
    val lastModified: Long
) {
    val displaySize: String
        get() = when {
            sizeBytes < 1024 -> "$sizeBytes B"
            sizeBytes < 1024 * 1024 -> "${sizeBytes / 1024} KB"
            sizeBytes < 1024 * 1024 * 1024 -> "${sizeBytes / (1024 * 1024)} MB"
            else -> "${sizeBytes / (1024 * 1024 * 1024)} GB"
        }

    companion object {
        private val SUPPORTED_EXTENSIONS = setOf("nsp", "xci", "nsz")

        fun fromFile(file: File): RomEntry? {
            val ext = file.extension.lowercase()
            if (ext !in SUPPORTED_EXTENSIONS) return null

            val title = file.nameWithoutExtension.replace("_", " ")
            return RomEntry(
                path = file.absolutePath,
                title = title,
                extension = ext.uppercase(),
                sizeBytes = file.length(),
                lastModified = file.lastModified()
            )
        }
    }
}
