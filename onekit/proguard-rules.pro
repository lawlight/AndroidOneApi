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

-libraryjars 'D:\Program Files\Android\Android Studio\jre\jre\lib\rt.jar'
-libraryjars 'D:\android\sdk\platforms\android-22\android.jar'

-keep class top.onehundred.android.kits.**{*;}


# 保留Annotation不混淆
-keepattributes *Annotation*,InnerClasses
-ignorewarnings
-dontshrink
-optimizationpasses 5
-dontusemixedcaseclassnames

-optimizations !class/unboxing/enum


# 保留我们使用的四大组件，自定义的Application等等这些类不被混淆
# 因为这些子类都有可能被外部调用
#
-keep public class * extends android.app.Appliction
-keep public class * extends android.content.BroadcastReceiver
-keep class android.support.v4.**{*;}
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepattributes Signature
-keepattributes *Annotation*

-keep class org.apache.commons.codec.**{*;}
-keep class org.apache.commons.logging.**{*;}
-keep class org.apache.http.**{*;}
-keep class org.codehaus.jackson.**{*;}
-keep class mozilla.**{*;}

-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep public class * implements java.io.Serializable {*;}
