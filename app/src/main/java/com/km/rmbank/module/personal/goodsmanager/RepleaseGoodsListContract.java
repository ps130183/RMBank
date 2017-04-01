package com.km.rmbank.module.personal.goodsmanager;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.GoodsDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/3/24.
 */

public interface RepleaseGoodsListContract {
    interface View extends BaseView{
        void initRecyclerView();
        void showRepleaseGoods(List<GoodsDto> goodsEntities);
    }
    interface Presenter extends BasePresenter{
        void loadRepleaseGoods(int page);
    }
}
