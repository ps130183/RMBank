package com.km.rmbank.module.personal.integral;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.IntegralDetailsDto;
import com.km.rmbank.dto.IntegralDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/4/15.
 */

public interface MyIntegralContract {
    interface View extends BaseView{
        void showUserIntegralInfo(IntegralDto integralDto);
        void showIntegralDetails(List<IntegralDetailsDto> integralDetailsDtos,int pageNo);
    }
    interface Presenter extends BasePresenter{
        void getUserIntegralInfo();
        void getIntegralDetails(int pageNo);
    }
}
