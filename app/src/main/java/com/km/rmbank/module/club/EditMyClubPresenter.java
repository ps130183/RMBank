package com.km.rmbank.module.club;

import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.utils.fileupload.FileUploadingListener;
import com.km.rmbank.utils.retrofit.PresenterWrapper;
import com.orhanobut.logger.Logger;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/7/11.
 */

public class EditMyClubPresenter extends PresenterWrapper<EditMyClubContract.View> implements EditMyClubContract.Presenter {

    public EditMyClubPresenter(EditMyClubContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void createMyClub(ClubDto clubDto) {
        mView.showLoading();
        mApiwrapper.createMyClub(clubDto)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.createMyClubSuccess();
                    }
                }));
    }

    @Override
    public void uploadClubImg(String imgPath, final int imageType, final int position) {
        FileUploadingListener listener = new FileUploadingListener() {
            @Override
            public void onProgress(final int progress) {
                Observable.just(progress)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(@NonNull Integer integer) throws Exception {
//                                Logger.d(imageType + ":  progress = " + progress);
                                mView.showImageUploadProgress(imageType,position,progress);
                            }
                        });

            }
        };
        mApiwrapper.imageUpload("5",imgPath,listener)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.imageUploadSuccess(s,imageType);
                    }
                }));
    }

    @Override
    public void editMyClub(ClubDto clubDto) {
        mView.showLoading();
        mApiwrapper.editMyClub(clubDto)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.createMyClubSuccess();
                    }
                }));
    }
}
