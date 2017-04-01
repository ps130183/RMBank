package com.km.rmbank.module.register;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;

/**
 * Created by kamangkeji on 17/3/21.
 */

public interface RegisterContract {
    interface View extends BaseView{
        void getCodeSuccess();
    }

    interface Presenter extends BasePresenter{
        void getCode(String mobilePhone);
    }
}
