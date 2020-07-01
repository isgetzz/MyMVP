package com.app.ccmvp.view.activity;

import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.app.ccmvp.R;
import com.app.ccmvp.adapter.TabFragment;
import com.app.ccmvp.base.BaseActivity;
import com.app.ccmvp.base.BaseView;
import com.app.ccmvp.bean.SystemData;
import com.app.ccmvp.mvp.presenter.SystemPresenter;
import com.app.ccmvp.view.custom.TabLayoutListener;
import com.app.ccmvp.view.fragment.GoodsFragment;
import com.app.ccmvp.view.fragment.MainFragment;
import com.app.ccmvp.view.fragment.ShopFragment;
import com.app.ccmvp.view.fragment.UserFragment;
import com.app.xui_lib.toast.XToast;
import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.inf.IEntity;
import com.arialyy.aria.core.task.DownloadTask;
import com.arialyy.aria.util.CommonUtil;
import com.baselib.customView.CustomScrollViewPager;
import com.baselib.eventBus.updateEvent;
import com.baselib.util.InstallApkUtil;
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

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主页
 */
public class MainActivity extends BaseActivity<SystemPresenter> implements BaseView<SystemData> {
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.tv_complete_loading)
    TextView tv_complete_loading;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_downing)
    TextView tv_downing;
    @BindView(R.id.tv_speed)
    TextView tv_speed;
    private long time = 0;
    private long mTaskId = -1;
    private DownloadEntity entity;

    @Override
    protected int BindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initUi(View view) {
        mPresenter.getSystemData();
        Aria.download(this).register();
        EventBus.getDefault().register(this);
        RequestPermission(getString(R.string.write_permission_hint));
    }

    //ThreadMode.MAIN 主线程调用更新Ui ThreadMode.POSTING 发送消息在哪个线程这个也在哪里调用
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdata(updateEvent message) {
    }

    //在这里处理任务执行中的状态，如进度进度条的刷新
    @Download.onTaskRunning
    protected void running(DownloadTask task) {
        String speed = task.getConvertSpeed();    //转换单位后的下载速度，单位转换需要在配置文件中打开
        //任务进度百分比
        progressBar.setProgress(task.getPercent());
        tv_speed.setText(task.getConvertSpeed());
        tv_downing.setText(getString(R.string.string_down_progress, task.getConvertCurrentProgress(), task.getConvertFileSize()));

    }

    @Download.onTaskComplete
    void taskComplete(DownloadTask task) {
        //在这里处理任务完成的状态
        EventBus.getDefault().post(new updateEvent(true));
        InstallApkUtil.Install(this, task.getFilePath());

    }

    @OnClick({R.id.fail_bt, R.id.tv_complete_loading})
    protected void onClickView(View view) {
        switch (view.getId()) {
            case R.id.fail_bt:
                dialog.show();
                mPresenter.getSystemData();
                break;
            case R.id.tv_complete_loading:
                String filePath = "/mnt/sdcard/mvp.apk";
                try {
                    File file1 = new File(filePath);
                    if (!file1.exists()) {
                        file1.createNewFile();
                    }
                    if (mTaskId == -1) {
                        mTaskId = Aria.download(this)
                                .load(SystemData.systemSetting.getAndroid_downurl())
                                .setFilePath(filePath)
                                .create();
                        tv_complete_loading.setText(getString(R.string.stop));
                        break;
                    }
                    if (Aria.download(this).load(mTaskId).isRunning()) {
                        Aria.download(this).load(mTaskId).stop();
                        tv_complete_loading.setText(getString(R.string.recover));
                    } else {
                        Aria.download(this).load(mTaskId).resume();
                        tv_complete_loading.setText(getString(R.string.stop));
                    }
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        entity = Aria.download(this).getFirstDownloadEntity(data.getData().getAndroid_downurl());
        if (entity != null) {
            mTaskId = entity.getId();
            tv_downing.setText(getString(R.string.string_down_progress, CommonUtil.formatFileSize(entity.getCurrentProgress()), CommonUtil.formatFileSize(entity.getFileSize())));
            int p = (int) (entity.getCurrentProgress() * 100 / entity.getFileSize());
            progressBar.setProgress(p);
            tv_complete_loading.setEnabled(entity.getState() != DownloadEntity.STATE_RUNNING);
            setLoadState();
/*
            if (entity.getState() == DownloadEntity.STATE_COMPLETE) {
                InstallApkUtil.Install(this, entity.getFilePath());
            }
*/
            //安装完成可以删除任务跟文件，相同地址生成的id 相同预防重复下载
            Aria.download(this).load(mTaskId).cancel(true);
        } else {
            tv_complete_loading.setEnabled(true);
        }
        loadResult(true);
        initPager();
    }

    @Override
    public void onFail(String err) {
        loadResult(false);
        XToast.normal(this, err).show();
    }

    private void initPager() {
        CustomScrollViewPager viewPager = findViewById(R.id.viewpager);
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
        Aria.download(this).unRegister();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void setLoadState() {
        String btStr = "";
        String stateStr = "";
        switch (entity.getState()) {
            case IEntity.STATE_WAIT:
                btStr = getResources().getString(R.string.start);
                stateStr = getResources().getString(R.string.waiting);
                break;
            case IEntity.STATE_OTHER:
            case IEntity.STATE_FAIL:
                btStr = getResources().getString(R.string.start);
                stateStr = getResources().getString(R.string.load_fail_hint);
                break;
            case IEntity.STATE_STOP:
                btStr = getResources().getString(R.string.recover);
                stateStr = getResources().getString(R.string.stop);
                break;
            case IEntity.STATE_PRE:
            case IEntity.STATE_POST_PRE:
            case IEntity.STATE_RUNNING:
                btStr = getResources().getString(R.string.stop);
                stateStr = entity.getConvertSpeed();
                break;
            case IEntity.STATE_COMPLETE:
                btStr = getResources().getString(R.string.re_start);
                stateStr = getResources().getString(R.string.completed);
                break;
            case IEntity.STATE_CANCEL:
                btStr = getResources().getString(R.string.close);
                break;
            default:
                btStr = getResources().getString(R.string.start);
                stateStr = "";
                break;
        }
        tv_complete_loading.setText(btStr);
    }
}
