package com.km.rmbank.module.login;

import com.km.rmbank.dto.UserDto;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/3/21.
 */

public class LoginPresenter extends PresenterWrapper<LoginContract.View> implements LoginContract.Presenter {


    public LoginPresenter(LoginContract.View mView) {
        super(mView);
    }

    @Override
    public void login(String mobilePhone, String passWord) {
        mView.showLoading();
        mApiwrapper.login(mobilePhone,passWord)
                .subscribe(newSubscriber(new Consumer<UserDto>() {
                    @Override
                    public void accept(@NonNull UserDto userDto) throws Exception {
                        userDto.saveToSp();
                        Constant.user.getDataFromSp();
                        mView.loginSuccess();
                    }

                }));
    }

    @Override
    public void onCreateView() {

    }
}
