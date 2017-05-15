package com.km.rmbank.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseFragment;
import com.ps.androidlib.utils.AppUtils;

import butterknife.BindView;

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

        int windowWidth = AppUtils.getCurWindowWidth(getContext()) - AppUtils.dip2px(getContext(),2);
        ivFloorImage1.getLayoutParams().height = windowWidth / 3;
        ivFloorImage2.getLayoutParams().height = windowWidth / 3;
        ivFloorImage3.getLayoutParams().height = windowWidth / 3;


    }
}
