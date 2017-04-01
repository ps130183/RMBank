package com.km.rmbank.module.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.HomeAdapter;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.entity.HomeEntity;
import com.ps.androidlib.utils.BannerUtils;
import com.ps.androidlib.utils.MToast;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/3/14.
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.banner)
    Banner banner;

    @BindView(R.id.rc_content)
    RecyclerView rcContent;

    public static HomeFragment newInstance(Bundle bundle) {
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void createView() {
        initBanner();
        initrcContentView();
    }


    private void initBanner(){
        List<Integer> images = new ArrayList<>();
        images.add(R.mipmap.timg);
        images.add(R.mipmap.timg);
        images.add(R.mipmap.timg);
        images.add(R.mipmap.timg);
        BannerUtils.initBanner(banner, images, new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                MToast.showToast(getContext(),"当前位置：" + position);
            }
        });
    }

    private void initrcContentView(){
        RVUtils.setLinearLayoutManage(rcContent, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rcContent);
        HomeAdapter adapter = new HomeAdapter(getContext(),R.layout.item_rc_home);
        rcContent.setAdapter(adapter);

        List<HomeEntity> homeModels = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            homeModels.add(new HomeEntity());
        }
        adapter.addData(homeModels);
    }
}
