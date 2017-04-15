package com.km.rmbank.module.personal.order.detail;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.OrderDto;

/**
 * Created by kamangkeji on 17/4/14.
 */

public interface OrderDetailContract {
    interface  View extends BaseView{
        void initOrderDetail(OrderDto orderDto);
        void sendGoodsSuccess();
        void receiverGoodsSuccess();
    }
    interface Presenter extends BasePresenter{
        void getOrderDetail(OrderDto orderDto);
        void shopSendGoods(OrderDto orderDto,String expressCompany,String expressNumber);
        void confirmReceiverGoods(OrderDto orderDto);
    }
}
