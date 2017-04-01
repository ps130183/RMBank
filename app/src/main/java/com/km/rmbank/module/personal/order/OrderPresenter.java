package com.km.rmbank.module.personal.order;

import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.cell.OrderCell;
import com.km.rmbank.entity.OrderEntity;
import com.km.rv_libs.base.ICell;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamangkeji on 17/3/24.
 */

public class OrderPresenter implements OrderContract.Presenter {

    private OrderContract.View view;
    private BaseActivity mActivity;

    public OrderPresenter(OrderContract.View view, BaseActivity mActivity) {
        this.view = view;
        this.mActivity = mActivity;
    }

    @Override
    public void loadOrder(int page, boolean finishOrder) {
        List<OrderEntity> orderEntities = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            orderEntities.add(new OrderEntity());
        }
        view.showOrderList(orderEntities,page);
    }

    @Override
    public void onCreateView() {

    }
}
