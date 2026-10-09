# Gson rules
-keep class com.google.gson.** { *; }
-keep class com.emulator.switch.model.** { *; }
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Coroutines
-keep class kotlinx.coroutines.** { *; }
