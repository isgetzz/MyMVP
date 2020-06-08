package com.baselib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.baselib.R;

/**
 * Created by cc on 2018/4/8.申请权限提示
 */
public class RequestPermissionDialog extends Dialog implements View.OnClickListener {
    private OnclickListener onclickListener;

    private RequestPermissionDialog(Builder builder) {
        super(builder.context, R.style.DialogTheme);
        onclickListener = builder.onclickListener;
        initUi(builder);
    }

    private void initUi(Builder builder) {
        setContentView(R.layout.permission_dialog);
        getWindow().getAttributes().gravity = Gravity.CENTER;
        setCanceledOnTouchOutside(false);
        TextView content_tv = findViewById(R.id.content_tv);
        if (!TextUtils.isEmpty(builder.content))
            content_tv.setText(builder.content);
        TextView determine_tv = findViewById(R.id.determine_tv);
        TextView cancel_tv = findViewById(R.id.cancel_tv);
        if (onclickListener != null) {
            determine_tv.setOnClickListener(this);
            cancel_tv.setOnClickListener(this);
        }
    }

    /**
     * Builder 构建类
     */
    public static class Builder {
        private Context context;
        private String content;
        private OnclickListener onclickListener;

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setOnclickListener(OnclickListener onclickListener) {
            this.onclickListener = onclickListener;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public RequestPermissionDialog build() {
            //获取dialog 对象,外部可以调用show(),dismiss()
            return new RequestPermissionDialog(this);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.determine_tv) {
            onclickListener.confirm();
            dismiss();
        } else if (id == R.id.cancel_tv) {
            //   onclickListener.cancel();
            dismiss();
        }
    }

    public interface OnclickListener {
        void confirm();

        //  void cancel();
    }
}
