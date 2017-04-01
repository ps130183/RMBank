package com.km.rmbank.module.personal.goodsmanager;

import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.GoodsDto;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamangkeji on 17/3/24.
 */

public class RepleaseGoodsListPresenter implements RepleaseGoodsListContract.Presenter {

    private RepleaseGoodsListContract.View view;
    private BaseActivity mActivity;

    public RepleaseGoodsListPresenter(RepleaseGoodsListContract.View view, BaseActivity mActivity) {
        this.view = view;
        this.mActivity = mActivity;
    }

    @Override
    public void loadRepleaseGoods(int page) {
        List<GoodsDto> goodsEntities = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            goodsEntities.add(new GoodsDto());
        }
        view.showRepleaseGoods(goodsEntities);
    }

    @Override
    public void onCreateView() {
        Logger.d("RepleaseGoodsListPresenter onCreateView");
        view.initRecyclerView();
        loadRepleaseGoods(1);
    }
}
