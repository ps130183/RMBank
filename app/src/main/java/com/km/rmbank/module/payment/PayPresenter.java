package com.km.rmbank.module.payment;

import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.dto.WeiCharParamsDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by kamangkeji on 17/4/6.
 */

public class PayPresenter extends PresenterWrapper<PayContract.View> implements PayContract.Presenter {

    public PayPresenter(PayContract.View mView) {
        super(mView);
    }

    @Override
    public void createPayOrder(String amount) {
       mView.showLoading();
        mApiwrapper.createPayOrder(amount)
                .subscribe(newSubscriber(new Consumer<PayOrderDto>() {
                    @Override
                    public void accept(@NonNull PayOrderDto payOrderDto) throws Exception {
                        mView.createPayOrderSuccess(payOrderDto);
                    }
                }));
    }


    @Override
    public void getAliPayOrder(String payNumber) {
        mView.showLoading();
        mApiwrapper.getAlipayParams(payNumber)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String alipayParamsDto) throws Exception {
                        mView.getAlipayParamsSuccess(alipayParamsDto);
                    }

                }));
    }

    @Override
    public void getWeiChatParams(String payNumber) {
        mView.showLoading();
        mApiwrapper.getWeiChatParams(payNumber)
                .subscribe(newSubscriber(new Consumer<WeiCharParamsDto>() {
                    @Override
                    public void accept(@NonNull WeiCharParamsDto weiCharParamsDto) throws Exception {
                        mView.getWeiCharParamsSuccess(weiCharParamsDto);
                    }
                }));
    }

    @Override
    public void payBalance(String payNumber) {
        mView.showLoading();
        mApiwrapper.payBalance(payNumber)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.payBalanceSuccess();
                    }
                }));
    }

    @Override
    public void onCreateView() {

    }
}
