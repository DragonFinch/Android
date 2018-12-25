package com.iyoyogo.android.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MineFragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments;
    List<String> title;
    public MineFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> title) {
        super(fm);
        this.fragments=fragments;
        this.title=title;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
}
