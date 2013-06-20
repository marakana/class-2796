
package com.marakana.android.logclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import com.marakana.android.logcommon.ILogService;
import com.marakana.android.logcommon.LogMessage;

public class LogActivity extends Activity implements OnClickListener, ServiceConnection {
    private static final String TAG = "LogActivity";

    private static final int[] LOG_LEVEL = {
        Log.VERBOSE, Log.DEBUG, Log.INFO, Log.WARN, Log.ERROR
    };

    private ILogService service;
    private Spinner priority;
    private EditText tag;
    private EditText msg;
    private Button button;
    private RadioGroup type;

    @Override
    public void onClick(View v) {
        int priorityPosition = this.priority.getSelectedItemPosition();
        if (priorityPosition != AdapterView.INVALID_POSITION) {
            final int priority = LOG_LEVEL[priorityPosition];
            final String tag = this.tag.getText().toString();
            final String msg = this.msg.getText().toString();
            if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg)) {
                new AlertDialog.Builder(this)
                .setMessage(R.string.log_tag_errors)
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int id) {
                        LogActivity.this.log(priority, tag, msg);
                    }
                }).setNegativeButton(android.R.string.no, null).create().show();
            } else {
                log(priority, tag, msg);
            }
        }
    }

    @Override
    public void onServiceConnected(ComponentName comp, IBinder conn) {
        service = ILogService.Stub.asInterface(conn);
        button.setEnabled(true);
    }

    @Override
    public void onServiceDisconnected(ComponentName comp) { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.main);
        this.priority = (Spinner) super.findViewById(R.id.log_priority);
        this.tag = (EditText) super.findViewById(R.id.log_tag);
        this.msg = (EditText) super.findViewById(R.id.log_msg);
        this.type = (RadioGroup) super.findViewById(R.id.type);
        this.type.check(R.id.type_log_j);
        this.button = (Button) super.findViewById(R.id.log_button);
        this.button.setOnClickListener(this);
        this.button.setEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!bindService(new Intent(LogMessage.SERVICE), this, BIND_AUTO_CREATE)) {
            Log.d(TAG, "Failed to bind service");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(this);
        service = null;
        button.setEnabled(false);
    }

    void log(int priority, String tag, String msg) {
        LogMessage.Type logType = null;
        switch (this.type.getCheckedRadioButtonId()) {
            case R.id.type_log_j:
                logType = LogMessage.Type.LOG_JAVA;
                break;
            case R.id.type_log_n:
                logType = LogMessage.Type.LOG_NATIVE;
                break;
        }

        if (null != logType) {
            this.tag.getText().clear();
            this.msg.getText().clear();

            try { service.log(new LogMessage(logType, priority, tag, msg)); }
            catch (Exception e) {
                Log.wtf(TAG, "Failed to log the message", e);
            }
        }
    }
}
