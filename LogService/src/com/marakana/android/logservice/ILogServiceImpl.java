package com.marakana.android.logservice;

import com.marakana.android.logcommon.ILogService;
import com.marakana.android.logcommon.LogMessage;
import com.marakana.android.lognative.LogLib;


public class ILogServiceImpl extends ILogService.Stub {
    @Override
    public void log(LogMessage msg) {
        switch (msg.getType()) {
            case LOG_JAVA:
                LogLib.logJ(msg.getLevel(), msg.getTag(), msg.getMessage());
                break;
            case LOG_NATIVE:
                LogLib.logN(msg.getLevel(), msg.getTag(), msg.getMessage());
                break;
        }
    }
}
