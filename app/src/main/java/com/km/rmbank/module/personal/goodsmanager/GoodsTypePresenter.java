package com.km.rmbank.module.personal.goodsmanager;

import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.GoodsTypeDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamangkeji on 17/4/6.
 */

public class GoodsTypePresenter extends PresenterWrapper<GoodsTypeContract.View> implements GoodsTypeContract.Presenter {


    public GoodsTypePresenter(GoodsTypeContract.View mView) {
        super(mView);
    }

    @Override
    public void getGoodsType() {
        List<GoodsTypeDto> goodsTypeDtos = new ArrayList<>();
        goodsTypeDtos.add(new GoodsTypeDto("武器"));
        goodsTypeDtos.add(new GoodsTypeDto("渔业"));
        goodsTypeDtos.add(new GoodsTypeDto("海产品"));
        goodsTypeDtos.add(new GoodsTypeDto("煤炭"));
        goodsTypeDtos.add(new GoodsTypeDto("互联网"));
        goodsTypeDtos.add(new GoodsTypeDto("林木"));
        mView.showGoodsType(goodsTypeDtos);
    }

    @Override
    public void onCreateView() {
        mView.initGoodsTypeView();
        getGoodsType();
    }
}
