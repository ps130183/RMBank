package com.km.rmbank.module.club.recent;

import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.utils.fileupload.FileUploadingListener;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/7/13.
 */

public class ReleaseActionRecentPresenter extends PresenterWrapper<ReleaseActionRecentContract.View> implements ReleaseActionRecentContract.Presenter {

    public ReleaseActionRecentPresenter(ReleaseActionRecentContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void uploadActionImg(String imagePath, final int imageType, final int position) {
        FileUploadingListener listener = new FileUploadingListener() {
            @Override
            public void onProgress(final int progress) {
                Observable.just(progress)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(@NonNull Integer integer) throws Exception {
//                                Logger.d(imageType + ":  progress = " + progress);
                                mView.showUploadImgProgress(imageType,position,progress);
                            }
                        });

            }
        };
        mApiwrapper.imageUpload("5",imagePath,listener)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.uploadActionImgSuccess(s,imageType,position);
                    }
                }));
    }

    @Override
    public void releaseActionRecent(ActionDto actionDto,String clubId) {
        mView.showLoading();
        mApiwrapper.releaseActionRecent(actionDto,clubId)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.releaseActionSuccess();
                    }
                }));
    }
}
