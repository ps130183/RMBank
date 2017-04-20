package com.km.rmbank.module.actionarea;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.InformationDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/4/12.
 */

public interface InformationContract {
    interface View extends BaseView{
        void initAction();
        void getActionListSuccess(List<InformationDto> actionDtos, int pageNo);
    }
    interface Presenter extends BasePresenter{
        void getActionList(int pageNo);
    }
}
