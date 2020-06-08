package com.app.ccmvp.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.app.ccmvp.util.StatusBarUtil;
import com.app.ccmvp.view.activity.MainActivity;
import com.baselib.dialog.RequestPermissionDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

/**
 * Created by cc on 2018/10/30.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    private long lastClick = 0;
    protected P mPresenter;

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
        View view = LayoutInflater.from(this).inflate(BindLayout(), null);
        setContentView(view);
        if (onCreatePresenter() != null) {
            mPresenter = onCreatePresenter();
        }
        initUi(view);
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
    }

    private boolean fastClick() {
        if (System.currentTimeMillis() - lastClick <= 300) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    protected abstract int BindLayout();

    protected abstract void initUi(View view);

    //当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以覆写此方法
    protected void initData() {

    }

    /**
     * 权限申请
     *
     * @param hint 提示语
     */
    public void RequestPermission(String hint) {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onDenied(data -> {
                    new RequestPermissionDialog.Builder()
                            .setContext(this)
                            .setContent(hint).setOnclickListener(() -> {
                        if (AndPermission.hasAlwaysDeniedPermission(this, data)) {
                            // 权限被用户禁止团出,到设置页面
                            AndPermission.with(this)
                                    .runtime()
                                    .setting()
                                    .start(1);
                        }
                    }).build().show();
                }).start();

    }
    protected abstract P onCreatePresenter();
       /* 进度条 void onProgressStart();
    void onProgressEnd();*/
}
