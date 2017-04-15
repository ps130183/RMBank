package com.km.rmbank.module.payment;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.dto.WeiCharParamsDto;

/**
 * Created by kamangkeji on 17/4/6.
 */

public interface PayContract {
    interface View extends BaseView{
        void createPayOrderSuccess(PayOrderDto payOrderDto);
        void getAlipayParamsSuccess(String alipayParamsDto);
        void getWeiCharParamsSuccess(WeiCharParamsDto weicharParams);
        void payBalanceSuccess();
    }
    interface Presenter extends BasePresenter{
        void createPayOrder(String amount);
        void getAliPayOrder(String payNumber);
        void getWeiChatParams(String payNumber);
        void payBalance(String payNumber);
    }
}
