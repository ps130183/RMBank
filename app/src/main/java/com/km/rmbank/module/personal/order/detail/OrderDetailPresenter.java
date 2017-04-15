package com.km.rmbank.module.personal.order.detail;

import com.km.rmbank.dto.OrderDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/4/14.
 */

public class OrderDetailPresenter extends PresenterWrapper<OrderDetailContract.View> implements OrderDetailContract.Presenter {
    public OrderDetailPresenter(OrderDetailContract.View mView) {
        super(mView);
    }

    @Override
    public void getOrderDetail(OrderDto orderDto) {
        mView.showLoading();
        mApiwrapper.getOrderDetails(orderDto)
                .subscribe(newSubscriber(new Consumer<OrderDto>() {
                    @Override
                    public void accept(@NonNull OrderDto orderDto) throws Exception {
                        mView.initOrderDetail(orderDto);
                    }
                }));
    }

    @Override
    public void shopSendGoods(OrderDto orderDto, String expressCompany, String expressNumber) {
        mView.showLoading();
        mApiwrapper.sendGoods(orderDto.getOrderNo(),expressCompany,expressNumber)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.sendGoodsSuccess();
                    }
                }));
    }

    @Override
    public void confirmReceiverGoods(OrderDto orderDto) {
        mView.showLoading();
        mApiwrapper.confirmReceiverGoods(orderDto)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.receiverGoodsSuccess();
                    }
                }));
    }

    @Override
    public void onCreateView() {

    }
}
