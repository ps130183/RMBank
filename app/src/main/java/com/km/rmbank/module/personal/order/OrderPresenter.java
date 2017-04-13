package com.km.rmbank.module.personal.order;

import com.km.rmbank.dto.OrderDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

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
//        List<OrderDto> orderEntities = new ArrayList<>();
//        for (int i = 0; i < 10; i++){
//            orderEntities.add(new OrderDto());
//        }
        mView.showLoading();
        mApiwrapper.getOrderList(finishOrder,page)
                .subscribe(newSubscriber(new Consumer<List<OrderDto>>() {
                    @Override
                    public void accept(@NonNull List<OrderDto> orderEntities) throws Exception {
                        mView.showOrderList(orderEntities,page);
                    }
                }));
//        mView.showOrderList(orderEntities,page);
    }

    @Override
    public void onCreateView() {

    }
}
