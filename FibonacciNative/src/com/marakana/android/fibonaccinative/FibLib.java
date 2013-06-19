package com.marakana.android.fibonaccinative;

import android.util.Log;

public class FibLib {
    private static final String TAG = "FIBLIB";

    static { System.loadLibrary("com_marakana_android_fibonaccinative_FibLib"); }

    public static native long fibNR(long n);

    public static native long fibNI(long n);

    public static long fibJR(long n) {
        Log.d(TAG, "Java Recursive: " + n);
        return fib(n);
    }

    public static long fibJI(long n) {
        Log.d(TAG, "Java Iterative: " + n);
        long p = -1;
        long result = 1;
        for (long i = 0; i <= n; i++) {
            long sum = result + p;
            p = result;
            result = sum;
        }
        return result;
    }

    private static long fib(long n) {
        return (n <= 0)
                ? 0
                : (n == 1)
                    ? 1
                    : fib(n - 1) + fib(n - 2);
    }
}
