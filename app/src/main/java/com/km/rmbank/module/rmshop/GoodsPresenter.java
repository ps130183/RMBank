package com.km.rmbank.module.rmshop;

import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.GoodsDto;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by kamangkeji on 17/4/1.
 */

public class GoodsPresenter implements GoodsContract.Presenter {

    private GoodsContract.View view;
    private BaseActivity activity;

    private ApiWrapper apiWrapper;
    public GoodsPresenter(GoodsContract.View view, BaseActivity activity) {
        this.view = view;
        this.activity = activity;
        apiWrapper = ApiWrapper.getInstance();
    }

    @Override
    public void loadGoodsList(final int pageNo) {
        Logger.d("当前View == " + view.toString() + "  pageNo == " + pageNo);
        view.showLoading();
        apiWrapper.getGoodsListOfShopping(pageNo)
                .subscribe(activity.newSubscriber(new Action1<List<GoodsDto>>() {
                    @Override
                    public void call(List<GoodsDto> goodsDtos) {
                        view.showGoodsList(goodsDtos,pageNo);
                    }
                }));
    }

    @Override
    public void onCreateView() {
        view.initGoodsList();
    }
}
