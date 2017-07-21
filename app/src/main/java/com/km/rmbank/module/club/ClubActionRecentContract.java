package com.km.rmbank.module.club;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ActionDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/7/13.
 */

public interface ClubActionRecentContract {
    interface View extends BaseView{
        void showActionRecent(List<ActionDto> actionDtos,int pageNo);
    }
    interface Presenter extends BasePresenter{
        void getActionRecent(String clubId,int pageNo);
    }
}
