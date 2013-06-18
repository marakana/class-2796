/* $Id: $
   Copyright 2013, G. Blake Meike

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.marakana.android.fibonaccinative;

import android.util.Log;


/**
 *
 * @version $Revision: $
 * @author <a href="mailto:blake.meike@gmail.com">G. Blake Meike</a>
 */
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
