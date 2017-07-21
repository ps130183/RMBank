package com.km.rmbank.module.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.km.rmbank.R;
import com.km.rmbank.adapter.ViewPagerAdapter;
import com.km.rmbank.adapter.ViewPagerTabLayoutAdapter;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.module.rmshop.GoodsFragment;
import com.ps.androidlib.entity.TabEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home3Fragment extends BaseFragment {

    @BindView(R.id.common_tab_layout)
    CommonTabLayout mCommonTabLayout;
    private String[] mTitle = {"俱乐部","动态","约吗","捡漏","合伙人"};
    @BindView(R.id.viewpager)
    ViewPager mViewPager;


    public static Home3Fragment newInstance(Bundle bundle) {
        Home3Fragment fragment = new Home3Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home3;
    }

    @Override
    protected void createView() {
        initViewPager();
    }

    private void initViewPager(){
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(Home3ClubFragment.newInstance(null));
        fragments.add(Home3DynamicFragment.newInstance(null));
        fragments.add(Home3ActionRecentFragment.newInstance(null));
        fragments.add(Home3ClubFragment.newInstance(null));
        fragments.add(Home3VipIntroduceFragment.newInstance(null));
        ArrayList<CustomTabEntity> tabEntityList = new ArrayList<>();
        for (int i = 0; i < mTitle.length; i++){
            tabEntityList.add(new TabEntity(mTitle[i],R.mipmap.icon_home_rbtn1_unpress,R.mipmap.icon_home_rbtn1_pressed));
        }
        mCommonTabLayout.setTabData(tabEntityList);
//        ViewPagerTabLayoutAdapter viewPagerAdapter = new ViewPagerTabLayoutAdapter(this.getFragmentManager(),fragments,titleList);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this.getFragmentManager(),fragments);
        mViewPager.setAdapter(viewPagerAdapter);


        mCommonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCommonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(0);
        mCommonTabLayout.setCurrentTab(0);

//        mCommonTabLayout.setViewPager(mViewPager);
    }

}
