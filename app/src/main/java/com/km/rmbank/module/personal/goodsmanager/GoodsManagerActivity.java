package com.km.rmbank.module.personal.goodsmanager;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.km.rmbank.R;
import com.km.rmbank.adapter.ViewPagerAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.module.personal.order.MyOrderActivity;
import com.ps.androidlib.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GoodsManagerActivity extends BaseActivity {

    private String[] mTitles = {"已发布", "已售出"};

    @BindView(R.id.stl_title)
    SegmentTabLayout stlTitle;
//    @BindView(R.id.viewpager)
//    ViewPager mViewpager;

    @BindView(R.id.activity_goods_manager)
    FrameLayout mFrameLayout;

    RepleaseGoodsListFragment repleaseGoodsListFragment1;
    OrderManagerFragment orderManagerFragment;

    @Override
    protected int getContentView() {
        return R.layout.activity_goods_manager;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return null;
    }

    @NonNull
    @Override
    protected List<CenterViewInterface> getCenterViews() {
        List<CenterViewInterface> centerViewInterfaces = new ArrayList<>();
        centerViewInterfaces.add(new GoodsManagerCenterView());
        return centerViewInterfaces;
    }

    class GoodsManagerCenterView implements CenterViewInterface{

        @Override
        public View getView() {
            return ViewUtils.getView(LayoutInflater.from(GoodsManagerActivity.this),null,R.layout.center_view_my_order);
        }

        @Override
        public void setViewWidget(View view) {

        }
    }

    @Override
    protected void onCreate() {

        setRightIconClick(R.mipmap.ic_add_block_48px, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toNextActivity(CreateNewGoodsActivity.class);
            }
        });

        initTitle();
    }

    private void initTitle(){
        stlTitle.setTabData(mTitles);

        stlTitle.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                setPageByTitleBar(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        stlTitle.setCurrentTab(0);
        setPageByTitleBar(0);
    }

    private void setPageByTitleBar(int position){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (position){
            case 0:
                if (repleaseGoodsListFragment1 == null){
                    repleaseGoodsListFragment1 = RepleaseGoodsListFragment.newInstance(null);
                }
                ft.replace(R.id.activity_goods_manager,repleaseGoodsListFragment1);
                break;
            case 1:
                if (orderManagerFragment == null){
                    orderManagerFragment = OrderManagerFragment.newInstance(null);
                }
                ft.replace(R.id.activity_goods_manager,orderManagerFragment);
                break;
        }
        ft.commit();
    }
}
