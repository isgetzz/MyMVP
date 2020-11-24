package com.baselib.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.baselib.R;
import com.baselib.interfaceUtil.OnclickListener;
import com.baselib.interfaceUtil.OnclickViewListener;

/**
 * 升级提示
 */
public class UpdateDialog extends BaseDialog {
    private OnclickViewListener listener;

    public UpdateDialog(Context context, OnclickViewListener listener) {
        super(context, R.layout.dialog_update);
        this.listener = listener;
        initData();
    }

    public void initData() {
        setCanceledOnTouchOutside(false);
        TextView tv_cancel = mContentView.findViewById(R.id.tv_cancel);
        TextView tv_update = mContentView.findViewById(R.id.tv_update);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.clickAffirm(v);
            }
        });
    }
}
