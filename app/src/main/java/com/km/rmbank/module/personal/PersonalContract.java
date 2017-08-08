package com.km.rmbank.module.personal;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ServiceDto;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.dto.UserInfoDto;

/**
 * Created by kamangkeji on 17/3/31.
 */

public interface PersonalContract {
    interface View extends BaseView{
        void showUserInfo(UserInfoDto userInfoDto);
        void getUserInfoByQRCodeSuccess(UserCardDto userCardDto,String friendPhone);
        void showShareContent(ShareDto shareDto);
        void  chatService(ServiceDto serviceDto);
    }
    interface Presenter extends BasePresenter{
        void loadUserInfo();
        void getUserInfoByQRCode(String url);
        void getShareContent();
        void getService();
    }
}
