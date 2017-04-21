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
        void initAction(List<String> bannerImage);
        void getActionListSuccess(List<InformationDto> actionDtos, int pageNo);
        void showInformationBanner(List<InformationDto> informationDtos);
    }
    interface Presenter extends BasePresenter{
        void getActionList(int pageNo);
        void getInformationBanner();
    }
}
