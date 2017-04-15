package com.km.rmbank.module.personal.shopcart;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.dto.ShoppingCartDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/3/20.
 */

public interface ShoppingCartContact {
    interface View extends BaseView{
        void showShoppingCartDatas(List<ShoppingCartDto> shoppingCartEntities);
        void updateGoodsCountSuccess(GoodsDetailsDto goodsDto,String optiontType);
        void createOrderSuccess(List<ShoppingCartDto> shoppingCartDtos);
        void deleteSuccess(int positionOnParent, int positionOnSub);
    }

    interface Presenter extends BasePresenter{
        void loadShoppingCartDatas();
        void updateGoodsCount(GoodsDetailsDto productNo, String optionType);
        void createOrder(String productNos);
        void deleteGoods(GoodsDetailsDto goodsDetailsDto,int positionOnParent, int positionOnSub);
    }
}
