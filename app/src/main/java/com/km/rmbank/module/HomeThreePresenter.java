package com.km.rmbank.module;

import com.km.rmbank.dto.InformationDto;
import com.km.rmbank.dto.MyFriendsDto;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/7/2.
 */

public class HomeThreePresenter extends PresenterWrapper<HomeThreeContract.View> implements HomeThreeContract.Presenter {

    public HomeThreePresenter(HomeThreeContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {
        if (!Constant.user.isEmpty()){
            getMyFriends();
        }
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

    @Override
    public void getMyFriends() {
        mApiwrapper.getMyFriends()
                .subscribe(newSubscriber(new Consumer<List<MyFriendsDto>>() {
                    @Override
                    public void accept(@NonNull List<MyFriendsDto> myFriendsDtos) throws Exception {
                        mView.showMyFriends(myFriendsDtos);
                    }
                }));
    }

    @Override
    public void getPastActions(final int pageNo) {
        mView.showLoading();
        mApiwrapper.getInformationList(pageNo)
                .subscribe(newSubscriber(new Consumer<List<InformationDto>>() {
                    @Override
                    public void accept(@NonNull List<InformationDto> informationDtos) throws Exception {
                        mView.showPastActions(informationDtos,pageNo);
                    }
                }));
    }

    @Override
    public void loadUserInfo() {
        if (Constant.user.isEmpty()){
            return;
        }
        mApiwrapper.getUserInfo()
                .subscribe(newSubscriber(new Consumer<UserInfoDto>() {
                    @Override
                    public void accept(@NonNull UserInfoDto userInfoDto) throws Exception {
                        mView.showUserInfo(userInfoDto);
                    }
                }));
    }
}
