package com.app.ccmvp;

import android.app.Application;
import android.os.Process;
import android.text.TextUtils;

import com.app.ccmvp.bean.SystemData;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by cc on 2018/11/5.
 */
public class App extends Application {

    private static App mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        // 获取当前包名
        String packageName = getPackageName();
        String processName = getProcessName(Process.myPid());
        if (!packageName.equals(processName)) {
            return;
        }
        mContext = this;
    }

    public void init() {
        StringBuffer param = new StringBuffer();
        param.append("appid=" + getString(R.string.app_id));
        param.append(",");
        // 设置使用v5+
        param.append(SpeechConstant.ENGINE_MODE + "=" + SpeechConstant.MODE_MSC);
        SpeechUtility.createUtility(this, param.toString());
    }

    public static App getContext() {
        return mContext;
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
