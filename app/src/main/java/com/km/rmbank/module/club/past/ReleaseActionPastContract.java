package com.km.rmbank.module.club.past;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.ActionPastDto;

/**
 * Created by kamangkeji on 17/7/18.
 */

public interface ReleaseActionPastContract {
    interface View extends BaseView{
        void uploadActionImgSuccess(String imageUrl,int imageType,int position);
        void showUploadImgProgress(int imageType, int position, int progress);
        void releaseActionSuccess();
    }
    interface Presenter extends BasePresenter{
        void uploadActionImg(String imagePath,int imageType,int position);
        void releaseActionRecent(ActionPastDto actionDto);
    }
}
