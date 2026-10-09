package com.emulator.switch

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.emulator.switch.ui.RomLibraryScreen
import com.emulator.switch.ui.SettingsActivity
import com.emulator.switch.ui.theme.SwitchEmulatorTheme
import com.emulator.switch.viewmodel.RomLibraryViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Request permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.MANAGE_EXTERNAL_STORAGE
                ),
                PERMISSION_REQUEST_CODE
            )
        }

        setContent {
            SwitchEmulatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: RomLibraryViewModel = viewModel()
                    RomLibraryScreen(
                        viewModel = viewModel,
                        onSettingsClick = { navigateToSettings() }
                    )
                }
            }
        }
    }

    private fun navigateToSettings() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
    }
}
