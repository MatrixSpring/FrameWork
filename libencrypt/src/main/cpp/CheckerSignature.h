//
//

#include <jni.h>
#include "Utils.h"

#ifndef WINANDROID_ENVCHECKER_H
#define WINANDROID_ENVCHECKER_H

class CheckerSignature {

public:

    //判断是否合法调用
    static bool isValid(JNIEnv* env, jobject context);

private:
    static jstring getPackageName(JNIEnv* env, jobject context, BaseClasses classes);
    static bool checkSign(JNIEnv* env, jobject context, BaseClasses classes);
    static jstring  getSign(JNIEnv* env, jobject context, BaseClasses classes);
    static bool checkPkgName(JNIEnv* env, jobject context, BaseClasses classes);
};


#endif //WINANDROID_ENVCHECKER_H
