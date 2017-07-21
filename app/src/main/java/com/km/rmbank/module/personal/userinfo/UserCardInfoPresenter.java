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
}
