package com.km.rmbank.module.personal.active;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ActiveGoodsDto;

/**
 * Created by kamangkeji on 17/9/12.
 */

public interface ActiveGoodsDetailContract {
    interface View extends BaseView{
        void showActiveGoodsDetailInfo(ActiveGoodsDto activeGoodsDto);
        void convertSuccess();
    }
    interface Presenter extends BasePresenter{
        void getActiveGoodsDetailInfo(String productNo);
        void convertActiveGoods(String productNo,String productCount,String receiveAddressId);
    }
}
