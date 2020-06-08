package com.app.ccmvp.view.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * 启动页
 */
public class LogoActivity extends AppCompatActivity {
    private Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //防止home退出打开app重启
        if (!this.isTaskRoot()) {
            Intent intent = getIntent();
            if (intent != null) {
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(intent.getAction())) {
                    finish();
                    return;
                }
            }
        }
        setTheme(android.R.style.Animation_Activity);
        //倒计时
        disposable = Flowable.intervalRange(0, 3, 0, 1, TimeUnit.SECONDS).subscribeOn(AndroidSchedulers.mainThread()).doOnNext(aLong -> {
        }).doOnComplete(() -> {
            Intent intent = new Intent(LogoActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }).subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && disposable.isDisposed())
            disposable.dispose();
    }
}
