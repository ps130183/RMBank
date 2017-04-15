package com.km.rmbank.module.personal.goodsmanager;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.dto.OrderDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/4/14.
 */

public interface OrderManagerContract {
    interface View extends BaseView{
        void initRecyclerView();
        void getSellOrderSuccess(List<OrderDto> goodsDtos, int pageNo);
    }
    interface Presenter extends BasePresenter{
        void getSellOrder(int pageNo);
    }
}
