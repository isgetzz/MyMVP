package com.app.ccmvp.view.fragment;

import android.util.Log;
import android.view.View;

import com.app.ccmvp.R;
import com.app.ccmvp.base.BaseFragment;
import com.app.ccmvp.base.BaseView;
import com.app.ccmvp.bean.NewResultData;
import com.app.ccmvp.mvp.presenter.SystemPresenter;
import com.baselib.viewpager.SimpleImageBanner;

import java.util.List;

public class MainFragment extends BaseFragment<SystemPresenter> implements BaseView<NewResultData> {
    private SimpleImageBanner banner_viewpager;
    private List<String> mData;

    @Override
    protected int setContentView() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initUi() {
        banner_viewpager = findViewById(R.id.banner_viewpager);
        mPresenter.getSystemData();
    }

    @Override
    protected void setOnClick(View view) {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected SystemPresenter onCreatePresenter() {
        return new SystemPresenter(this);
    }

    @Override
    public void onFail(String err) {

    }

    @Override
    public void onSucceed(NewResultData data) {
        Log.e("NewResultData", data + "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
