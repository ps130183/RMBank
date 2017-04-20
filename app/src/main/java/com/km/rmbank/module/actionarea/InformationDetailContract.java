package com.km.rmbank.module.actionarea;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;

/**
 * Created by kamangkeji on 17/4/20.
 */

public interface InformationDetailContract {
    interface View extends BaseView{
        void initWebView();
        void showInfomationDetail(String html);
    }
    interface Presenter extends BasePresenter{
        void getInformationDetail(String id);
    }
}
