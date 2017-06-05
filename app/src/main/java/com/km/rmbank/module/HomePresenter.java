package com.km.rmbank.module;

import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/5/15.
 */

public class HomePresenter extends PresenterWrapper<HomeContract.View> implements HomeContract.Presenter {

    public HomePresenter(HomeContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {
        getShareContent();
    }

    @Override
    public void getShareContent() {
        if (Constant.user.isEmpty()){
            return;
        }
        mApiwrapper.getShareContent()
                .subscribe(newSubscriber(new Consumer<ShareDto>() {
                    @Override
                    public void accept(@NonNull ShareDto shareDto) throws Exception {
                        mView.showShareContent(shareDto);
                    }
                }));
    }

    @Override
    public void getUserInfoByQRCode(final String url) {
        mView.showLoading();
        if (Constant.user.isEmpty()){
            return;
        }
        mApiwrapper.getUserCardOnQRCode(url)
                .subscribe(newSubscriber(new Consumer<UserCardDto>() {
                    @Override
                    public void accept(@NonNull UserCardDto userCardDto) throws Exception {
                        String phone = url.split("[?]")[1].split("=")[1];
                        mView.getUserInfoByQRCodeSuccess(userCardDto,phone);
                    }
                }));
    }

    @Override
    public void getUserLocation(String longitude, String latitude) {
        if (Constant.user.isEmpty()){
            return;
        }
        mApiwrapper.updateUserLocation(longitude,latitude)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.locationSuccess();
                    }
                }));
    }

}
