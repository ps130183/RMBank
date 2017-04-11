package com.km.rmbank.module.personal.shopcart.createorder;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.dto.ShoppingCartDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/3/21.
 */

public interface CreateOrderContact {
    interface View extends BaseView{
        void showDefaultReceiverAddress(ReceiverAddressDto receiverAddressDto);
        void showOrderDatas(List<ShoppingCartDto> cartEntities);
        void submitOrderSuccess(PayOrderDto payOrderDto);
    }
    interface Presenter extends BasePresenter{
        void getDefaultReceiverAddress();
        void loadOrderDatas();
        void submitOrder(String productNos,String productCounts,String receiveAddressId,String freight,String intrgral);
    }
}
