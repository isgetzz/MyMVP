package com.app.ccmvp.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.app.ccmvp.R;
import com.app.ccmvp.util.StatusBarUtil;

/**
 * Created by cc on 2018/10/30.
 */

public abstract class LoadFailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState.putParcelable("android:support:fragments", null);
        }
        super.onCreate(savedInstanceState);
        //设置为竖屏
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        //设置进入退出动画
        setTheme(android.R.style.Animation_Activity);
        StatusBarUtil.darkMode(this);
        setContentView(R.layout.load_fail);
    }
}
