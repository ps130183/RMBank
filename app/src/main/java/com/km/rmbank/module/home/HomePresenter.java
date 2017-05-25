package com.km.rmbank.module.home;

import com.km.rmbank.dto.BannerDto;
import com.km.rmbank.dto.HomeGoodsTypeDto;
import com.km.rmbank.dto.HomeNewRecommendDto;
import com.km.rmbank.dto.HomeRecommendDto;
import com.km.rmbank.entity.HomeDataEntity;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/4/12.
 */

public class HomePresenter extends PresenterWrapper<HomeContract.View> implements HomeContract.Presenter {

    public HomePresenter(HomeContract.View mView) {
        super(mView);
    }

    @Override
    public void getRecommend(final int pageNo) {
        mView.showLoading();
        mApiwrapper.getHomeRecommend(pageNo)
                .subscribe(newSubscriber(new Consumer<List<HomeRecommendDto>>() {
                    @Override
                    public void accept(@NonNull List<HomeRecommendDto> homeRecommendDtos) throws Exception {
                        mView.getRecommendSuccess(homeRecommendDtos,pageNo);
                    }
                }));
    }

    @Override
    public void getHomeBanner() {
        mView.showLoading();
        mApiwrapper.getHomeBanner()
                .subscribe(newSubscriber(new Consumer<List<BannerDto>>() {
                    @Override
                    public void accept(@NonNull List<BannerDto> bannerDtos) throws Exception {
                        mView.showHomeBanner(bannerDtos);
                    }
                }));
    }

    @Override
    public void getHomeGoodsType() {
        mView.showLoading();
        mApiwrapper.getHomeGoodsTypes()
                .subscribe(newSubscriber(new Consumer<List<HomeGoodsTypeDto>>() {
                    @Override
                    public void accept(@NonNull List<HomeGoodsTypeDto> homeGoodsTypeDtos) throws Exception {
//                        homeDataEntity.setHomeGoodsTypeDtos(homeGoodsTypeDtos);
                        mView.showHomeGoodsType(homeGoodsTypeDtos);
                        getHomeNewRecommend(1);
                    }
                }));


    }

    @Override
    public void getHomeNewRecommend(int pageNo) {
        mApiwrapper.getHomeNewRecommend(pageNo)
                .subscribe(newSubscriber(new Consumer<List<HomeNewRecommendDto>>() {
                    @Override
                    public void accept(@NonNull List<HomeNewRecommendDto> homeNewRecommendDtos) throws Exception {
                        mView.ShowHomeNewRecommend(homeNewRecommendDtos);
                    }
                }));
    }

    @Override
    public void onCreateView() {
//        getHomeBanner();
        getHomeGoodsType();
    }
}
