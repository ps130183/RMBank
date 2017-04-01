package com.km.rmbank.module.personal.userinfo;

import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.utils.SecretConstant;

import rx.functions.Action1;

/**
 * Created by kamangkeji on 17/3/31.
 */

public class UserInfoPresenter implements UserInfoContract.Presenter {

    private UserInfoContract.View view;
    private BaseActivity activity;

    private ApiWrapper mApiWrapper;
    public UserInfoPresenter(UserInfoContract.View view, BaseActivity activity) {
        this.view = view;
        this.activity = activity;
        mApiWrapper = ApiWrapper.getInstance();
    }

    @Override
    public void uploadProtrait(String imagePath) {
        view.showLoading();
        mApiWrapper.imageUpload("3",imagePath)
                .subscribe(activity.newSubscriber(new Action1<String>() {

                    @Override
                    public void call(String s) {
                        view.uploadProtraitSuccess(s);
                    }
                }));
    }

    @Override
    public void saveUserInfo(UserInfoDto userInfoDto) {
        view.showLoading();
        mApiWrapper.updateUserInfo(userInfoDto)
                .subscribe(activity.newSubscriber(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        view.saveUserInfoSuccess();
                    }
                }));
    }

    @Override
    public void onCreateView() {

    }
}
