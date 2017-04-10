package com.km.rmbank.module.rmshop.goods;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.GoodsDetailsDto;

/**
 * Created by kamangkeji on 17/4/6.
 */

public interface GoodsDetailsContract {
    interface View extends BaseView{
        void showGoodsDetails(GoodsDetailsDto goodsDetailsDto);
        void followGoodsSuccess();
    }
    interface Presenter extends BasePresenter{
        void getGoodsDetails(String productNo);
        void followGoods(String productNo);
    }
}