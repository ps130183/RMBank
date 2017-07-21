package com.km.rmbank.module.club;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ActionPastDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/7/18.
 */

public interface ClubActionPastContract {
    interface View extends BaseView{
        void showActionPast(List<ActionPastDto> actionPastDtos,int pageNo);
    }
    interface Presenter extends BasePresenter{
        void getActionPasList(String clubId,int pageNo);
    }
}
