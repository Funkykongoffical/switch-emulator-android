package com.emulator.switch.utils

import android.content.Context
import com.emulator.switch.model.EmulatorConfig
import com.google.gson.Gson

class PreferenceManager(private val context: Context) {
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveConfig(config: EmulatorConfig) {
        val json = gson.toJson(config)
        preferences.edit()
            .putString(CONFIG_KEY, json)
            .apply()
    }

    fun loadConfig(): EmulatorConfig {
        val json = preferences.getString(CONFIG_KEY, null) ?: return EmulatorConfig()
        return try {
            gson.fromJson(json, EmulatorConfig::class.java)
        } catch (e: Exception) {
            EmulatorConfig()
        }
    }

    companion object {
        private const val PREFS_NAME = "switch_emulator_prefs"
        private const val CONFIG_KEY = "emulator_config"
    }
}
