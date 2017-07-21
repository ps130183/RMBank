package com.km.rmbank.module.home;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.InformationDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/7/20.
 */

public interface Home3DynamicContract {
    interface View extends BaseView{
        void showDynamicInformationList(List<InformationDto> informationDtos);
    }
    interface Presenter extends BasePresenter{
        void getDynamicInformationList(int pageNo);
    }
}
