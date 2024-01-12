
#keep class 'MainActivity'
-keep class
com.amine.geminidroid.MainActivity

#keep method 'onCreate()'
-keepclassmembers class
com.amine.geminidroid.MainActivity{
  onCreate(android.os.Bundle);
}

-keepclassmembers class
com.amine.geminidroid.camera.CameraViewModel


# ADVICE: start obsfuscation with models and enums

-keep class com.amine.geminidroid.ui.theme.ThemeKt

-keep class com.google.ai.client.generativeai.**
{*;}

-obfuscationdictionary
dictionary.txt

-optimizations

# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile