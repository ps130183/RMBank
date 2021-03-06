package com.km.rmbank.module.login;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;

/**
 * Created by kamangkeji on 17/3/21.
 */

public interface LoginContract {
    interface View extends BaseView{
        void loginSuccess();
        void createUserCard(String phone);
        void getPhoneCodeSuccess();
    }

    interface Presenter extends BasePresenter{
        void login(String mobilePhone,String passWord);
        void getPhoneCode(String phone);
    }
}
