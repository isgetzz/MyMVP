package com.app.ccmvp.view.fragment;

import android.util.Log;
import android.view.View;

import com.app.ccmvp.R;
import com.app.ccmvp.base.BaseFragment;
import com.app.ccmvp.base.BaseView;
import com.app.ccmvp.bean.MainData;
import com.app.ccmvp.bean.SystemData;
import com.app.ccmvp.mvp.presenter.MainPresenter;
import com.baselib.viewpager.SimpleImageBanner;

import java.util.List;

public class MainFragment extends BaseFragment<MainPresenter> implements BaseView {
    private SimpleImageBanner banner_viewpager;
    private List<String> mData;

    @Override
    protected int setContentView() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initUi() {
        banner_viewpager = findViewById(R.id.banner_viewpager);
        mPresenter.getMainData(MainPresenter.MainData);
        mPresenter.getMainData(MainPresenter.BaseData);
    }

    @Override
    protected void setOnClick(View view) {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected MainPresenter onCreatePresenter() {
        return new MainPresenter(this);
    }


    @Override
    public void onSucceed(Object data) {
        if (data instanceof MainData) {
            MainData mainData = (MainData) data;
        } else if (data instanceof SystemData) {
            SystemData systemData = (SystemData) data;
        }

    }

    @Override
    public void onFail(String err) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
