package com.km.rmbank.module.club.recent;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ActionMemberDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/7/14.
 */

public interface ActionJoinMemberContract {
    interface View extends BaseView{
        void showActionMemberList(List<ActionMemberDto> actionMemberDtos,int pageNo);
        void showActionMemberNum(String number);
    }
    interface Presenter extends BasePresenter{
        void getActionMemberList(String actionId,int pageNo);

        void getActionMemberNum(String actionId);
    }
}
