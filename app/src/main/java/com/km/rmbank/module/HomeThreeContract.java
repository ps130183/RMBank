package com.km.rmbank.module;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.InformationDto;
import com.km.rmbank.dto.MyFriendsDto;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.dto.UserInfoDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/7/2.
 */

public interface HomeThreeContract {
    interface View extends BaseView{
        void getUserInfoByQRCodeSuccess(UserCardDto userCardDto, String friendPhone);
        void locationSuccess();

        void showMyFriends(List<MyFriendsDto> myFriendsDtos);

        void showPastActions(List<InformationDto> informationDtos,int curPageNo);

        void showUserInfo(UserInfoDto userInfoDto);
    }

    interface Presenter extends BasePresenter{

        void getUserInfoByQRCode(String url);
        void getUserLocation(String longitude,String latitude);

        void getMyFriends();

        void getPastActions(int pageNo);

        void loadUserInfo();
    }
}
