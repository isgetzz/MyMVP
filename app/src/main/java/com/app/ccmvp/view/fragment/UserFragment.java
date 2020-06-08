package com.app.ccmvp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.ccmvp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UserFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user,null);
    }
    /*  @Override
    protected int setContentView() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initUi() {

    }
    @Override
    protected void setOnClick(View view) {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return new MainPresenter();
    }*/

}
