package com.emulator.switch.utils

import android.content.Context
import com.emulator.switch.model.RomEntry
import java.io.File

class RomScanner(private val context: Context) {
    private val supportedExtensions = setOf("nsp", "xci", "nsz")

    fun scanDirectory(directory: File): List<RomEntry> {
        val roms = mutableListOf<RomEntry>()
        scanDirectoryRecursive(directory, roms)
        return roms
    }

    private fun scanDirectoryRecursive(directory: File, roms: MutableList<RomEntry>) {
        try {
            directory.listFiles()?.forEach { file ->
                when {
                    file.isDirectory -> scanDirectoryRecursive(file, roms)
                    file.isFile && file.extension.lowercase() in supportedExtensions -> {
                        val romEntry = RomEntry.fromFile(file)
                        if (romEntry != null) {
                            roms.add(romEntry)
                        }
                    }
                }
            }
        } catch (e: SecurityException) {
            // Handle permission denied
        }
    }
}
