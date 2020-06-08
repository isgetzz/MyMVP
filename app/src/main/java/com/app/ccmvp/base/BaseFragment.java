package com.app.ccmvp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by cc on 2018/11/5.
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements View.OnClickListener {
    // 视图是否已经初初始化
    protected boolean isInit = false;
    protected boolean isLoad = false;
    private View view;
    private long lastClick;
    protected P mPresenter;
    protected Context context;
    protected Activity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(setContentView(), container, false);
        context = getContext();
        activity = getActivity();
        isInit = true;
        isCanLoadData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (activity == null) {
            activity = getActivity();
        }
        if (onCreatePresenter() != null) {
            mPresenter = onCreatePresenter();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
    }

    //视图是否已经对用户可见，系统的方法
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    //是否可以加载数据 可以加载数据的条件：1.视图已经初始化2.视图对用户可见
    private void isCanLoadData() {
        if (!isInit) {
            return;
        }
        if (getUserVisibleHint()) {
            if (onCreatePresenter() != null) {
                mPresenter = onCreatePresenter();
            }
            initUi();
            setListener();
            initData();
            isLoad = true;
            isInit = false;
        } else {
            if (isLoad) {
                stopLoad();
            }
        }
    }

    //设置绑定布局
    protected abstract int setContentView();

    //当视图初始化并且对用户可见的时候去真正的加载数据,每个页面会显示一次
    protected abstract void initUi();

    protected abstract void setOnClick(View view);

    protected abstract void setListener();

    //获取设置的布局
    protected View getContentView() {
        return view;
    }

    //找出对应的控件
    protected <T extends View> T findViewById(int id) {
        return (T) getContentView().findViewById(id);
    }

    @Override
    public void onClick(View v) {
        if (fastClick()) {
            setOnClick(v);
        }
    }

    private boolean fastClick() {
        if (System.currentTimeMillis() - lastClick <= 300) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    //当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以覆写此方法
    protected void stopLoad() {

    }

    protected void initData() {

    }

    //视图销毁的时候讲Fragment是否初始化的状态变为false
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInit = false;
        isLoad = false;
    }

    protected abstract P onCreatePresenter();
}
