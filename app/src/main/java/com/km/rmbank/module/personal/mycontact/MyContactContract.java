package com.km.rmbank.module.personal.mycontact;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.MyFriendsDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/6/8.
 */

public interface MyContactContract {
    interface View extends BaseView{
        void initRecyclerview();
        void showMyContact(List<MyFriendsDto> myFriendsDtos);
    }

    interface Presenter extends BasePresenter{
        void getMyFriends();
    }
}
