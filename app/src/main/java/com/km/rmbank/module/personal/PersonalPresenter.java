package com.km.rmbank.module.personal;

import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/3/31.
 */

public class PersonalPresenter extends PresenterWrapper<PersonalContract.View> implements PersonalContract.Presenter {

    public PersonalPresenter(PersonalContract.View mView) {
        super(mView);
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
    public void onCreateView() {

    }
}
