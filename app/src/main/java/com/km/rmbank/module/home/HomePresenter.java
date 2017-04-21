package com.km.rmbank.module.home;

import com.km.rmbank.dto.BannerDto;
import com.km.rmbank.dto.HomeRecommendDto;
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
    public void onCreateView() {
        getHomeBanner();
    }
}
