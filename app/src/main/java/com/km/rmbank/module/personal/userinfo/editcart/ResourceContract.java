package com.km.rmbank.module.personal.userinfo.editcart;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.IndustryDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/3/30.
 */

public interface ResourceContract {
    interface View extends BaseView{
        void showIndustry(List<IndustryDto> industryEntities);
    }
    interface Presenter extends BasePresenter{
        void loadIndustryData();
    }
}
