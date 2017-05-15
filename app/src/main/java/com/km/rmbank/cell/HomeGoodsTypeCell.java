package com.km.rmbank.cell;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.km.rmbank.R;
import com.km.rmbank.adapter.ViewPagerAdapter;
import com.km.rmbank.entity.HomeGtEntity;
import com.km.rmbank.fragment.HomeFloorTwoFragment;
import com.km.rmbank.fragment.HomeGoodsTypeFragment;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamangkeji on 17/5/10.
 */

public class HomeGoodsTypeCell extends BaseCell<List<HomeGtEntity>> {

    private FragmentManager mFragmentManager;

    public HomeGoodsTypeCell(List<HomeGtEntity> mData, OnCellClickListener<List<HomeGtEntity>> onCellClickListener) {
        super(mData, R.layout.cell_home_goods_type, onCellClickListener);
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (mFragmentManager == null){
            throw new RuntimeException("please set fragmentManage");
        }
        ViewPager viewPager = holder.findView(R.id.viewpager);
        final LinearLayout llDot = holder.findView(R.id.ll_dot);
        Context context = llDot.getContext();

        List<Fragment> fragments = new ArrayList<>();
        List<HomeGtEntity> content = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++){
            if (i != 0 && i % 8 == 0){
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("goodsTypes", (ArrayList<HomeGtEntity>) content);
//                fragments.add(HomeFloorTwoFragment.newInstance(bundle));
                fragments.add(HomeGoodsTypeFragment.newInstance(bundle));
                content = new ArrayList<>();
            }
            content.add(mData.get(i));
        }

        if (content.size() > 0){
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("goodsTypes", (ArrayList<HomeGtEntity>) content);
            fragments.add(HomeGoodsTypeFragment.newInstance(bundle));
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(mFragmentManager,fragments);
        viewPager.setAdapter(adapter);

        //添加区分页数的 点
        for (int i = 0; i < fragments.size(); i++){
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(AppUtils.dip2px(context,4),AppUtils.dip2px(context,4),AppUtils.dip2px(context,4),AppUtils.dip2px(context,4));
            imageView.setLayoutParams(params);
            imageView.setImageResource(R.mipmap.ic_dot_gray);
            llDot.addView(imageView);
        }
        setSelectedDot(llDot,0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelectedDot(llDot,position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setmFragmentManager(FragmentManager mFragmentManager) {
        this.mFragmentManager = mFragmentManager;
    }

    /**
     * 根据当前页数 改变 点  的颜色
     * @param llDot
     * @param position
     */
    private void setSelectedDot(LinearLayout llDot,int position){
        for (int i = 0; i < llDot.getChildCount(); i++){
            ImageView imageView = (ImageView) llDot.getChildAt(i);
            if (position == i){
                imageView.setImageResource(R.mipmap.ic_dot_red);
            } else {
                imageView.setImageResource(R.mipmap.ic_dot_gray);
            }
        }
    }
}
