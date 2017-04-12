package com.km.rmbank.module.personal.order;

import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.cell.OrderCell;
import com.km.rmbank.entity.OrderEntity;
import com.km.rmbank.utils.retrofit.PresenterWrapper;
import com.km.rv_libs.base.ICell;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/3/24.
 */

public class OrderPresenter extends PresenterWrapper<OrderContract.View> implements OrderContract.Presenter {


    public OrderPresenter(OrderContract.View mView) {
        super(mView);
    }

    @Override
    public void loadOrder(final int page, String finishOrder) {
//        List<OrderEntity> orderEntities = new ArrayList<>();
//        for (int i = 0; i < 10; i++){
//            orderEntities.add(new OrderEntity());
//        }
        mView.showLoading();
        mApiwrapper.getOrderList(finishOrder,page)
                .subscribe(newSubscriber(new Consumer<List<OrderEntity>>() {
                    @Override
                    public void accept(@NonNull List<OrderEntity> orderEntities) throws Exception {
                        mView.showOrderList(orderEntities,page);
                    }
                }));
//        mView.showOrderList(orderEntities,page);
    }

    @Override
    public void onCreateView() {

    }
}
