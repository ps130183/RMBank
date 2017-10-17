package com.km.rmbank.module.personal.active;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ActiveGoodsOrderDetailDto;

/**
 * Created by kamangkeji on 17/9/13.
 */

public interface ActiveGoodsInfoContract {
    interface View extends BaseView{
        void  showActiveGoodsOrderDetail(ActiveGoodsOrderDetailDto orderDetailDto);
    }

    interface Presenter extends BasePresenter{
        void getActiveGoodsOrderDetail(String orderNo);
    }
}
