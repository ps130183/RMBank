package com.km.rmbank.module.personal.userinfo;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.UserCardDto;

/**
 * Created by kamangkeji on 17/7/21.
 */

public interface UserCardInfoContract {
    interface View extends BaseView{
        void showUserCardInfo(UserCardDto userCardDto);
        void applyBecomeFriendSuccess();
    }
    interface Presenter extends BasePresenter{
        void getUserCardInfo();
        void getUserCardById(String userId);

        void addShopToFriend(String friendMobilePhone);

        void applyBecomeMyFriends(String mobilePhone);
    }
}
