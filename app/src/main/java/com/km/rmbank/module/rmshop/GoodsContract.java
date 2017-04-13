package com.km.rmbank.module.rmshop;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.GoodsDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/4/1.
 */

public interface GoodsContract {
    interface View extends BaseView{
        void initGoodsList();
        void showGoodsList(List<GoodsDto> goodsDtos,int pageNo);
    }
    interface Presenter extends BasePresenter{
        void loadGoodsList(int pageNo,String typeId);
    }
}
