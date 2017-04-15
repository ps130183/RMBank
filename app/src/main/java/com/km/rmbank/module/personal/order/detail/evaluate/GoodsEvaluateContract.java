package com.km.rmbank.module.personal.order.detail.evaluate;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;

/**
 * Created by kamangkeji on 17/4/15.
 */

public interface GoodsEvaluateContract {
    interface View extends BaseView{
        void evaluateSuccess();
    }
    interface Presenter extends BasePresenter{
        void evaluateGoods(String orderNo,String content);
    }
}
