package com.km.rmbank.module.rmshop.goods;

import android.os.Bundle;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.BasePresenter;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.BannerUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/3/17.
 */

public class GoodsInfoFragment extends BaseFragment {

    @BindView(R.id.banner)
    Banner banner;
    private int[] bannerRes = {R.mipmap.timg,R.mipmap.timg,R.mipmap.timg,R.mipmap.timg,R.mipmap.timg};

    public static GoodsInfoFragment newInstance(Bundle bundle) {
        GoodsInfoFragment fragment = new GoodsInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_goods_info;
    }

    @Override
    protected void createView() {
        initBanner();
    }


    private void initBanner(){
        int windowWidth = AppUtils.getCurWindowWidth(getContext());
        banner.getLayoutParams().height = windowWidth;
        List<Integer> bannerList = new ArrayList<>();
        for (int i = 0; i < bannerRes.length; i++){
            bannerList.add(bannerRes[i]);
        }
        BannerUtils.initBanner(banner, bannerList, new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                showToast(bannerRes[position] + "");
            }
        });
    }
}
