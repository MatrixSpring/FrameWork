package com.dawn.libframe.checknet;

import java.lang.reflect.Method;

public class CheckNetUtils {

    public static void initCheckNet(Object object) {
        // 1.获取方法
        Class<?> clazz = object.getClass();
        // 2. 获取所有方法
        Method[] methods = clazz.getDeclaredMethods();
        // 3.遍历所有方法
        for (Method method : methods) {
            CheckNet checkNet = method.getAnnotation(CheckNet.class);
            if (checkNet != null) {
                // toast
            }
        }
    }

}
