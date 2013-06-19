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
package com.marakana.android.fibonaccicommon;

import android.os.Parcel;
import android.os.Parcelable;


/**
 *
 * @version $Revision: $
 * @author <a href="mailto:blake.meike@gmail.com">G. Blake Meike</a>
 */
public class FibonacciResponse implements Parcelable {
    public static final Parcelable.Creator<FibonacciResponse> CREATOR
        = new Parcelable.Creator<FibonacciResponse>() {

            @Override
            public FibonacciResponse createFromParcel(Parcel parcel) {
                long time = parcel.readLong();
                long result = parcel.readLong();
                return new FibonacciResponse(time, result);
            }

            @Override
            public FibonacciResponse[] newArray(int size) {
                return new FibonacciResponse[size];
            }
    };

    private final long time;
    private final long result;

    public FibonacciResponse(long time, long result) {
        this.time = time;
        this.result = result;
    }

    public long getTime() { return time; }

    public long getResult() { return result; }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(time);
        parcel.writeLong(result);
    }
}
