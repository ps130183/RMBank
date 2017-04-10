package com.km.rmbank.module.rmshop.goods;

import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/4/6.
 */

public class GoodsDetailsPresenter extends PresenterWrapper<GoodsDetailsContract.View> implements GoodsDetailsContract.Presenter {


    public GoodsDetailsPresenter(GoodsDetailsContract.View mView) {
        super(mView);
    }

    @Override
    public void getGoodsDetails(String productNo) {
        mView.showLoading();
        mApiwrapper.getGoodsDetails(productNo)
                .subscribe(newSubscriber(new Consumer<GoodsDetailsDto>() {
                    @Override
                    public void accept(@NonNull GoodsDetailsDto goodsDetailsDto) throws Exception {
                        mView.showGoodsDetails(goodsDetailsDto);
                    }

                }));
    }

    @Override
    public void followGoods(String productNo) {
        mApiwrapper.followGodos(productNo)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.followGoodsSuccess();
                    }
                }));
    }

    @Override
    public void onCreateView() {

    }
}
