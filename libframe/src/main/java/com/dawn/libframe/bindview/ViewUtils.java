package com.dawn.libframe.bindview;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ViewUtils {
    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);
    }

    public static void inject(View view) {
        inject(new ViewFinder(view), view);
    }

    public static void inject(View view, Object object) {
        inject(new ViewFinder(view), object);
    }

    private static void inject(ViewFinder viewFinder, Object object) {
        injectFiled(viewFinder, object);
        injectEvent(viewFinder, object);
    }

    /**
     * 注入属性
     *
     * @param finder
     * @param object
     */
    private static void injectEvent(ViewFinder finder, Object object) {
        // 1.获取里面的方法
        Class<?> clazz = object.getClass();
        // 2.获取类中的所有方法
        Method[] methods = clazz.getDeclaredMethods();
        // 遍历方法获取具有标记的方法
        for (Method method : methods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            // 2.获取onclick的值
            int[] viewIds = onClick.value();
            // 3.findViewById找到View
            for (int id : viewIds) {
                View view = finder.findViewById(id);
                // 4.setOnClickListener
                if (view != null) {
                    // 5.放射执行方法
                    view.setOnClickListener(new DeclaredOnClickListener(method, object));
                }
            }
        }
    }

    private static void injectFiled(ViewFinder finder, Object object) {
        // 1.遍历属性
        Class<?> clazz = object.getClass();
        // 获取所有属性
        Field[] fields = clazz.getDeclaredFields();
        // 获取viewById里面的value值
        for (Field field : fields) {
            Annotation viewById = field.getAnnotation(ViewById.class);
            if (viewById != null) {
                int viewId = ((ViewById) viewById).value();
                // 3.findViewById找到View
                View view = finder.findViewById(viewId);
                if (view != null) {
                    // 4.动态的注入找到的view
                    field.setAccessible(true);
                    try {
                        field.set(object, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static class DeclaredOnClickListener implements View.OnClickListener{
        private Object mObject;
        private Method mMethod;

        public DeclaredOnClickListener(Method method, Object object) {
            this.mObject = object;
            this.mMethod = method;
        }

        @Override
        public void onClick(View v) {
            try {
                mMethod.setAccessible(true);
                mMethod.invoke(mObject, mMethod);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
