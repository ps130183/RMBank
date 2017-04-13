package com.km.rmbank.module.home;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.GoodsDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/4/13.
 */

public interface SearchContract {
    interface View extends BaseView{
        void initRecyclerview();
        void showSearchResult(List<GoodsDto> goodsDtos,int pageNo);
    }
    interface Presenter extends BasePresenter{
        void getSearchGoods(int pageNo,String name);
    }
}
