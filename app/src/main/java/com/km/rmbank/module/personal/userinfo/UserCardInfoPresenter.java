package com.km.rmbank.module.personal.userinfo;

import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/7/21.
 */

public class UserCardInfoPresenter extends PresenterWrapper<UserCardInfoContract.View> implements UserCardInfoContract.Presenter {

    public UserCardInfoPresenter(UserCardInfoContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getUserCardInfo() {
        mView.showLoading();
        mApiwrapper.getUserCard()
                .subscribe(newSubscriber(new Consumer<UserCardDto>() {
                    @Override
                    public void accept(@NonNull UserCardDto userCardDto) throws Exception {
                        mView.showUserCardInfo(userCardDto);
                    }
                }));
    }

    @Override
    public void getUserCardById(String userId) {
        mView.showLoading();
        mApiwrapper.getUserCardById(userId)
                .subscribe(newSubscriber(new Consumer<UserCardDto>() {
                    @Override
                    public void accept(@NonNull UserCardDto userCardDto) throws Exception {
                        mView.showUserCardInfo(userCardDto);
                    }
                }));
    }

    @Override
    public void addShopToFriend(String friendMobilePhone) {
        mView.showLoading();
        mApiwrapper.applyBecomeFriend(friendMobilePhone)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {

                    }
                }));
    }

    @Override
    public void applyBecomeMyFriends(String mobilePhone) {
        mView.showLoading();
        mApiwrapper.applyBecomeFriend(mobilePhone)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.applyBecomeFriendSuccess();
                    }
                }));
    }
}
