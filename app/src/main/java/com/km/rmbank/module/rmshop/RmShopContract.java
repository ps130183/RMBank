package com.km.rmbank.module.rmshop;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.GoodsTypeDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/4/12.
 */

public interface RmShopContract {
    interface View extends BaseView{
        void getGoodsTypeSuccess(List<GoodsTypeDto> goodsTypeDtos);
    }
    interface Presenter extends BasePresenter{
        void getGodosTypes();
    }
}
