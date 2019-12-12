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

#指定代码的压缩级别
-optimizationpasses 5
#预校验
-dontpreverify
-printmapping proguardMapping.txt
#指定混淆时采用的算法,后面的参数是一个过滤器
-optimizations !code/simplification/cast,!field/*,!class/merging/*
#保护注解
-keepattributes *Annotation*,InnerClasses
-keep class * extends java.lang.annotation.Annotation {*;}
#避免混淆泛型
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable

#混淆时是否记录日志
-verbose
#包名不混合大小写
-dontusemixedcaseclassnames
#指定不优化输入类文件。 默认情况下，启用优化; 所有方法都以字节码级别进行了优化。
-dontoptimize

#屏蔽警告
-ignorewarnings

#--------------------开启混淆日志记录配置------------
#记录生成的日志数据,gradle build 时会在本项目的根目录输出
#apk 包内所有class的内部结构
#-dump proguard/class_files.txt
#未混淆的类和成员
#-printseeds progurad/seeds.txt
#列出apk 中删除的代码
#-printusage proguard/unused.txt
#混淆前后的映射
#-printmapping proguard/mapping.txt


####################默认保护区######################
#继承了activity application service broadcastReceiver contentprovider 不混淆
-keep public class * extends android.app.Application
-keep public class * extends android.support.v7.app.AppCompatActivity
-keep public class * extends android.support.multidex.MultiDexApplication
-keep public class * extends android.support.v7.app.AppCompatActivity
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class * extends android.webkit.WebView

-keep public class com.android.vending.licensing.ILicensingService
#Android support 不混淆
-keep class android.support.** {*;}

-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

#在layout中的 Android:onclick="onClick"不要进行混淆
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

#保持Serializable不被混淆
-keepnames class * implements java.io.Serializable
#保持 Serializable 不被混淆并且
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
#保持枚举enum 类不被混淆
-keepclassmembers enum *{
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keepclassmembers class * {
    public void *ButtonClicked(android.view.View);
}
#保持Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
#Parcelable实现类除了不能混淆本身之外，为了确保类成员也能够被访问，类成员也不能被混淆
-keepclassmembers class * implements android.os.Parcelable {
 public <fields>;
 private <fields>;
}
#不混淆资源类
-keep class **.R$* {
 *;
}

-keepclassmembers class * {
    void *(*Event);
}

#native 方法不混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

#webview 不混淆
-keepclassmembers class android.webkit.WebView {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
    public void *(android.webkit.WebView, jav.lang.String);
}

#support-v4 不混淆
-dontwarn android.support.v4.**
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v4.** { *; }


#----------------------  我的实体bean   ------------------------

#实体类 bean 不要混淆 在GSON 中需要找对应的属性字段进行解析
-keep public class com.yty.writing.entity.**{*;}

#----------------------  第三方jar包   ------------------------
#不去忽略非公共的库类
-dontskipnonpubliclibraryclassmembers
-dontskipnonpubliclibraryclasses

# gson 不混淆
-dontwarn com.google.gson.**
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }

#dom4j 不混淆
-dontwarn org.dom4j.**
-keep class org.dom4j.** {*;}

#友盟
-dontwarn com.umeng.**
-keep class com.umeng.** {*;}

#bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

#event-bus3.1.1
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#okhttp okio
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
-dontwarn okio.**
-keep class okio.**{*;}

#retrofit
-dontwarn javax.annotation.**
-dontwarn javax.inject.**
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#glide如果你的API级别<=Android API 27 则需要添加
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder

#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#share-微信 share-qq
-dontwarn com.tencent.**
-keep class com.tencent.**{*;}


#share-Sina
-dontwarn com.weibo.sdk.Android.WeiboDialog
-dontwarn android.NET.http.SslError
-dontwarn android.webkit.WebViewClient
-keep public class android.Net.http.SslError{*;}
-keep public class android.webkit.WebViewClient{*;}
-keep public class android.webkit.WebChromeClient{*;}
-keep public interface android.webkit.WebChromeClient$CustomViewCallback {*;}
-keep public interface android.webkit.ValueCallback {*;}
-keep class * implements android.webkit.WebChromeClient {*;}
-keep class com.sina.weibo.sdk.api.** {*;}
-keep class com.sina.weibo.sdk.** {*;}


#不混淆 阿里支付
-dontwarn com.alipay.**
-keep class com.alipay.** { *;}
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.sdk.app.H5PayCallback {
    <fields>;
    <methods>;
}
-keep class com.alipay.android.phone.mrpc.core.** { *; }
-keep class com.alipay.apmobilesecuritysdk.** { *; }
-keep class com.alipay.mobile.framework.service.annotation.** { *; }
-keep class com.alipay.mobilesecuritysdk.face.** { *; }
-keep class com.alipay.tscenter.biz.rpc.** { *; }
-keep class org.json.alipay.** { *; }
-keep class com.alipay.tscenter.** { *; }
-keep class com.ta.utdid2.** { *;}
-keep class com.ut.device.** { *;}

#不混淆 awt
-dontwarn com.awt.**
-keep class com.awt.** { *;}

#不混淆 loading
-keep class com.wang.avi.indicators.** { *;}

#不混淆 sqlcipher加密数据库
-keep class net.sqlcipher.** { *; }
-keep class net.sqlcipher.database.* { *; }


