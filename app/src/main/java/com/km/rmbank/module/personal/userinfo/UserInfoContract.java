package com.km.rmbank.module.personal.userinfo;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.UserInfoDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/3/31.
 */

public interface UserInfoContract {
    interface View extends BaseView{
        void uploadProtraitSuccess(String imageUri);
        void saveUserInfoSuccess();
    }
    interface Presenter extends BasePresenter{
        void uploadProtrait(String imagePath);
        void saveUserInfo(UserInfoDto userInfoDto);
    }

}
