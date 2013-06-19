package com.marakana.android.fibonacciservice;

import com.marakana.android.fibonaccicommon.FibonacciRequest;
import com.marakana.android.fibonaccicommon.FibonacciResponse;
import com.marakana.android.fibonaccicommon.IFibonacciService;
import com.marakana.android.fibonaccinative.FibLib;

public class IFibonacciServiceImpl extends IFibonacciService.Stub {

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
        return FibLib.fibJI(n);
    }

    @Override
    public long fibJR(long n) {
        return FibLib.fibJR(n);
    }

    @Override
    public long fibNI(long n) {
        return FibLib.fibNI(n);
    }

    @Override
    public long fibNR(long n) {
        return FibLib.fibNR(n);
    }
}
