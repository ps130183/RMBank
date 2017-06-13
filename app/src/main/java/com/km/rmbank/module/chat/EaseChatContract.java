package com.km.rmbank.module.chat;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.dto.UserInfoDto;

/**
 * Created by kamangkeji on 17/6/9.
 */

public interface EaseChatContract {
    interface View extends BaseView{
        void showUserCart(UserCardDto userCardDto, String phone);
        void showCurLoginUserInfo(UserInfoDto userInfoDto);
    }
    interface Presenter extends BasePresenter{
        void getUserCardInfo(String url);
        void getCurLoginUserInfo();
    }
}
