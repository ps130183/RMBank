package com.km.rmbank.module.personal.order;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.OrderDto;
import com.km.rmbank.dto.PayOrderDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/3/24.
 */

public interface OrderContract {
    interface View extends BaseView{
        void showOrderList(List<OrderDto> orderEntities, int page);
        void getPayOrderSuccess(PayOrderDto payOrderDto);

    }
    interface Presenter extends BasePresenter{
        void loadOrder(int page,String finishOrder);
        void getPayOrder(OrderDto orderDto);
    }
}
