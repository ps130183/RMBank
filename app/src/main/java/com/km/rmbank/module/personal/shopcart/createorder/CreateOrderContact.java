package com.km.rmbank.module.personal.shopcart.createorder;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.entity.ShoppingCartEntity;

import java.util.List;

/**
 * Created by kamangkeji on 17/3/21.
 */

public interface CreateOrderContact {
    interface View extends BaseView{
        void showOrderDatas(List<ShoppingCartEntity> cartEntities);
    }
    interface Presenter extends BasePresenter{
        void loadOrderDatas();
    }
}
