package com.km.rmbank.module.register;

import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.DefaultDto;

import rx.functions.Action1;

/**
 * Created by kamangkeji on 17/3/30.
 */

public class RegisterUserPresenter implements RegisterUserContract.Presenter {

    private RegisterUserContract.View view;
    private BaseActivity activity;

    private ApiWrapper mApiwrapper;

    public RegisterUserPresenter(RegisterUserContract.View view, BaseActivity activity) {
        this.view = view;
        this.activity = activity;
        mApiwrapper = ApiWrapper.getInstance();
    }

    @Override
    public void registerUser(String phone, String password,String smsCode) {
        view.showLoading();
        mApiwrapper.userRegister(phone,password,smsCode)
                .subscribe(activity.newSubscriber(new Action1<DefaultDto>() {

                    @Override
                    public void call(DefaultDto defaultDto) {
                        view.registerSuccess();
                    }
                }));
    }

    @Override
    public void forgetLoginPwd(String phone, String password, String smsCode) {
        view.showLoading();
        mApiwrapper.forgetLoginPwd(phone,password,smsCode)
                .subscribe(activity.newSubscriber(new Action1<DefaultDto>() {

                    @Override
                    public void call(DefaultDto defaultDto) {
                        view.registerSuccess();
                    }
                }));
    }

    @Override
    public void onCreateView() {

    }
}
