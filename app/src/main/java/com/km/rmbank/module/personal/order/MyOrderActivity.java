package com.km.rmbank.module.personal.order;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.km.rmbank.R;
import com.km.rmbank.adapter.ViewPagerAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.ps.androidlib.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyOrderActivity extends BaseActivity {

    private String[] mTitles = {"未完成", "已完成"};
//    private BaseFragment[] fragments = {MyOrderFragment.newInstance(null),OrderFinishedFragment.newInstance(null)};


    @BindView(R.id.stl_title)
    SegmentTabLayout stlTitle;

    @BindView(R.id.viewpager)
    ViewPager mViewpager;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_order;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "";
    }

    @NonNull
    @Override
    protected List<CenterViewInterface> getCenterViews() {
        List<CenterViewInterface> centerViewInterfaces = new ArrayList<>();
        centerViewInterfaces.add(new OrderCenterView());
        return centerViewInterfaces;
    }

    class OrderCenterView implements CenterViewInterface{

        @Override
        public View getView() {
            return ViewUtils.getView(LayoutInflater.from(MyOrderActivity.this),null,R.layout.center_view_my_order);
        }

        @Override
        public void setViewWidget(View view) {

        }
    }

    @Override
    protected void onCreate() {
        initViewPager();
        initTitle();
    }

    private void initTitle(){
        stlTitle.setTabData(mTitles);

        stlTitle.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                stlTitle.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        stlTitle.setCurrentTab(0);
    }

    private void initViewPager(){
        List<Fragment> fragments = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putString("finishOrder","0");
        fragments.add(MyOrderFragment.newInstance(bundle));
        bundle = new Bundle();
        bundle.putString("finishOrder","4");
        fragments.add(MyOrderFragment.newInstance(bundle));
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),fragments);
        mViewpager.setAdapter(adapter);
     }
}
