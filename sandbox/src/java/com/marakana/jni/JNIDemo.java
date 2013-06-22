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
package com.marakana.jni;

import java.lang.ref.SoftReference;


/**
 *
 * @version $Revision: $
 * @author <a href="mailto:blake.meike@gmail.com">G. Blake Meike</a>
 */
public class JNIDemo {

    private static JNIDemo demo;

    public static void foo() {
        // won't work!!!
        // demo = this;
    }

    public void bar(JNIDemo n) {
        demo = this;
    }

    public static void main(String[] args) {
        JNIDemo n = new JNIDemo();
        SoftReference<JNIDemo> m = new SoftReference<JNIDemo>(n);
        new JNIDemo().bar(n);
        JNIDemo p = m.get();
        System.out.println("Same: " + (n == m.get()));
    }
}



