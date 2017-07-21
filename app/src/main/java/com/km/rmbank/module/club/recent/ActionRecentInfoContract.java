package com.km.rmbank.module.club.recent;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.ActionMemberDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/7/14.
 */

public interface ActionRecentInfoContract {
    interface View extends BaseView{
        void showActionRecentInfo(ActionDto actionDto);
        void applyActionSuccess();
    }
    interface Presenter extends BasePresenter{
        void getActionRecentInfo(String actionId);
        void applyAction(String activityId,String name,String phone);
    }
}
