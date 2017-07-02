package com.km.rmbank.module.home;

import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/7/1.
 */

public class HomeClubTabPresenter extends PresenterWrapper<HomeClubTanContract.View> implements HomeClubTanContract.Presenter {

    public HomeClubTabPresenter(HomeClubTanContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getClubInfos(String type) {
        mView.showLoading();
        mApiwrapper.getClubInfos(type)
                .subscribe(newSubscriber(new Consumer<List<ClubDto>>() {
                    @Override
                    public void accept(@NonNull List<ClubDto> clubDtos) throws Exception {
                        mView.showClubInfos(clubDtos);
                    }
                }));
    }
}
