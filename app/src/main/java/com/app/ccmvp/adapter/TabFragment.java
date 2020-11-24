package com.app.ccmvp.adapter;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 标签栏切换适配器
 */
public class TabFragment extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
 //   private List<String> titles;

    public TabFragment(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Log.d("destroyItem", position + "");
        super.destroyItem(container, position, object);
    }
/*
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position % titles.size());
    }*/
}