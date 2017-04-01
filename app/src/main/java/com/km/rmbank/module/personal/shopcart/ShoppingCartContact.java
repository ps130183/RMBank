package com.km.rmbank.module.personal.shopcart;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.entity.ShoppingCartEntity;

import java.util.List;

/**
 * Created by kamangkeji on 17/3/20.
 */

public interface ShoppingCartContact {
    interface View extends BaseView{
        void showShoppingCartDatas(List<ShoppingCartEntity> shoppingCartEntities);
    }

    interface Presenter extends BasePresenter{
        void loadShoppingCartDatas();
    }
}
