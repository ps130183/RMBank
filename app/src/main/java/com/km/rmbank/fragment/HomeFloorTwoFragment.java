package com.km.rmbank.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.dto.HomeNewRecommendDto;
import com.km.rmbank.module.rmshop.goods.RmShopActivity;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.glide.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFloorTwoFragment extends BaseFragment {

    @BindView(R.id.ll_floor_two)
    LinearLayout llFloorTwo;

    @BindView(R.id.tv_floor_name1)
    TextView tvFloorName1;
    @BindView(R.id.tv_floor_name2)
    TextView tvFloorName2;
    @BindView(R.id.tv_floor_name3)
    TextView tvFloorName3;

    @BindView(R.id.iv_floor_image1)
    ImageView ivFloorImage1;
    @BindView(R.id.iv_floor_image2)
    ImageView ivFloorImage2;
    @BindView(R.id.iv_floor_image3)
    ImageView ivFloorImage3;

    private List<HomeNewRecommendDto.TypeListBean> typeListBeanList;
    private String levelOneId = "";
    public static HomeFloorTwoFragment newInstance(Bundle bundle) {

        HomeFloorTwoFragment fragment = new HomeFloorTwoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getContentView() {
        return R.layout.fragment_home_floor_two;
    }

    @Override
    protected void createView() {

        levelOneId = getArguments().getString("levelOneId");

        typeListBeanList = getArguments().getParcelableArrayList("typeList");

        int windowWidth = AppUtils.getCurWindowWidth(getContext()) - AppUtils.dip2px(getContext(),2);
        ivFloorImage1.getLayoutParams().height = windowWidth / 3 - AppUtils.dip2px(getContext(),16);
        ivFloorImage2.getLayoutParams().height = windowWidth / 3 - AppUtils.dip2px(getContext(),16);
        ivFloorImage3.getLayoutParams().height = windowWidth / 3 - AppUtils.dip2px(getContext(),16);

        tvFloorName1.setText(typeListBeanList.get(0).getProductTypeName());
        GlideUtils.loadImage(ivFloorImage1,typeListBeanList.get(0).getProductTypeImage());

        tvFloorName2.setText(typeListBeanList.get(1).getProductTypeName());
        GlideUtils.loadImage(ivFloorImage2,typeListBeanList.get(1).getProductTypeImage());

        tvFloorName3.setText(typeListBeanList.get(2).getProductTypeName());
        GlideUtils.loadImage(ivFloorImage3,typeListBeanList.get(2).getProductTypeImage());

    }

    @OnClick({R.id.ll_floor1,R.id.tv_floor_name1,R.id.iv_floor_image1})
    public void clickFloor1(View view){
        if (typeListBeanList.size() > 0){
            Bundle bundle = new Bundle();
            bundle.putBoolean("isLevelOne",false);
            bundle.putString("levelOneId",levelOneId);
            bundle.putString("levelTwoId",typeListBeanList.get(0).getId());
            toNextActivity(RmShopActivity.class,bundle);
        }
    }

    @OnClick({R.id.ll_floor2,R.id.tv_floor_name2,R.id.iv_floor_image2})
    public void clickFloor2(View view){
        if (typeListBeanList.size() > 1){
            Bundle bundle = new Bundle();
            bundle.putBoolean("isLevelOne",false);
            bundle.putString("levelOneId",levelOneId);
            bundle.putString("levelTwoId",typeListBeanList.get(1).getId());
            toNextActivity(RmShopActivity.class,bundle);
        }
    }

    @OnClick({R.id.ll_floor3,R.id.tv_floor_name3,R.id.iv_floor_image3})
    public void clickFloor3(View view){
        if (typeListBeanList.size() > 2){
            Bundle bundle = new Bundle();
            bundle.putBoolean("isLevelOne",false);
            bundle.putString("levelOneId",levelOneId);
            bundle.putString("levelTwoId",typeListBeanList.get(2).getId());
            toNextActivity(RmShopActivity.class,bundle);
        }
    }
}
