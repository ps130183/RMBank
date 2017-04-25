package com.km.rmbank.module.rmshop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.km.rmbank.R;
import com.km.rmbank.adapter.ViewPagerTabLayoutAdapter;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.dto.GoodsTypeDto;
import com.ps.androidlib.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/3/14.
 */

public class RmShopFragment extends BaseFragment<RmShopPresenter> implements RmShopContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.s_tab_layout)
    SlidingTabLayout mStabLayout;
    private String[] mTitle = {"全部分类","海产品","农产品","电子产品","服装","化妆品","海参","笔记本"};


    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private int tabCurPosition;
    private List<String> tabList;

    public static RmShopFragment newInstance(Bundle bundle) {
        RmShopFragment fragment = new RmShopFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home_rmshop;
    }

    @Override
    public RmShopPresenter getmPresenter() {
        return new RmShopPresenter(this);
    }

    @Override
    protected void createView() {
        toolbar.setBackgroundResource(R.color.color_white);
        title.setTextColor(ContextCompat.getColor(getContext(),R.color.color_block));
        title.setText("商城");
//        initViewPager();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void initViewPager(){
        List<Fragment> fragments = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        for (int i = 0; i < mTitle.length; i++){
            titleList.add(mTitle[i]);
            Bundle bundle  = new Bundle();
            bundle.putString("tabname", mTitle[i]);
            bundle.putInt("tabid",i);
            fragments.add(GoodsFragment.newInstance(bundle));
        }
        ViewPagerTabLayoutAdapter viewPagerAdapter = new ViewPagerTabLayoutAdapter(this.getFragmentManager(),fragments,titleList);
        viewPager.setAdapter(viewPagerAdapter);

        mStabLayout.setViewPager(viewPager);
    }

    @Override
    public void getGoodsTypeSuccess(List<GoodsTypeDto> goodsTypeDtos) {
        GoodsTypeDto allGoods = new GoodsTypeDto("全部分类");
        goodsTypeDtos.add(0,allGoods);
        List<Fragment> fragments = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        for (GoodsTypeDto goodsTypeDto : goodsTypeDtos){
            titleList.add(goodsTypeDto.getProductType());
            Bundle bundle = new Bundle();
            bundle.putParcelable("goodsTypeDto",goodsTypeDto);
            fragments.add(GoodsFragment.newInstance(bundle));
        }
        ViewPagerTabLayoutAdapter viewPagerAdapter = new ViewPagerTabLayoutAdapter(this.getFragmentManager(),fragments,titleList);
        viewPager.setAdapter(viewPagerAdapter);

        mStabLayout.setViewPager(viewPager);
    }
}
