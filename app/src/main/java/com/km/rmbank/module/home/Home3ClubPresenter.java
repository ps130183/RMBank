package com.km.rmbank.module.home;

import com.km.rmbank.dto.InformationDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/7/19.
 */

public class Home3ClubPresenter extends PresenterWrapper<Home3ClubContract.View> implements Home3ClubContract.Presenter {

    public Home3ClubPresenter(Home3ClubContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getBannerList() {
        mView.showLoading();
        mApiwrapper.getInformationBanner()
                .subscribe(newSubscriber(new Consumer<List<InformationDto>>() {
                    @Override
                    public void accept(@NonNull List<InformationDto> informationDtos) throws Exception {
                        mView.showBannerList(informationDtos);
                    }
                }));
    }

    @Override
    public void getActionList(final int pageNo) {
        mView.showLoading();
        mApiwrapper.getInformationList(pageNo)
                .subscribe(newSubscriber(new Consumer<List<InformationDto>>() {
                    @Override
                    public void accept(@NonNull List<InformationDto> informationDtos) throws Exception {
                        mView.getActionListSuccess(informationDtos,pageNo);
                    }
                }));
    }
}
