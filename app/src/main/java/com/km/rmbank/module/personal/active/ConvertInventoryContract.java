package com.km.rmbank.module.personal.active;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ActiveGoodsOrderListDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/9/13.
 */

public interface ConvertInventoryContract {
    interface View extends BaseView{
        void showActiveGoodsOrderList(List<ActiveGoodsOrderListDto> activeGoodsOrderListDtos, int pageNo);
    }
    interface Presenter extends BasePresenter{
        void getActiveGoodsOrderList(int pageNo);
    }
}
