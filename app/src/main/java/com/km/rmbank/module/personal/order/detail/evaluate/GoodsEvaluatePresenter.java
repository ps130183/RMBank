package com.km.rmbank.module.personal.order.detail.evaluate;

import com.km.rmbank.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/4/15.
 */

public class GoodsEvaluatePresenter extends PresenterWrapper<GoodsEvaluateContract.View> implements GoodsEvaluateContract.Presenter {
    public GoodsEvaluatePresenter(GoodsEvaluateContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void evaluateGoods(String orderNo, String content) {
        mView.showLoading();
        mApiwrapper.evaluateGoods(orderNo,content)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.evaluateSuccess();
                    }
                }));
    }
}
