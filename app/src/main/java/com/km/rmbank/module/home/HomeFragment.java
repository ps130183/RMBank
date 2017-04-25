package com.km.rmbank.module.home;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.km.rmbank.R;
import com.km.rmbank.adapter.HomeAdapter;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.BannerDto;
import com.km.rmbank.dto.HomeRecommendDto;
import com.km.rmbank.entity.HomeEntity;
import com.km.rmbank.module.actionarea.InformationDetailActivity;
import com.km.rmbank.module.home.message.MessageActivity;
import com.km.rmbank.module.rmshop.goods.GoodsActivity;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.BannerUtils;
import com.ps.androidlib.utils.MToast;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by kamangkeji on 17/3/14.
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.banner)
    Banner banner;

    @BindView(R.id.rc_content)
    RecyclerView rcContent;

    @BindView(R.id.et_search)
    EditText etSearch;

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
    public HomePresenter getmPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected void createView() {
//        initBanner();
        int windowWidth = AppUtils.getCurWindowWidth(getContext());
        appBar.getLayoutParams().height = windowWidth / 4 * 3;
        initrcContentView();
    }

    private void initrcContentView(){
        RVUtils.setLinearLayoutManage(rcContent, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rcContent);
        final HomeAdapter adapter = new HomeAdapter(getContext());
        rcContent.setAdapter(adapter);
        adapter.setOnClickGoodsListener(new HomeAdapter.OnClickGoodsListener() {
            @Override
            public void clickGoods(HomeRecommendDto.ProductReconmmendListBean bean) {
                Bundle bundle = new Bundle();
                bundle.putString("productNo",bean.getProductNo());
                toNextActivity(GoodsActivity.class,bundle);
            }
        });

        adapter.addLoadMore(rcContent, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getRecommend(adapter.getNextPage());
            }
        });
        mPresenter.getRecommend(adapter.getNextPage());
    }

    @Override
    public void getRecommendSuccess(List<HomeRecommendDto> homeRecommendDtos,int pageNo) {
        HomeAdapter adapter = (HomeAdapter) rcContent.getAdapter();
        adapter.addData(homeRecommendDtos,pageNo);
    }

    @Override
    public void showHomeBanner(final List<BannerDto> bannerDtos) {
        List<String> bannerImages = new ArrayList<>();
        for (BannerDto bannerDto : bannerDtos){
            bannerImages.add(bannerDto.getAvatarUrl());
        }
        BannerUtils.initBannerFromUrl(banner, bannerImages, new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerDto bannerDto = bannerDtos.get(position);
                String type = bannerDto.getType();
                Bundle bundle = new Bundle();
                if ("1".equals(type)){
                    bundle.putString("informationId",bannerDto.getId());
                    bundle.putString("informationTitle",bannerDto.getTitle());
                    toNextActivity(InformationDetailActivity.class,bundle);
                } else if ("2".equals(type)){
                    bundle.putString("productNo",bannerDto.getId());
                    toNextActivity(GoodsActivity.class,bundle);
                }
//                showToast("banner type " + bannerDto.getType());
            }
        });
    }

    @OnClick(R.id.et_search)
    public void etSearch(View view){
        toNextActivity(SearchActivity.class);
    }

    @OnClick(R.id.tv_message)
    public void message(View view){
        toNextActivity(MessageActivity.class);
    }
}
