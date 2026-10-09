package com.emulator.switch.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emulator.switch.model.EmulatorConfig
import com.emulator.switch.model.RomEntry
import com.emulator.switch.utils.PreferenceManager
import com.emulator.switch.utils.RomScanner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

class RomLibraryViewModel : ViewModel() {
    private val _romList = MutableStateFlow<List<RomEntry>>(emptyList())
    val romList: StateFlow<List<RomEntry>> = _romList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _config = MutableStateFlow(EmulatorConfig())
    val config: StateFlow<EmulatorConfig> = _config.asStateFlow()

    private lateinit var preferenceManager: PreferenceManager
    private lateinit var romScanner: RomScanner

    fun initialize(context: Context) {
        preferenceManager = PreferenceManager(context)
        romScanner = RomScanner(context)
        _config.value = preferenceManager.loadConfig()
    }

    fun scanRoms() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val romFolder = _config.value.romFolder
                if (romFolder.isEmpty()) {
                    _errorMessage.value = "ROM folder not configured. Please set it in Settings."
                    _isLoading.value = false
                    return@launch
                }

                val file = File(romFolder)
                if (!file.exists() || !file.isDirectory) {
                    _errorMessage.value = "ROM folder does not exist: $romFolder"
                    _isLoading.value = false
                    return@launch
                }

                val roms = romScanner.scanDirectory(file)
                    .sortedBy { it.title.lowercase() }

                _romList.value = roms
                if (roms.isEmpty()) {
                    _errorMessage.value = "No compatible ROMs found. Supported: .nsp, .xci, .nsz"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error scanning ROMs: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun launchGame(context: Context, rom: RomEntry) {
        viewModelScope.launch {
            try {
                val emulatorPath = _config.value.emulatorPath
                if (emulatorPath.isEmpty()) {
                    _errorMessage.value = "Emulator path not configured. Please set it in Settings."
                    return@launch
                }

                val emulatorFile = File(emulatorPath)
                if (!emulatorFile.exists()) {
                    _errorMessage.value = "Emulator executable not found: $emulatorPath"
                    return@launch
                }

                // Try to launch emulator
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(Uri.fromFile(File(rom.path)), "application/*")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                // Fallback: Try to launch emulator package
                val emulatorIntent = context.packageManager.getLaunchIntentForPackage(
                    _config.value.emulatorType.packageName
                )
                if (emulatorIntent != null) {
                    emulatorIntent.putExtra("rom_path", rom.path)
                    emulatorIntent.putExtra("game_title", rom.title)
                    context.startActivity(emulatorIntent)
                } else {
                    _errorMessage.value = "Emulator not installed: ${_config.value.emulatorType.displayName}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error launching game: ${e.message}"
            }
        }
    }

    fun updateConfig(config: EmulatorConfig) {
        _config.value = config
        preferenceManager.saveConfig(config)
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
