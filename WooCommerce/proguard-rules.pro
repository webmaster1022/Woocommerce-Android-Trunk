-dontobfuscate

###### OkHttp - begin
-dontwarn okio.**
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }

-keepattributes Signature
-keepattributes *Annotation*
###### OkHttp - end

###### Event Bus 3
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
###### Event Bus 3 - end

###### Event Bus 2 - begin
-keepclassmembers class ** {
    public void onEvent*(**);
}

# Only required if you use AsyncExecutor
-keepclassmembers class * extends de.greenrobot.event.util.ThrowableFailureEvent {
    ** *(java.lang.Throwable);
}
###### Event Bus 2 - end

###### FluxC (was needed for Json deserializers) - begin
-keep class org.wordpress.android.fluxc** { *; }
###### FluxC - end

###### FluxC - WellSql (needed for Addon support) - begin
-keep class com.wellsql** { *; }
###### FluxC - end

###### Dagger - begin
-dontwarn com.google.errorprone.annotations.*
###### Dagger - end

-keep class com.google.common.** { *; }
-dontwarn com.google.common.**

###### Zendesk - begin
-keep class com.zendesk.** { *; }
-keep class zendesk.** { *; }
-keep class javax.inject.Provider
-keep class com.squareup.picasso.** { *; }
-keep class com.jakewharton.disklrucache.** { *; }
-keep class com.google.gson.** { *; }
-keep class okio.** { *; }
-keep class retrofit2.** { *; }
-keep class uk.co.senab.photoview.** { *; }
###### Zendesk - end

###### Encrypted Logs - begin
-dontwarn java.awt.*
-keep class com.sun.jna.* { *; }
-keepclassmembers class * extends com.sun.jna.* { public *; }
###### Encrypted Logs - end

###### Glide - begin
-keep class com.bumptech.glide.GeneratedAppGlideModuleImpl { *; }
###### Glide - end

###### SavedStateHandleExt - begin
###### We use reflection so we have to keep this method
-keepclassmembers class * extends androidx.navigation.NavArgs {
    fromSavedStateHandle(androidx.lifecycle.SavedStateHandle);
}
###### SavedStateHandleExt - end
