package com.km.rmbank.module.login;

import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.DefaultDto;
import com.km.rmbank.dto.UserDto;
import com.ps.androidlib.utils.SPUtils;

import rx.functions.Action1;

/**
 * Created by kamangkeji on 17/3/21.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;
    private BaseActivity mActivity;
    private ApiWrapper mApiwrapper;

    public LoginPresenter(LoginContract.View view, BaseActivity mActivity) {
        this.view = view;
        this.mActivity = mActivity;
        mApiwrapper = ApiWrapper.getInstance();
    }

    @Override
    public void login(String mobilePhone, String passWord) {
        view.showLoading();
        mApiwrapper.login(mobilePhone,passWord)
                .subscribe(mActivity.newSubscriber(new Action1<UserDto>() {
                    @Override
                    public void call(UserDto userDto) {
                        userDto.saveToSp();
                        view.loginSuccess();
                    }
                }));
    }

    @Override
    public void onCreateView() {

    }
}
