package com.km.rmbank.module.rmshop.goods;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.EvaluateDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/4/13.
 */

public interface GoodsEvaluateContract {
    interface View extends BaseView{
        void initRecyclerview();
        void showUserEvaluate(List<EvaluateDto> evaluateDtos,int pageNo);
    }
    interface Presenter extends BasePresenter{
        void getUserEvaluate(String productNo,int pageNo);
    }
}
