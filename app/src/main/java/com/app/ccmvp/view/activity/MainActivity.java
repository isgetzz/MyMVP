package com.app.ccmvp.view.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.app.ccmvp.R;
import com.app.ccmvp.adapter.TabFragment;
import com.app.ccmvp.base.BaseActivity;
import com.app.ccmvp.base.BaseView;
import com.app.ccmvp.mvp.presenter.MainPresenter;
import com.app.ccmvp.util.Utils;
import com.app.ccmvp.view.custom.TabLayoutListener;
import com.app.ccmvp.view.fragment.GoodsFragment;
import com.app.ccmvp.view.fragment.MainFragment;
import com.app.ccmvp.view.fragment.ShopFragment;
import com.app.ccmvp.view.fragment.UserFragment;
import com.app.xui_lib.toast.XToast;
import com.arialyy.aria.core.Aria;
import com.baselib.customView.CustomScrollViewPager;
import com.baselib.dialog.UpdateDialog;
import com.baselib.dialog.UpdateDialogProgreBar;
import com.baselib.interfaceUtil.OnclickViewListener;
import com.baselib.util.BaseUtil;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.app.ccmvp.mvp.presenter.MainPresenter.TopData;


/**
 * 主页
 */
public class MainActivity extends BaseActivity<MainPresenter> implements BaseView, OnclickViewListener {
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    private long time = 0;
    private UpdateDialog dialog;
    private UpdateDialogProgreBar dialogProgreBar;

    @Override
    protected int BindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initUi(View view) {
        mPresenter.getNewData(TopData);
        Aria.download(this).register();
        dialog = new UpdateDialog(this, this);
        dialogProgreBar = new UpdateDialogProgreBar(this);
        Utils.RequestPermission(getString(R.string.write_permission_hint), this);
    }

    @Override
    protected MainPresenter onCreatePresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void onSucceed(Object data) {
        loadResult(true);
        if (10 > BaseUtil.getVersionCode(this)) {
            dialog.show();
        }
        initPager();
    }

    @Override
    public void onFail(String err) {
        loadResult(false);
        XToast.normal(this, err).show();
    }

    private void initPager() {
        CustomScrollViewPager viewPager = findViewById(R.id.viewpager);
        List<Fragment> list = new ArrayList<>();
        list.add(new MainFragment());
        list.add(new GoodsFragment());
        list.add(new ShopFragment());
        list.add(new UserFragment());
        TabFragment tabFragment = new TabFragment(getSupportFragmentManager(), list);
        viewPager.setAdapter(tabFragment);
        tablayout.addOnTabSelectedListener(new TabLayoutListener(viewPager));
        //如果自定义tab不能用这种方式 需要在适配器里面需要set 其他颜色属性可以xml设置。
        // tablayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        //防止fragment页面不显示
        viewPager.setOffscreenPageLimit(list.size());
        addTab();
    }

    //添加tab
    private void addTab() {
//        for (int i = 0; i < 4; i++) {
//            if (i == 0) {
//                tablayout.addTab(tablayout.newTab().setCustomView(tab_icon(SystemData.systemSetting.getMenu_nav().get(i).getTitle(), SystemData.systemSetting.getMenu_nav().get(i).getImg_url_hover(), ContextCompat.getColor(this, R.color.tab))), true);
//            } else {
//                tablayout.addTab(tablayout.newTab().setCustomView(tab_icon(SystemData.systemSetting.getMenu_nav().get(i).getTitle(), SystemData.systemSetting.getMenu_nav().get(i).getImg_url(), ContextCompat.getColor(this, R.color.black6))), false);
//            }
//        }
    }

    private View tab_icon(String str, String imageUrl, int color) {
        View tab = LayoutInflater.from(this).inflate(R.layout.main_tab_view, null);
        TextView tv = tab.findViewById(R.id.tab_view);
        tv.setText(str);
        tv.setTextColor(color);
        ImageView im = tab.findViewById(R.id.tab_img);
        Glide.with(this).load(imageUrl).into(im);
        return tab;
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - time <= 1000)
            finish();
        else
            XToast.normal(this, getResources().getString(R.string.back_hint), 300).show();
        time = System.currentTimeMillis();
    }

    @Override
    public void clickAffirm(View view) {
        if (dialog.isShowing())
            dialog.dismiss();
        if (dialog != null) {
            dialogProgreBar.show();
            dialogProgreBar.startLoad();
        }
    }

    @Override
    protected void onDestroy() {
        Aria.download(this).unRegister();
        if (dialog != null)
            dialog.dismiss();
        dialog = null;
        if (dialogProgreBar != null)
            dialogProgreBar.dismiss();
        dialogProgreBar = null;
        super.onDestroy();
    }

}
