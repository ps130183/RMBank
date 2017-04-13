package com.km.rmbank.module.rmshop;

import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;
import com.orhanobut.logger.Logger;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/4/1.
 */

public class GoodsPresenter extends PresenterWrapper<GoodsContract.View> implements GoodsContract.Presenter {

    public GoodsPresenter(GoodsContract.View mView) {
        super(mView);
    }

    @Override
    public void loadGoodsList(final int pageNo,String typeId) {
//        Logger.d("当前View == " + view.toString() + "  pageNo == " + pageNo);
        mView.showLoading();
        mApiwrapper.getGoodsListOfShopping(pageNo,typeId)
                .subscribe(newSubscriber(new Consumer<List<GoodsDto>>() {
                    @Override
                    public void accept(@NonNull List<GoodsDto> goodsDtos) throws Exception {
                        mView.showGoodsList(goodsDtos,pageNo);
                    }
                }));
    }

    @Override
    public void onCreateView() {
        mView.initGoodsList();
    }
}
