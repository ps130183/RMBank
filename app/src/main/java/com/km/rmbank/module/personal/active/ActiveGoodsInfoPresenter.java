package com.km.rmbank.module.personal.active;

import com.km.rmbank.dto.ActiveGoodsOrderDetailDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/9/13.
 */

public class ActiveGoodsInfoPresenter extends PresenterWrapper<ActiveGoodsInfoContract.View> implements ActiveGoodsInfoContract.Presenter {

    public ActiveGoodsInfoPresenter(ActiveGoodsInfoContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getActiveGoodsOrderDetail(String orderNo) {
        mView.showLoading();
        mApiwrapper.getActiveGoodsOrderDetail(orderNo)
                .subscribe(newSubscriber(new Consumer<ActiveGoodsOrderDetailDto>() {
                    @Override
                    public void accept(@NonNull ActiveGoodsOrderDetailDto activeGoodsOrderDetailDto) throws Exception {
                        mView.showActiveGoodsOrderDetail(activeGoodsOrderDetailDto);
                    }
                }));
    }
}
