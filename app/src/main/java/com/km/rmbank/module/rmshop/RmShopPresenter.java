package com.km.rmbank.module.rmshop;

import com.km.rmbank.dto.GoodsTypeDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/4/12.
 */

public class RmShopPresenter extends PresenterWrapper<RmShopContract.View> implements RmShopContract.Presenter {

    public RmShopPresenter(RmShopContract.View mView) {
        super(mView);
    }

    @Override
    public void getGodosTypes() {
        mApiwrapper.getGoodsTypes()
                .subscribe(newSubscriber(new Consumer<List<GoodsTypeDto>>() {
                    @Override
                    public void accept(@NonNull List<GoodsTypeDto> goodsTypeDtos) throws Exception {
                        mView.getGoodsTypeSuccess(goodsTypeDtos);
                    }
                }));
    }

    @Override
    public void onCreateView() {
        getGodosTypes();
    }

}
