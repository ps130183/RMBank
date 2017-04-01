package com.km.rmbank.module.personal;

import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.UserDto;
import com.km.rmbank.dto.UserInfoDto;
import com.orhanobut.logger.Logger;

import rx.functions.Action1;

/**
 * Created by kamangkeji on 17/3/31.
 */

public class PersonalPresenter implements PersonalContract.Presenter {

    private PersonalContract.View view;
    private BaseActivity activity;

    private ApiWrapper mApiwrapper;
    public PersonalPresenter(PersonalContract.View view, BaseActivity activity) {
        this.view = view;
        this.activity = activity;
        mApiwrapper = ApiWrapper.getInstance();
    }

    @Override
    public void loadUserInfo() {
        mApiwrapper.getUserInfo()
                .subscribe(activity.newSubscriber(new Action1<UserInfoDto>() {
                    @Override
                    public void call(UserInfoDto userInfoDto) {
                        view.showUserInfo(userInfoDto);
                    }
                }));
    }

    @Override
    public void onCreateView() {

    }
}
