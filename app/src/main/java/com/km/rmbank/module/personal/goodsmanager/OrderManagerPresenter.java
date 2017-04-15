package com.km.rmbank.module.personal.goodsmanager;

import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.dto.OrderDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/4/14.
 */

public class OrderManagerPresenter extends PresenterWrapper<OrderManagerContract.View> implements OrderManagerContract.Presenter {

    public OrderManagerPresenter(OrderManagerContract.View mView) {
        super(mView);
    }

    @Override
    public void getSellOrder(final int pageNo) {
        mApiwrapper.getSellGoodsList(pageNo)
                .subscribe(newSubscriber(new Consumer<List<OrderDto>>() {
                    @Override
                    public void accept(@NonNull List<OrderDto> goodsDtos) throws Exception {
                        mView.getSellOrderSuccess(goodsDtos,pageNo);
                    }
                }));
    }

    @Override
    public void onCreateView() {
        mView.initRecyclerView();
    }
}
