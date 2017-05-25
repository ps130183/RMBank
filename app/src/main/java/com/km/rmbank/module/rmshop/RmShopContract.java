package com.km.rmbank.module.rmshop;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.dto.GoodsTypeDto;
import com.km.rmbank.dto.HomeGoodsTypeDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/4/12.
 */

public interface RmShopContract {
    interface View extends BaseView{
        void getGoodsTypeSuccess(List<GoodsTypeDto> goodsTypeDtos);
        void showGoodsType(List<HomeGoodsTypeDto> goodsTypeDtos);
        void showGoodsList(int pageNo,List<GoodsDto> goodsDtos);
    }
    interface Presenter extends BasePresenter{
        void getGodosTypes();
        void getGoodsList(int pageNo,String isInIndextActivity,int orderBy,String roleId);
    }
}
