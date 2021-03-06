package com.baselib.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

import androidx.annotation.StyleRes;

import com.baselib.R;
import com.baselib.customView.MiniLoadingView;
import com.baselib.interfaceUtil.IMessageLoader;
import com.baselib.interfaceUtil.cancelOnclickListener;


/**
 * 迷你loading加载
 *
 * @author XUE
 * @since 2019/4/9 14:16
 */
public class MiniLoadingDialog extends BaseDialog implements IMessageLoader {

    private MiniLoadingView mLoadingView;
    private TextView mTvTipMessage;

    private cancelOnclickListener mLoadingCancelListener;

    public MiniLoadingDialog(Context context) {
            super(context, R.style.DialogTheme, R.layout.xui_dialog_loading_mini);
        initView("数据加载中…");
    }

    public MiniLoadingDialog(Context context, String tipMessage) {
        super(context, R.style.DialogTheme, R.layout.xui_dialog_loading_mini);
        initView(tipMessage);
    }

    public MiniLoadingDialog(Context context, @StyleRes int themeResId) {
        super(context, themeResId, R.layout.xui_dialog_loading_mini);
        initView("数据加载中…");
    }

    public MiniLoadingDialog(Context context, @StyleRes int themeResId, String tipMessage) {
        super(context, themeResId, R.layout.xui_dialog_loading_mini);
        initView(tipMessage);
    }

    private void initView(String tipMessage) {
        mLoadingView = findViewById(R.id.mini_loading_view);
        mTvTipMessage = findViewById(R.id.tv_tip_message);
        updateMessage(tipMessage);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

    }


    /**
     * 更新提示信息
     *
     * @param tipMessage
     * @return
     */
    @Override
    public void updateMessage(String tipMessage) {
        if (mTvTipMessage != null) {
            mTvTipMessage.setText(tipMessage);
        }
    }

    /**
     * 更新提示信息
     *
     * @param tipMessageId
     * @return
     */
    @Override
    public void updateMessage(int tipMessageId) {
        updateMessage(getString(tipMessageId));
    }


    /**
     * 显示加载
     */
    @Override
    public void show() {
        super.show();
        if (mLoadingView != null) {
            mLoadingView.start();
        }
    }

    /**
     * 隐藏加载
     */
    @Override
    public void dismiss() {
        if (mLoadingView != null) {
            mLoadingView.stop();
        }
        super.dismiss();
    }

    /**
     * 资源释放
     */
    @Override
    public void recycle() {

    }

    /**
     * 是否在加载
     *
     * @return
     */
    @Override
    public boolean isLoading() {
        return isShowing();
    }

    /**
     * 设置是否可取消
     *
     * @param flag
     */
    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
        if (flag) {
            setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    if (mLoadingCancelListener != null) {
                        mLoadingCancelListener.clickCancel();
                    }
                }
            });
        }
    }

    /**
     * 设置取消的回掉监听
     *
     * @param listener
     */
    @Override
    public void setLoadingCancelListener(cancelOnclickListener listener) {
        mLoadingCancelListener = listener;
    }
}
