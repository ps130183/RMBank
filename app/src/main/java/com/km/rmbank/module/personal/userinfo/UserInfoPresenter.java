package com.km.rmbank.module.personal.userinfo;

import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.utils.fileupload.FileUploadingListener;
import com.km.rmbank.utils.retrofit.PresenterWrapper;
import com.orhanobut.logger.Logger;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * Created by kamangkeji on 17/3/31.
 */

public class UserInfoPresenter extends PresenterWrapper<UserInfoContract.View> implements UserInfoContract.Presenter {


    public UserInfoPresenter(UserInfoContract.View mView) {
        super(mView);
    }

    @Override
    public void uploadProtrait(String imagePath) {
        FileUploadingListener listener = new FileUploadingListener() {
            @Override
            public void onProgress(int progress) {
                Logger.d("progress = " + progress);
            }
        };
        mApiwrapper.imageUpload("3", imagePath,listener)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.uploadProtraitSuccess(s);
                    }
                });


    }

    @Override
    public void saveUserInfo(UserInfoDto userInfoDto) {
        mView.showLoading();
        mApiwrapper.updateUserInfo(userInfoDto)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.saveUserInfoSuccess();
                    }

                }));
    }

    @Override
    public void onCreateView() {

    }
}
