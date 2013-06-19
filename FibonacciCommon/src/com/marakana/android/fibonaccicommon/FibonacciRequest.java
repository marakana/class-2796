package com.marakana.android.fibonaccicommon;

import android.os.Parcel;
import android.os.Parcelable;


public class FibonacciRequest implements Parcelable {
    public static final String SERVICE = "com.marakana.android.fibonacciservice";


    public static enum Type {
        RECURSIVE_JAVA, ITERATIVE_JAVA, RECURSIVE_NATIVE, ITERATIVE_NATIVE;
    }

    public static final Parcelable.Creator<FibonacciRequest> CREATOR
        = new Parcelable.Creator<FibonacciRequest>() {

            @Override
            public FibonacciRequest createFromParcel(Parcel parcel) {
                long n = parcel.readLong();
                Type type = Type.values()[parcel.readInt()];
                return new FibonacciRequest(type, n);
            }

            @Override
            public FibonacciRequest[] newArray(int size) {
                return new FibonacciRequest[size];
            }
    };

    private final Type type;
    private final long n;

    public FibonacciRequest(Type type, long n) {
        this.type = type;
        this.n = n;
    }

    public Type getType() { return type; }

    public long getN() { return n; }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(n);
        parcel.writeInt(type.ordinal());
    }
}
