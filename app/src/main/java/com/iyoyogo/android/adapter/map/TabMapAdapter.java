package com.iyoyogo.android.adapter.map;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class TabMapAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<Fragment> list1;
    private List<String> table;
    public TabMapAdapter(FragmentManager fm, List<Fragment> list1, List<String> table) {
        super(fm);
        this.list1 = list1;
        this.table = table;
    }

    @Override
    public Fragment getItem(int position) {
        return list1.get(position);
    }

    @Override
    public int getCount() {
        return list1.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return table.get(position);
    }
}