package com.km.rmbank.module.personal.goodsmanager;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.GoodsTypeDto;
import com.km.rmbank.dto.HomeGoodsTypeDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/4/6.
 */

public interface GoodsTypeContract {
    interface View extends BaseView{
        void initGoodsTypeView();
        void showGoodsType(List<HomeGoodsTypeDto> goodsTypeDtos);
    }
    interface Presenter extends BasePresenter{
        void getGoodsType();
    }
}
