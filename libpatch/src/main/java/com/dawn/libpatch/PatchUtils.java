package com.dawn.libpatch;

public class PatchUtils {
    static {
        System.loadLibrary("patchLibrary");
    }

    public synchronized static native boolean make(String oldFilePath, String newFilePath, String patchPath);

    public synchronized static native boolean diff(String oldFilePath, String newFilePath, String patchPath);
}
