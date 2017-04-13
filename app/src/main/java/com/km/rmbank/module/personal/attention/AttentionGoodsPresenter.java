package com.km.rmbank.module.personal.attention;

import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/4/12.
 */

public class AttentionGoodsPresenter extends PresenterWrapper<AttentionGoodsContract.View> implements AttentionGoodsContract.Presenter {

    public AttentionGoodsPresenter(AttentionGoodsContract.View mView) {
        super(mView);
    }

    @Override
    public void getAttentionGoods(final int pageNo) {
        mView.showLoading();
        mApiwrapper.getAttentionGoodsList(pageNo)
                .subscribe(newSubscriber(new Consumer<List<GoodsDto>>() {
                    @Override
                    public void accept(@NonNull List<GoodsDto> goodsDtos) throws Exception {
                        mView.getAttentionGoodsSuccess(goodsDtos,pageNo);
                    }
                }));
    }

    @Override
    public void onCreateView() {
        mView.initAttentionGoods();
    }
}
