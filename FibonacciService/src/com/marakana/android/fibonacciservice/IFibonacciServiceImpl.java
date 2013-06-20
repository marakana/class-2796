package com.marakana.android.fibonacciservice;

import android.content.Context;

import com.marakana.android.fibonaccicommon.FibonacciRequest;
import com.marakana.android.fibonaccicommon.FibonacciResponse;
import com.marakana.android.fibonaccicommon.IFibonacciService;
import com.marakana.android.fibonaccinative.FibLib;

public class IFibonacciServiceImpl extends IFibonacciService.Stub {
    private final Context context;

    public IFibonacciServiceImpl(Context context) {
        super();
        this.context = context;
    }

    @Override
    public FibonacciResponse fib(FibonacciRequest req) {
        long time = System.currentTimeMillis();
        long result = 0;
        switch(req.getType()) {
            case ITERATIVE_JAVA:
                result = fibJI(req.getN());
                break;
            case ITERATIVE_NATIVE:
                result = fibNI(req.getN());
                break;
            case RECURSIVE_JAVA:
                result = fibJR(req.getN());
                break;
            case RECURSIVE_NATIVE:
                result = fibNR(req.getN());
                break;
        }

        return new FibonacciResponse(System.currentTimeMillis() - time, result);
    }

    @Override
    public long fibJI(long n) {
        return FibLib.fibJI(checkN(n, 7));
    }

    @Override
    public long fibJR(long n) {
        return FibLib.fibJR(checkN(n, 4));
    }

    @Override
    public long fibNI(long n) {
        return FibLib.fibNI(checkN(n, 13));
    }

    @Override
    public long fibNR(long n) {
        return FibLib.fibNR(checkN(n, 9));
    }

    private long checkN(long n, int max) {
        if (n > max) {
            this.context.enforceCallingOrSelfPermission(
                    Manifest.permission.USE_SLOW_FIBONACCI_SERVICE, "Just beat it!");
        }
        return n;
    }
}
