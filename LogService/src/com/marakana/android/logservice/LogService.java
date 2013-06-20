package com.marakana.android.logservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class LogService extends Service {
    private ILogServiceImpl service;

    @Override
    public void onCreate() {
        super.onCreate();
        service = new ILogServiceImpl();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return service;
    }
}
