package com.km.rmbank.module.club.past;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ActionPastDto;

/**
 * Created by kamangkeji on 17/7/19.
 */

public interface ActionPastDetailContract {
    interface View extends BaseView{
        void showActionPastDetails(ActionPastDto actionPastDto);
    }
    interface Presenter extends BasePresenter{
        void getActionPastDetails(String id);
    }
}
