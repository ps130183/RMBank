package com.km.rmbank.module.home;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ActionDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/7/19.
 */

public interface Home3ActionRecentContract {
    interface View extends BaseView{
        void showActionRecent(List<ActionDto> actionDtos,int pageNo);
    }
    interface Presenter extends BasePresenter{
        void getActionRecent(int pageNo);
    }
}
