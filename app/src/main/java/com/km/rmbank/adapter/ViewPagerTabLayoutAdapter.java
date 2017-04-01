package com.km.rmbank.adapter;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by kamangkeji on 17/2/13.
 */

public class ViewPagerTabLayoutAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList;
    private List<String> tabLayout;

    public ViewPagerTabLayoutAdapter(FragmentManager fm, List<Fragment> fragmentList,List<String> tabLayout) {
        super(fm);
        this.fragmentList = fragmentList;
        this.tabLayout = tabLayout;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabLayout.get(position);
    }
}
