package com.km.rmbank.module.register;

import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.DefaultDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/3/30.
 */

public class RegisterUserPresenter extends PresenterWrapper<RegisterUserContract.View> implements RegisterUserContract.Presenter {


    public RegisterUserPresenter(RegisterUserContract.View mView) {
        super(mView);
    }

    @Override
    public void registerUser(String phone, String password,String smsCode) {
        mView.showLoading();
        mApiwrapper.userRegister(phone,password,smsCode)
                .subscribe(newSubscriber(new Consumer() {

                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        mView.registerSuccess();
                    }
                }));
    }

    @Override
    public void forgetLoginPwd(String phone, String password, String smsCode) {
        mView.showLoading();
        mApiwrapper.forgetLoginPwd(phone,password,smsCode)
                .subscribe(newSubscriber(new Consumer() {

                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        mView.registerSuccess();
                    }
                }));
    }

    @Override
    public void onCreateView() {

    }
}
