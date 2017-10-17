package com.km.rmbank.module.personal.active;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ActiveGoodsDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/9/12.
 */

public interface ConvertActiveGoodsListContract {
    interface View extends BaseView{
        void showConvertActiveGoodsList(List<ActiveGoodsDto> activeGoodsDtos,int pageNo);
    }
    interface Presenter extends BasePresenter{
        void getConvertActiveGoodsList(int pageNo);
    }
}
