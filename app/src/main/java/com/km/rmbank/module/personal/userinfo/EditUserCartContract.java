package com.km.rmbank.module.personal.userinfo;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.UserCardDto;

/**
 * Created by kamangkeji on 17/3/31.
 */

public interface EditUserCartContract {
    interface View extends BaseView{
        void showUserCard(UserCardDto userCardDto);
        void createUserCardSuccess();
        void applyBecomeFriendSuccess();

        void addFriendSuccess();
    }
    interface Presenter extends BasePresenter{
        void getUserCard();
        void createUserCard(UserCardDto userCardDto);
        void applyBecomeFriend(String friendPhone);
        void getUserCardById(String userId);

        void addShopToFriend(String friendMobilePhone);
    }
}
