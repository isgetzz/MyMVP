package com.app.ccmvp.view.activity;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.app.ccmvp.R;
import com.app.ccmvp.adapter.TabFragment;
import com.app.ccmvp.base.BaseActivity;
import com.app.ccmvp.base.BaseView;
import com.app.ccmvp.bean.SystemData;
import com.app.ccmvp.mvp.presenter.SystemPresenter;
import com.app.ccmvp.util.Utils;
import com.app.ccmvp.view.custom.TabLayoutListener;
import com.app.ccmvp.view.fragment.GoodsFragment;
import com.app.ccmvp.view.fragment.MainFragment;
import com.app.ccmvp.view.fragment.ShopFragment;
import com.app.ccmvp.view.fragment.UserFragment;
import com.app.xui_lib.toast.XToast;
import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.task.DownloadTask;
import com.baselib.customView.CustomScrollViewPager;
import com.baselib.eventBus.updateEvent;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * 主页
 */
public class MainActivity extends BaseActivity<SystemPresenter> implements BaseView<SystemData> {
    private LinearLayout fail_layout;
    private ImageView fail_image;
    private TabLayout tablayout;
    private long time = 0;
    @Override
    protected int BindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initUi(View view) {
        mPresenter.getSystemData();
        fail_layout = findViewById(R.id.fail_layout);
        fail_image = findViewById(R.id.fail_image);
        EventBus.getDefault().register(this);
        RequestPermission(getString(R.string.write_permission_hint));
        Aria.download(this).register();
        String filePath = "/mnt/sdcard/mvp.apk";
        try {
            File file1 = new File(filePath);
            if (!file1.exists()) {
                file1.createNewFile();
            }
            long mTaskId = Aria.download(this)
                    .load("http://shop.cngaoluo.com/app/gaoluo.apk")     //读取下载地址
                    .setFilePath(filePath)//读取下载地址
                    .ignoreFilePathOccupy()
                    //  .option(option)
                    .ignoreCheckPermissions()
                    .create();

            Log.e("running2", file1.getAbsolutePath() + "==" + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //ThreadMode.MAIN 主线程调用更新Ui ThreadMode.POSTING 发送消息在哪个线程这个也在哪里调用
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdata(updateEvent message) {
        Log.e("running3", message.isUpdate() + "=");
    }

    //在这里处理任务执行中的状态，如进度进度条的刷新
    @Download.onTaskRunning
    protected void running(DownloadTask task) {
        //当前下载的进度1.00M
        String progress = task.getConvertCurrentProgress();
        int p = task.getPercent();    //任务进度百分比
        String speed = task.getConvertSpeed();    //转换单位后的下载速度，单位转换需要在配置文件中打开
        Log.e("running", progress + "==" + p + "==" + speed);
    }

    @Download.onTaskComplete
    void taskComplete(DownloadTask task) {
        //在这里处理任务完成的状态
        EventBus.getDefault().post(new updateEvent(true));
        Log.e("running1", task.getFilePath() + "=");
    }

    @OnClick(R.id.fail_bt)
    protected void onClickView(View view) {
        switch (view.getId()) {
            case R.id.fail_bt:
                mPresenter.getSystemData();
                break;
        }
    }

    @Override
    protected SystemPresenter onCreatePresenter() {
        return new SystemPresenter(this);
    }

    @Override
    public void onSucceed(SystemData data) {
        SystemData.systemSetting = data.getData();
        fail_layout.setVisibility(View.GONE);
        initPager();
    }

    @Override
    public void onFail(String err) {
        fail_layout.setVisibility(View.VISIBLE);
        XToast.normal(this, err).show();
    }

    private void initPager() {
        CustomScrollViewPager viewPager = findViewById(R.id.viewpager);
        tablayout = findViewById(R.id.tablayout);
        List<Fragment> list = new ArrayList<>();
        list.add(new MainFragment());
        list.add(new GoodsFragment());
        list.add(new ShopFragment());
        list.add(new UserFragment());
        TabFragment tabFragment = new TabFragment(getSupportFragmentManager(), list);
        viewPager.setAdapter(tabFragment);
        tablayout.addOnTabSelectedListener(new TabLayoutListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        viewPager.setOffscreenPageLimit(list.size());
        addTab();
    }

    private void addTab() {
        for (int i = 0; i < SystemData.systemSetting.getMenu_nav().size(); i++) {
            if (i == 0) {
                tablayout.addTab(tablayout.newTab().setCustomView(tab_icon(SystemData.systemSetting.getMenu_nav().get(i).getTitle(), SystemData.systemSetting.getMenu_nav().get(i).getImg_url_hover(), ContextCompat.getColor(this, R.color.tab))), true);
            } else {
                tablayout.addTab(tablayout.newTab().setCustomView(tab_icon(SystemData.systemSetting.getMenu_nav().get(i).getTitle(), SystemData.systemSetting.getMenu_nav().get(i).getImg_url(), ContextCompat.getColor(this, R.color.black6))), false);
            }
        }
    }

    private View tab_icon(String str, String imageUrl, int color) {
        View tab = LayoutInflater.from(this).inflate(R.layout.main_tab_view, null);
        TextView tv = tab.findViewById(R.id.tab_view);
        tv.setText(str);
        tv.setTextColor(color);
        ImageView im = tab.findViewById(R.id.tab_img);
        Glide.with(this).load(imageUrl).into(im);
        return tab;
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - time <= 1000)
            finish();
        else
            XToast.normal(this, getResources().getString(R.string.back_hint), 300).show();
        time = System.currentTimeMillis();
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {
        super.onActivityResult(reqCode, resCode, data);
        switch (reqCode) {
            case 1: {
                if (AndPermission.hasPermissions(this, Permission.Group.STORAGE)) {
                    // 有对应的权限
                } else {
                    // 没有对应的权限
                }
                break;
            }
        }
    }
    @Override
    protected void onDestroy() {
        Utils.clearImgMemory(fail_image);
        Aria.download(this).unRegister();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
