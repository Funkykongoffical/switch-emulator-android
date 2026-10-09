package com.emulator.switch.model

import com.google.gson.annotations.SerializedName

data class EmulatorConfig(
    @SerializedName("emulator_path")
    val emulatorPath: String = "",

    @SerializedName("rom_folder")
    val romFolder: String = "",

    @SerializedName("launch_args")
    val launchArgs: String = "--load {{rom}}",

    @SerializedName("emulator_type")
    val emulatorType: EmulatorType = EmulatorType.RYUJINX
) {
    enum class EmulatorType(val displayName: String, val packageName: String) {
        RYUJINX("Ryujinx", "org.ryujinx.emulator"),
        YUZU("Yuzu", "org.yuzu.yuzu_emu"),
        SKYLINE("Skyline", "emu.skyline")
    }
}
