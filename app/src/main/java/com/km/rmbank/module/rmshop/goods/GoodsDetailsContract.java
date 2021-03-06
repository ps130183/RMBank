package com.km.rmbank.module.rmshop.goods;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.dto.UserCardDto;

/**
 * Created by kamangkeji on 17/4/6.
 */

public interface GoodsDetailsContract {
    interface View extends BaseView{
        void showGoodsDetails(GoodsDetailsDto goodsDetailsDto);
        void followGoodsSuccess();
        void addShoppingCartSuccess();
        void showShopUserCartInfo(UserCardDto userCardDto);
    }
    interface Presenter extends BasePresenter{
        void getGoodsDetails(String productNo);
        void followGoods(String productNo);
        void addShoppingCart(String productNo,String count);
        void getShopUserCartInfo(String id);


    }
}
