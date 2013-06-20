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
package com.marakana.android.logcommon;

import android.os.Parcel;
import android.os.Parcelable;


/**
 *
 * @version $Revision: $
 * @author <a href="mailto:blake.meike@gmail.com">G. Blake Meike</a>
 */
public class LogMessage implements Parcelable {
    public static final String SERVICE = "com.marakana.android.logservice";

    public static enum Type { LOG_JAVA, LOG_NATIVE; }

    public static final Parcelable.Creator<LogMessage> CREATOR
        = new Parcelable.Creator<LogMessage>() {

        @Override
        public LogMessage createFromParcel(Parcel parcel) {
            Type type = Type.values()[parcel.readInt()];
            int level = parcel.readInt();
            String tag = parcel.readString();
            String message = parcel.readString();
            return new LogMessage(type, level, tag, message);
        }

        @Override
        public LogMessage[] newArray(int size) {
            return new LogMessage[size];
        }
    };


    private Type type;
    private final int level;
    private final String tag;
    private final String message;

    public LogMessage(Type type, int level, String tag, String message) {
        this.type = type;
        this.level = level;
        this.tag = tag;
        this.message = message;
    }

    public Type getType() { return type; }

    public int getLevel() { return level; }

    public String getTag() { return tag; }

    public String getMessage() { return message; }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(type.ordinal());
        parcel.writeInt(level);
        parcel.writeString(tag);
        parcel.writeString(message);
    }
}
