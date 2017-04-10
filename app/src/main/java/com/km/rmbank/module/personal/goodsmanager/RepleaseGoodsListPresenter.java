package com.km.rmbank.module.personal.goodsmanager;

import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/3/24.
 */

public class RepleaseGoodsListPresenter extends PresenterWrapper<RepleaseGoodsListContract.View> implements RepleaseGoodsListContract.Presenter {

    public RepleaseGoodsListPresenter(RepleaseGoodsListContract.View mView) {
        super(mView);
    }

    @Override
    public void loadRepleaseGoods(final int page) {
        mApiwrapper.getGoodsListOfShop(page)
                .subscribe(newSubscriber(new Consumer<List<GoodsDto>>() {
                    @Override
                    public void accept(@NonNull List<GoodsDto> goodsDtos) throws Exception {
                        mView.showRepleaseGoods(goodsDtos,page);
                    }

                }));
    }

    @Override
    public void onCreateView() {
        mView.initRecyclerView();
    }
}
