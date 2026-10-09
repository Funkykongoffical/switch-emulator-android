package com.emulator.switch.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.emulator.switch.model.EmulatorConfig
import com.emulator.switch.utils.PreferenceManager
import com.emulator.switch.ui.theme.SwitchEmulatorTheme

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferenceManager = PreferenceManager(this)
        val initialConfig = preferenceManager.loadConfig()

        setContent {
            SwitchEmulatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SettingsScreen(
                        initialConfig = initialConfig,
                        onSave = {
                            preferenceManager.saveConfig(it)
                            finish()
                        },
                        onBack = { finish() }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    initialConfig: EmulatorConfig,
    onSave: (EmulatorConfig) -> Unit,
    onBack: () -> Unit
) {
    var emulatorPath by remember { mutableStateOf(initialConfig.emulatorPath) }
    var romFolder by remember { mutableStateOf(initialConfig.romFolder) }
    var launchArgs by remember { mutableStateOf(initialConfig.launchArgs) }
    var selectedEmulator by remember { mutableStateOf(initialConfig.emulatorType) }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Settings") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Emulator", style = MaterialTheme.typography.titleMedium)

            EmulatorSelector(
                selected = selectedEmulator,
                onSelected = { selectedEmulator = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = emulatorPath,
                onValueChange = { emulatorPath = it },
                label = { Text("Emulator Path") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            TextField(
                value = romFolder,
                onValueChange = { romFolder = it },
                label = { Text("ROM Folder") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            TextField(
                value = launchArgs,
                onValueChange = { launchArgs = it },
                label = { Text("Launch Arguments") },
                modifier = Modifier.fillMaxWidth(),
                helperText = { Text("Use {{rom}} as placeholder for ROM path") }
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val config = EmulatorConfig(
                        emulatorPath = emulatorPath,
                        romFolder = romFolder,
                        launchArgs = launchArgs,
                        emulatorType = selectedEmulator
                    )
                    onSave(config)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}

@Composable
fun EmulatorSelector(
    selected: EmulatorConfig.EmulatorType,
    onSelected: (EmulatorConfig.EmulatorType) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        EmulatorConfig.EmulatorType.values().forEach { emulator ->
            RadioButton(
                selected = selected == emulator,
                onClick = { onSelected(emulator) },
                modifier = Modifier.padding(8.dp)
            )
            Text(emulator.displayName)
        }
    }
}
