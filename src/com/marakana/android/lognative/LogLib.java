
package com.marakana.android.lognative;

import android.util.Log;

// javah -jni -classpath bin/classes -d jni com.marakana.android.lognative.LogLib

public class LogLib {
    static { System.loadLibrary("LogLib"); }

    public static void logJ(int priority, String tag, String msg) {
        Log.println(priority, tag, msg);
    }

    public static native void logN(int priority, String tag, String msg);
}
