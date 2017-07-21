package com.km.rmbank.module.club;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.utils.fileupload.FileUploadingListener;

/**
 * Created by kamangkeji on 17/7/11.
 */

public interface EditMyClubContract {
    interface View extends BaseView{
        void createMyClubSuccess();
        void imageUploadSuccess(String imageUrl,int imageType);
        void showImageUploadProgress(int imageType,int position,int progress);
    }
    interface Presenter extends BasePresenter{
        void createMyClub(ClubDto clubDto);
        void uploadClubImg(String imgPath,int imageType, int position);
        void editMyClub(ClubDto clubDto);
    }
}
