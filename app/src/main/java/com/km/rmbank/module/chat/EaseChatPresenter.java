package com.km.rmbank.module.chat;

import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/6/9.
 */

public class EaseChatPresenter extends PresenterWrapper<EaseChatContract.View> implements EaseChatContract.Presenter {

    public EaseChatPresenter(EaseChatContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {
        getCurLoginUserInfo();
    }

    @Override
    public void getUserCardInfo(final String url) {
        mView.showLoading();
        if (Constant.user.isEmpty()){
            return;
        }
        mApiwrapper.getUserCardOnQRCode(url)
                .subscribe(newSubscriber(new Consumer<UserCardDto>() {
                    @Override
                    public void accept(@NonNull UserCardDto userCardDto) throws Exception {
                        String phone = url.split("[?]")[1].split("=")[1];
                        mView.showUserCart(userCardDto,phone);
                    }
                }));
    }

    @Override
    public void getCurLoginUserInfo() {
        mApiwrapper.getUserInfo()
                .subscribe(newSubscriber(new Consumer<UserInfoDto>() {
                    @Override
                    public void accept(@NonNull UserInfoDto userInfoDto) throws Exception {
                        mView.showCurLoginUserInfo(userInfoDto);
                    }
                }));
    }
}
