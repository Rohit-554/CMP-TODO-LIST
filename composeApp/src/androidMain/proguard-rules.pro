# remove ui previews and tooling
-assumenosideeffects class androidx.compose.ui.tooling.** { *; }
-assumenosideeffects class androidx.compose.ui.tooling.preview.** { *; }
-assumenosideeffects class androidx.compose.ui.inspector.** { *; }
-dontwarn androidx.compose.ui.tooling.**
-dontwarn androidx.compose.ui.tooling.preview.**
-dontwarn androidx.compose.ui.inspector.**

# strip preview annotations
-keepclassmembers class * { @androidx.compose.ui.tooling.preview.Preview *; }

# remove material icon packs overload
-dontwarn androidx.compose.material.icons.**
-keep class androidx.compose.material.icons.** { *; }

# remove compose runtime debug traces
-assumenosideeffects class androidx.compose.runtime.Recomposer { *; }

# remove kotlin intrinsics overhead
-assumenosideeffects class kotlin.jvm.internal.Intrinsics { *; }

# remove coroutines debug
-assumenosideeffects class kotlinx.coroutines.debug.** { *; }
-dontwarn kotlinx.coroutines.debug.**

# keep serialization only
-keep class kotlinx.serialization.** { *; }
-keepclassmembers class * implements kotlinx.serialization.KSerializer { *; }

# keep navigation core
-keep class androidx.navigation.** { *; }
-dontwarn androidx.navigation.**

# remove compose inspector reflection
-assumenosideeffects class androidx.compose.ui.platform.inspector.** { *; }
-dontwarn androidx.compose.ui.platform.inspector.**

# strip tooling data resources
-dontwarn androidx.compose.ui.tooling.data.**
-assumenosideeffects class androidx.compose.ui.tooling.data.** { *; }


# remove coil gif/video decoders if present
-dontwarn coil.gif.**
-dontwarn coil.video.**
