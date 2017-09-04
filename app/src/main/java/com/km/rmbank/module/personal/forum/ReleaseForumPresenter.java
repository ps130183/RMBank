package com.km.rmbank.module.personal.forum;

import com.km.rmbank.dto.ForumDto;
import com.km.rmbank.utils.fileupload.FileUploadingListener;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/8/14.
 */

public class ReleaseForumPresenter extends PresenterWrapper<ReleaseForumContract.View> implements ReleaseForumContract.Presenter {

    public ReleaseForumPresenter(ReleaseForumContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void uploadImage(String imagePath, final int position) {
        mApiwrapper.imageUpload("6",imagePath)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.showImage(s,position);
                    }
                }));
    }

    @Override
    public void releaseForum(ForumDto forumDto) {
        mView.showLoading();
        mApiwrapper.releaseForum(forumDto)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.releaseForumSuccess();
                    }
                }));
    }
}
