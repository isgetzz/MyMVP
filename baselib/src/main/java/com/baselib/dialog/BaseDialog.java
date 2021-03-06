/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.baselib.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatDialog;
import androidx.core.content.ContextCompat;

import com.baselib.R;
import com.baselib.util.BaseUtil;


/**
 *  基类Dialog
 *  触摸Dialog屏幕以外的区域，dialog消失同·时隐藏键盘
 *
 * @author cc
 * @since 2018/12/6 下午3:29
 */
public class BaseDialog extends AppCompatDialog {
    public View mContentView;

    public BaseDialog(Context context, int layoutId) {
        this(context, R.style.DialogTheme, layoutId);
    }

    public BaseDialog(Context context, View contentView) {
        this(context, R.style.DialogTheme, contentView);
    }

    public BaseDialog(Context context) {
        super(context, R.style.DialogTheme);
    }

    public BaseDialog(Context context, int theme, int layoutId) {
        super(context, theme);
        init(layoutId);

    }

    public BaseDialog(Context context, int theme, View contentView) {
        super(context, theme);
        init(contentView);
    }

    public void init(int layoutId) {
        View view = getLayoutInflater().inflate(layoutId, null);
        init(view);
    }

    private void init(View view) {
        setContentView(view);
        mContentView = view;
        setCanceledOnTouchOutside(true);
    }

    /**
     * 设置弹窗的宽和高
     *
     * @param width
     * @param height
     */
    public BaseDialog setDialogSize(int width, int height) {
        // 获取对话框当前的参数值
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = width;
        p.height = height;
        getWindow().setAttributes(p);
        return this;
    }

    @Override
    public <T extends View> T findViewById(int resId) {
        return mContentView.findViewById(resId);
    }

    public String getString(int resId){
        return getContext().getResources().getString(resId);
    }

    public Drawable getDrawable(int resId){
        return ContextCompat.getDrawable(getContext(), resId);
    }
}
