package com.km.rmbank.module;

import android.view.View;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ShareDto;

/**
 * Created by kamangkeji on 17/5/15.
 */

public interface HomeContract {
    interface View extends BaseView{
        void showShareContent(ShareDto shareDto);
    }
    interface Presenter extends BasePresenter{
        void getShareContent();
    }
}
