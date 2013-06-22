package com.example.androidsandbox;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;


public class MyService extends IntentService {

    private static final String PARAM_MSG = "MyService.MESSAGE";
    private static final String PARAM_FOO = "MyService.FOO";
    private static final String PARAM_BAR = "MyService.BAR";

    public static void post(Context ctxt, String message, Parcelable foo, Object bar) {
        Intent intent = new Intent("theFooService");
        intent.putExtra(PARAM_MSG, message);
        intent.putExtra(PARAM_FOO, foo);
        // can't work!
        // intent.putExtra(PARAM_BAR, bar);
        ctxt.startService(intent);
    }


    public MyService() { super("MYSERVICE"); }

     /// runs on daemon thread.
    @Override
    protected void onHandleIntent(Intent intent) {
        doPost(
                intent.getStringExtra(PARAM_MSG),
                intent.getParcelableExtra(PARAM_FOO));
    }


    private void doPost(String stringExtra, Parcelable foo) {
        // TODO Auto-generated method stub

    }

}
