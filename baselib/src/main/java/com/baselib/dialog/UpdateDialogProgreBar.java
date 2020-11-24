package com.baselib.dialog;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.inf.IEntity;
import com.arialyy.aria.core.task.DownloadTask;
import com.arialyy.aria.util.CommonUtil;
import com.baselib.R;
import com.baselib.interfaceUtil.OnclickListener;
import com.baselib.interfaceUtil.OnclickViewListener;
import com.baselib.util.FileUtil;
import com.baselib.util.InstallApkUtil;

/**
 * 更新下载进度条提示
 */
public class UpdateDialogProgreBar extends BaseDialog {
    private TextView tv_downing;
    private TextView tv_complete_loading;
    private ProgressBar progressBar;
    private TextView tv_speed;
    private long mTaskId = -1;
    private String url = "http:www.ccapk";
    private int code = 99;//模拟服务器版本
    private DownloadEntity entity;
    private Context context;

    public UpdateDialogProgreBar(Context context) {
        super(context, R.layout.dialog_update_progress);
        Aria.download(this).register();
        this.context = context;
        initData();
    }

    public void initData() {
        setCanceledOnTouchOutside(false);
        tv_complete_loading = mContentView.findViewById(R.id.tv_complete_loading);
        tv_speed = mContentView.findViewById(R.id.tv_speed);
        tv_downing = mContentView.findViewById(R.id.tv_downing);
        progressBar = mContentView.findViewById(R.id.progressBar);
        tv_complete_loading.setText(getString(R.string.stop));
        tv_complete_loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //没有记录重新下载
                if (mTaskId == -1) {
                    mTaskId = Aria.download(this)
                            .load(url)
                            .setFilePath(FileUtil.getFilePath(context, "MVP", System.currentTimeMillis() + "_" + code + ".apk").getAbsolutePath())
                            .create();
                    tv_complete_loading.setText(getString(R.string.stop));
                }
                if (Aria.download(this).load(mTaskId).isRunning()) {
                    Aria.download(this).load(mTaskId).stop();
                    tv_speed.setText(getString(R.string.down_speed_normal));
                    tv_complete_loading.setText(getString(R.string.recover));
                } else {
                    Aria.download(this).load(mTaskId).resume();
                    tv_complete_loading.setText(getString(R.string.stop));
                }
            }
        });

    }

    public void startLoad() {//开始下载
        entity = Aria.download(this).getFirstDownloadEntity(url);
        if (entity == null) {
            mTaskId = Aria.download(this)
                    .load(url)
                    .setFilePath(FileUtil.getFilePath(context, "MVP", System.currentTimeMillis() + "_" + code + ".apk").getAbsolutePath())
                    .create();
        } else {
            mTaskId = entity.getId();
            tv_downing.setText(context.getString(R.string.string_down_progress, CommonUtil.formatFileSize(entity.getCurrentProgress()), CommonUtil.formatFileSize(entity.getFileSize())));
            int p = (int) (entity.getCurrentProgress() * 100 / entity.getFileSize());
            progressBar.setProgress(p);
            tv_complete_loading.setEnabled(entity.getState() != DownloadEntity.STATE_RUNNING);
            setLoadState();
            if (entity.getState() == DownloadEntity.STATE_COMPLETE) {
                InstallApkUtil.Install(context, entity.getFilePath());
                dismiss();
            }
        }
    }

    //在这里处理任务执行中的状态，如进度进度条的刷新
    @Download.onTaskRunning
    protected void running(DownloadTask task) {
        //任务进度百分比
        progressBar.setProgress(task.getPercent());
        //转换单位后的下载速度，单位转换需要在配置文件中打开
        tv_speed.setText(task.getConvertSpeed());
        tv_downing.setText(context.getString(R.string.string_down_progress, task.getConvertCurrentProgress(), task.getConvertFileSize()));
    }

    @Download.onTaskComplete
    protected void taskComplete(DownloadTask task) {
        //在这里处理任务完成的状态
        InstallApkUtil.Install(context, task.getFilePath());
        dismiss();
    }

    public void setLoadState() {
        String btStr = "";
        String stateStr = "";
        switch (entity.getState()) {
            case IEntity.STATE_WAIT:
                btStr = context.getResources().getString(R.string.start);
                stateStr = context.getResources().getString(R.string.waiting);
                break;
            case IEntity.STATE_OTHER:
            case IEntity.STATE_FAIL:
                btStr = context.getResources().getString(R.string.start);
                stateStr = context.getResources().getString(R.string.load_fail_hint);
                break;
            case IEntity.STATE_STOP:
                btStr = context.getResources().getString(R.string.recover);
                stateStr = context.getResources().getString(R.string.stop);
                break;
            case IEntity.STATE_PRE:
            case IEntity.STATE_POST_PRE:
            case IEntity.STATE_RUNNING:
                btStr = context.getResources().getString(R.string.stop);
                stateStr = entity.getConvertSpeed();
                break;
            case IEntity.STATE_COMPLETE:
                btStr = context.getResources().getString(R.string.re_start);
                stateStr = context.getResources().getString(R.string.completed);
                break;
            case IEntity.STATE_CANCEL:
                btStr = context.getResources().getString(R.string.close);
                break;
            default:
                btStr = context.getResources().getString(R.string.start);
                stateStr = "";
                break;
        }
        tv_complete_loading.setText(btStr);
    }

}
