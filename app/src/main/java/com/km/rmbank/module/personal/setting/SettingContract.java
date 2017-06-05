package com.km.rmbank.module.personal.setting;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;

/**
 * Created by kamangkeji on 17/6/5.
 */

public interface SettingContract {
    interface View extends BaseView{
        void showAllowUserCardResult(boolean isAllow);
    }
    interface Presenter extends BasePresenter{
        void updateAllowUserCard(boolean isAllow);
    }
}
