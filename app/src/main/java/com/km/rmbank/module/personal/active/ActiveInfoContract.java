package com.km.rmbank.module.personal.active;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ActiveValueDetailDto;
import com.km.rmbank.dto.ActiveValueDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/9/11.
 */

public interface ActiveInfoContract {
    interface View extends BaseView{
        void showActiveValue(ActiveValueDto activeValueDto);
        void showActiveValueDetail(List<ActiveValueDetailDto> activeValueDetailDtos,int pageNo);
    }
    interface Presenter extends BasePresenter{
        void getActiveValue();
        void getActiveValueDetail(int pageNo);
    }
}
