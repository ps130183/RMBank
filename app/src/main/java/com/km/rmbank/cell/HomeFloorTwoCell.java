package com.km.rmbank.cell;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.ViewPagerAdapter;
import com.km.rmbank.dto.HomeNewRecommendDto;
import com.km.rmbank.fragment.HomeFloorTwoFragment;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamangkeji on 17/5/11.
 */

public class HomeFloorTwoCell extends BaseCell<HomeNewRecommendDto> implements View.OnClickListener {

    private FragmentManager mFragmentManager;
    private int itemViewType = 2000;

    public HomeFloorTwoCell(HomeNewRecommendDto mData, OnCellClickListener<HomeNewRecommendDto> onCellClickListener) {
        super(mData, R.layout.cell_home_floor_two, onCellClickListener);
    }

    @Override
    public int getItemViewType() {
        return itemViewType;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        if (mFragmentManager == null){
            throw new RuntimeException("please set fragmentManage");
        }

        TextView tvFloorTitle = holder.getTextView(R.id.tv_floor_title);
        tvFloorTitle.setText(mData.getRecommendName());
        TextView tvFloorSubTitle = holder.getTextView(R.id.tv_floor_sub_title);
        tvFloorSubTitle.setText(mData.getSubtitle());
        tvFloorTitle.setOnClickListener(null);
        tvFloorSubTitle.setOnClickListener(this);

        ViewPager viewPager = holder.findView(R.id.viewpager1);
        final LinearLayout llDot = holder.findView(R.id.ll_dot);

        //计算viewpager的高度
        Context context = viewPager.getContext();

        int windowWidth = AppUtils.getCurWindowWidth(context);
        int ivHeight = windowWidth / 3;
        viewPager.getLayoutParams().height = ivHeight + AppUtils.dip2px(context,31);

        List<Fragment> fragments = new ArrayList<>();
        List<HomeNewRecommendDto.TypeListBean> typeListBeanList = mData.getTypeList();

        List<HomeNewRecommendDto.TypeListBean> subTypeList = new ArrayList<>();

        for (int i = 0; i < typeListBeanList.size(); i++){
            if (i != 0 && i % 3 == 0){
                Bundle bundle = new Bundle();
                bundle.putString("levelOneId",mData.getProductTypeParentId());
                bundle.putParcelableArrayList("typeList", (ArrayList<? extends Parcelable>) subTypeList);
                fragments.add(HomeFloorTwoFragment.newInstance(bundle));
                subTypeList = new ArrayList<>();
            }
            subTypeList.add(typeListBeanList.get(i));

        }

        Bundle bundle = new Bundle();
        bundle.putString("levelOneId",mData.getProductTypeParentId());
        bundle.putParcelableArrayList("typeList", (ArrayList<? extends Parcelable>) subTypeList);
        fragments.add(HomeFloorTwoFragment.newInstance(bundle));
        subTypeList = new ArrayList<>();

        Logger.d("fragments.length = " + fragments.size());

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

    public void setmFragmentManager(FragmentManager mFragmentManager) {
        this.mFragmentManager = mFragmentManager;
    }

    @Override
    public void onClick(View v) {
        onCellClickListener.cellClick(mData,0);
    }

    public void setItemViewType(int itemViewType) {
        this.itemViewType += itemViewType;
    }
}
