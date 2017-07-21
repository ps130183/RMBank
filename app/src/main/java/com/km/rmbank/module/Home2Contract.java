package com.km.rmbank.module;

import android.view.View;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.MyFriendsDto;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.dto.UserCardDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/5/15.
 */

public interface Home2Contract {
    interface View extends BaseView{
        void showShareContent(ShareDto shareDto);
        void getUserInfoByQRCodeSuccess(UserCardDto userCardDto, String friendPhone);
        void locationSuccess();

        void showMyFriends(List<MyFriendsDto> myFriendsDtos);
    }
    interface Presenter extends BasePresenter{
        void getShareContent();
        void getUserInfoByQRCode(String url);
        void getUserLocation(String longitude,String latitude);

        void getMyFriends();
    }
}
