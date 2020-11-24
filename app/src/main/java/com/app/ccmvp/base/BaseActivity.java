package com.app.ccmvp.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.app.ccmvp.R;
import com.app.ccmvp.util.StatusBarUtil;
import com.app.ccmvp.util.Utils;
import com.baselib.dialog.MiniLoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by cc on 2018/10/30.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    private long lastClick = 0;
    protected P mPresenter;
    private Unbinder unbinder;
    @BindView(R.id.fail_layout)
    LinearLayout fail_layout;
    @BindView(R.id.fail_image)
    ImageView fail_image;
    FrameLayout fl_main;
    public MiniLoadingDialog dialog;

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
        setContentView(R.layout.activity_base);
        fl_main = findViewById(R.id.fl_main);
        View view = LayoutInflater.from(this).inflate(BindLayout(), null);
        fl_main.addView(view);
        unbinder = ButterKnife.bind(this);
        if (onCreatePresenter() != null) {
            mPresenter = onCreatePresenter();
        }
        dialog = new MiniLoadingDialog(this);
        dialog.show();
        initUi(view);
        initData();
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
        EventBus.getDefault().register(this);
    }
    /**
     * 接口请求成功隐藏
     */
    protected void loadResult(boolean success) {
        fail_layout.setVisibility(success ? View.GONE : View.VISIBLE);
        fl_main.setVisibility(success ? View.VISIBLE : View.GONE);
        if (dialog != null)
            dialog.dismiss();
    }

    protected abstract P onCreatePresenter();

    @Subscribe
    public void onEventBus(boolean b) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (dialog != null)
            dialog.dismiss();
        if (fail_image != null)
            Utils.clearImgMemory(fail_image);
    }

}
