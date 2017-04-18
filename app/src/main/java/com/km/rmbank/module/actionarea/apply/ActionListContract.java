package com.km.rmbank.module.actionarea.apply;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ActionDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/4/18.
 */

public interface ActionListContract {
    interface View extends BaseView{
        void initRecyclerview();
        void showActionList(List<ActionDto> actionDtos,int pageNo);
    }
    interface Presenter extends BasePresenter{
        void getActionList(int pageNo);
    }
}
