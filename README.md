# Switch Emulator Android Wrapper

A native Android app wrapper that launches Nintendo Switch emulators with ROM support.

## Features

- 📱 **Native Android UI** - Built with Jetpack Compose
- 🎮 **ROM Library Scanning** - Auto-detects .nsp, .xci, .nsz files
- ⚙️ **Multiple Emulator Support**:
  - Ryujinx
  - Yuzu
  - Skyline
- 📁 **Flexible ROM Management** - Point to any folder
- 🔧 **Custom Launch Arguments** - Configure per-emulator settings
- 💾 **Persistent Configuration** - Saves your settings

## What this app does

This is a **ROM launcher wrapper**, not an emulator. It:

1. Scans directories for Nintendo Switch ROM files
2. Displays them in a clean, native Android interface
3. Launches a compatible external emulator with the selected ROM
4. Manages emulator configuration and ROM metadata

## Requirements

- Android 8.0 (API level 26) or higher
- A compatible emulator app installed (Ryujinx, Yuzu, or Skyline)
- ROM files in .nsp, .xci, or .nsz format

## Installation

### Build from source

```bash
cd switch-emulator-android
gradelew build
gradelew installDebug  # Install on connected device
```

### Download APK

Release APKs are available on the [Releases](https://github.com/Funkykongoffical/switch-emulator-android/releases) page.

## Setup Instructions

1. **Install an emulator app:**
   - Ryujinx: Not available on Google Play; side-load APK
   - Yuzu: Not available on Google Play; side-load APK
   - Skyline: Available on GitHub

2. **Open Switch Emulator Native**

3. **Tap Settings (gear icon)**

4. **Configure:**
   - Select emulator type
   - Set emulator executable path (or package name for installed apps)
   - Select your ROM folder
   - (Optional) Customize launch arguments

5. **Tap Save**

6. **Back to library, tap Scan ROMs**

7. **Tap Play on any game**

## Project Structure

```
app/src/main/java/com/emulator/switch/
├── MainActivity.kt           # Entry point
├── model/
│   ├── RomEntry.kt          # ROM file model
│   └── EmulatorConfig.kt     # Configuration model
├── viewmodel/
│   └── RomLibraryViewModel.kt # Business logic
├── ui/
│   ├── RomLibraryScreen.kt   # Main ROM list UI
│   ├── SettingsActivity.kt   # Settings UI
│   └── theme/                # Material Design 3 theme
└── utils/
    ├── PreferenceManager.kt  # Persistent storage
    └── RomScanner.kt         # Filesystem scanning
```

## Configuration

Settings are saved in SharedPreferences and include:

- **Emulator Path**: Full path to emulator executable
- **ROM Folder**: Directory containing your ROMs
- **Launch Arguments**: Template string with `{{rom}}` placeholder
- **Emulator Type**: Which emulator backend to use

## Supported ROM Formats

- `.nsp` - Nintendo Switch Package (installed)
- `.xci` - Nintendo Switch cartridge image
- `.nsz` - Compressed .nsp format

## Legal Notice

This app is a launcher only. It does not include emulation code. Users must:

- Own legal copies of games they wish to play
- Use ROMs only for games they own
- Install emulators from official sources
- Comply with local copyright laws

## Contributing

Contributions are welcome! Please:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

MIT License - See LICENSE file for details

## Disclaimer

This project is for educational purposes. Users are responsible for ensuring their use complies with applicable laws and the terms of service of the emulator projects they use.
