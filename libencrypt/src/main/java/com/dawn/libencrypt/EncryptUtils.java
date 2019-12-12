package com.dawn.libencrypt;

import android.content.Context;
import android.widget.Toast;

import com.dawn.libencrypt.utils.AppReflect;

public class EncryptUtils {
    static {
        System.loadLibrary("lib-encrypt");
    }

    public static native String sign(String str);

    public static native boolean init(Context context);

    public static byte[] encryptData(byte[] data) {
        return encryptData(AppReflect.sAppContext, data);
    }

    public static byte[] decryptData(byte[] data) {
        return decryptData(AppReflect.sAppContext, data);
    }

    public static String getSign(String data) {
        return getSign(AppReflect.sAppContext, data);
    }

    public static String getDeviceId() {
        return "deviceId";
    }

    public static String getAppVersion() {
        return "1.0";
    }

    public static String getChannel() {
        return "huawei";
    }

    public static void showToast(String tips) {
        Toast.makeText(AppReflect.sAppContext, tips, Toast.LENGTH_SHORT).show();
    }

    native private static byte[] encryptData(Context context, byte[] data);
    native private static byte[] decryptData(Context context, byte[] data);
    native private static String getSign(Context context, String data);
}

