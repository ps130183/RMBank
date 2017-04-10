package com.km.rmbank.module.personal;

import com.km.rmbank.dto.UserInfoDto;
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
        mApiwrapper.getUserInfo()
                .subscribe(newSubscriber(new Consumer<UserInfoDto>() {
                    @Override
                    public void accept(@NonNull UserInfoDto userInfoDto) throws Exception {
                        mView.showUserInfo(userInfoDto);
                    }
                }));
    }

    @Override
    public void onCreateView() {

    }
}
