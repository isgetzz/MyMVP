package com.app.ccmvp.view.custom;


import android.widget.ImageView;
import android.widget.TextView;

import com.app.ccmvp.App;
import com.app.ccmvp.R;
import com.app.ccmvp.bean.SystemData;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

public class TabLayoutListener extends TabLayout.ViewPagerOnTabSelectedListener {
    private ViewPager viewPager;
    public TabLayoutListener(ViewPager viewPager) {
        super(viewPager);
        this.viewPager = viewPager;
    }
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
        setTab(tab, SystemData.systemSetting.getMenu_nav().get(tab.getPosition()).getImg_url_hover(), R.color.tab);
    }
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        setTab(tab, SystemData.systemSetting.getMenu_nav().get(tab.getPosition()).getImg_url(), R.color.black6);
    }
    private void setTab(TabLayout.Tab tab, String url, int colorId) {
        if (tab.getCustomView() != null) {
            TextView view = tab.getCustomView().findViewById(R.id.tab_view);
            ImageView imageView = tab.getCustomView().findViewById(R.id.tab_img);
            Glide.with(App.getContext()).load(url).into(imageView);
            view.setTextColor(ContextCompat.getColor(App.getContext(), colorId));
        }
    }
}