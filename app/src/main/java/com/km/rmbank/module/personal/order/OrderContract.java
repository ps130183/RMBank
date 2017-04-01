package com.km.rmbank.module.personal.order;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.entity.OrderEntity;
import com.km.rv_libs.base.ICell;

import java.util.List;

/**
 * Created by kamangkeji on 17/3/24.
 */

public interface OrderContract {
    interface View extends BaseView{
        void showOrderList(List<OrderEntity> orderEntities, int page);
    }
    interface Presenter extends BasePresenter{
        void loadOrder(int page,boolean finishOrder);
    }
}
