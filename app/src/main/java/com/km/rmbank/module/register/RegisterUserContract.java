package com.km.rmbank.module.register;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;

/**
 * Created by kamangkeji on 17/3/30.
 */

public interface RegisterUserContract {
    interface  View extends BaseView{
        void registerSuccess();
        void registerFail(String message);
    }

    interface Presenter extends BasePresenter{
        void registerUser(String phone,String password,String smsCode);
        void forgetLoginPwd(String phone,String password,String smsCode);
    }
}
