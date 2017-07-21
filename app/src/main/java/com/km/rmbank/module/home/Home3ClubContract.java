package com.km.rmbank.module.home;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.InformationDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/7/19.
 */

public interface Home3ClubContract {
    interface View extends BaseView{
        void showBannerList(List<InformationDto> informationDtos);
        void getActionListSuccess(List<InformationDto> actionDtos, int pageNo);
    }
    interface Presenter extends BasePresenter{
        void getBannerList();
        void getActionList(int pageNo);
    }
}
