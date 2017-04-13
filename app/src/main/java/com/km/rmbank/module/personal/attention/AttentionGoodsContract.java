package com.km.rmbank.module.personal.attention;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.GoodsDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/4/12.
 */

public interface AttentionGoodsContract {
    interface View extends BaseView{
        void initAttentionGoods();
        void getAttentionGoodsSuccess(List<GoodsDto> goodsDtos,int pageNo);
    }
    interface Presenter extends BasePresenter{
        void getAttentionGoods(int pageNo);
    }
}
