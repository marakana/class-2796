package com.marakana.android.fibonacciclient;

import java.util.Locale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.marakana.android.fibonaccicommon.FibonacciRequest;
import com.marakana.android.fibonaccicommon.FibonacciResponse;
import com.marakana.android.fibonaccicommon.IFibonacciService;


public class FibonacciActivity extends Activity
    implements OnClickListener, ServiceConnection
{
    private static final String TAG = "UI";

    TextView output;
    IFibonacciService service;
    private EditText input;
    private RadioGroup type;
    private Button button;

    @Override
    public void onServiceConnected(ComponentName name, IBinder conn) {
        service = IFibonacciService.Stub.asInterface(conn);
        button.setEnabled(true);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) { }

    @Override
    public void onClick(View view) {
        if (null == service) { return; }

        String s = this.input.getText().toString();
        if (TextUtils.isEmpty(s)) { return; }

        final ProgressDialog dialog = ProgressDialog.show(this, "", "Calculating...", true);
        final Locale locale = super.getResources().getConfiguration().locale;

        FibonacciRequest.Type fibType = null;
        switch (FibonacciActivity.this.type.getCheckedRadioButtonId()) {
            case R.id.type_fib_jr:
                fibType = FibonacciRequest.Type.RECURSIVE_JAVA;
                break;
            case R.id.type_fib_ji:
                fibType = FibonacciRequest.Type.ITERATIVE_JAVA;
                break;
            case R.id.type_fib_nr:
                fibType = FibonacciRequest.Type.RECURSIVE_NATIVE;
                break;
            case R.id.type_fib_ni:
                fibType = FibonacciRequest.Type.ITERATIVE_NATIVE;
               break;
        }

        new AsyncTask<FibonacciRequest, Void, String>() {
            @Override
            protected String doInBackground(FibonacciRequest... params) {
                FibonacciRequest req = params[0];
                FibonacciResponse result = null;

                long t = SystemClock.uptimeMillis();
                try { result = service.fib(params[0]); }
                catch (RemoteException e) {
                    Log.e(TAG, "Remote request failed: ", e);
                    return "failed";
                }
                t = SystemClock.uptimeMillis() - t;

                long t1 = result.getTime();
                return String.format(
                        locale,
                        "fib(%d)=%d in %d ms, %d binder",
                        req.getN(), result.getResult(), t1, t - t1);
            }

            @Override
            protected void onPostExecute(String result) {
                dialog.dismiss();
                FibonacciActivity.this.output.setText(result);
            }
        }.execute(new FibonacciRequest(fibType, Long.parseLong(s)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.input = (EditText) super.findViewById(R.id.input);
        this.type = (RadioGroup) super.findViewById(R.id.type);
        this.output = (TextView) super.findViewById(R.id.output);
        button = (Button) super.findViewById(R.id.button);
        button.setOnClickListener(this);
        button.setEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "bind");
        if (!bindService(new Intent(FibonacciRequest.SERVICE), this, BIND_AUTO_CREATE)) {
            Log.e(TAG, "Failed to bind service");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "unbind");
        unbindService(this);
        service = null;
        button.setEnabled(false);
    }
}
