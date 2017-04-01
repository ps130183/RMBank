package com.km.rmbank.module.personal;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.UserInfoDto;

/**
 * Created by kamangkeji on 17/3/31.
 */

public interface PersonalContract {
    interface View extends BaseView{
        void showUserInfo(UserInfoDto userInfoDto);
    }
    interface Presenter extends BasePresenter{
        void loadUserInfo();
    }
}
