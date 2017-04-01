package com.km.rmbank.module.rmshop.goods;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.km.rmbank.R;
import com.km.rmbank.adapter.ViewPagerTabLayoutAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BasePresenter;
import com.ps.androidlib.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GoodsActivity extends BaseActivity {

    private String[] mTitle = {"商品","详情","评价"};

    @BindView(R.id.s_tab_layout)
    SlidingTabLayout mTabLayout;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;



    @Override
    protected int getContentView() {
        return R.layout.activity_goods;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return null;
    }

    @Override
    protected void onCreate() {
        initViewPager();
    }

    @NonNull
    @Override
    protected List<CenterViewInterface> getCenterViews() {
        List<CenterViewInterface> centerViews = new ArrayList<>();
        centerViews.add(new GoodsCenterView());
        return centerViews;
    }

    class GoodsCenterView implements CenterViewInterface{

        @Override
        public View getView() {
            return ViewUtils.getView(LayoutInflater.from(context),null,R.layout.center_view_goods);
        }

        @Override
        public void setViewWidget(View view) {

        }
    }

    private void initViewPager(){

        List<String> mTitleList = new ArrayList<>();
        for (String title : mTitle){
            mTitleList.add(title);
        }

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(GoodsInfoFragment.newInstance(null));//商品信息
        fragments.add(GoodsDetailsFragment.newInstance(null));//商品详情
        fragments.add(GoodsInfoFragment.newInstance(null));//评价信息
        ViewPagerTabLayoutAdapter adapter = new ViewPagerTabLayoutAdapter(getSupportFragmentManager(),fragments,mTitleList);
        mViewPager.setAdapter(adapter);
        mTabLayout.setViewPager(mViewPager);
    }
}
