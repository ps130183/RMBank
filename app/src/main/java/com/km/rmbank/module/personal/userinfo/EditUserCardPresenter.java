package com.km.rmbank.module.personal.userinfo;

import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.module.personal.userinfo.EditUserCartContract;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


/**
 * Created by kamangkeji on 17/3/31.
 */

public class EditUserCardPresenter extends PresenterWrapper<EditUserCartContract.View> implements EditUserCartContract.Presenter {


    public EditUserCardPresenter(EditUserCartContract.View mView) {
        super(mView);
    }

    @Override
    public void getUserCard() {
        mView.showLoading();
        mApiwrapper.getUserCard()
                .subscribe(newSubscriber(new Consumer<UserCardDto>() {
                    @Override
                    public void accept(@NonNull UserCardDto userCardDto) throws Exception {
                        mView.showUserCard(userCardDto);
                    }

                }));
    }

    @Override
    public void createUserCard(UserCardDto userCardDto) {
        mView.showLoading();
        mApiwrapper.createUserCart(userCardDto)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String userCardDto) throws Exception {
                        mView.createUserCardSuccess();
                    }

                }));
    }

    @Override
    public void applyBecomeFriend(String friendPhone) {
        mView.showLoading();
        mApiwrapper.applyBecomeFriend(friendPhone)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.applyBecomeFriendSuccess();
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
                        mView.showUserCard(userCardDto);
                    }
                }));
    }

    @Override
    public void onCreateView() {

    }
}
