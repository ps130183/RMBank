package com.km.rmbank.module.club;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;

/**
 * Created by kamangkeji on 17/8/30.
 */

public interface EditClubImageTextInfoContract {
    interface View extends BaseView{
        void showImage(String imageUrl, int position);
    }

    interface Presenter extends BasePresenter{
        void uploadImage(String imagePath,int position);
    }
}
