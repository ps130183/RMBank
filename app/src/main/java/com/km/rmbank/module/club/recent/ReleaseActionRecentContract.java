package com.km.rmbank.module.club.recent;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ActionDto;

/**
 * Created by kamangkeji on 17/7/13.
 */

public interface ReleaseActionRecentContract {
    interface View extends BaseView{
        void uploadActionImgSuccess(String imageUrl,int imageType,int position);
        void showUploadImgProgress(int imageType, int position, int progress);
        void releaseActionSuccess();
    }
    interface Presenter extends BasePresenter{
        void uploadActionImg(String imagePath,int imageType,int position);
        void releaseActionRecent(ActionDto actionDto,String clubId);
    }
}
