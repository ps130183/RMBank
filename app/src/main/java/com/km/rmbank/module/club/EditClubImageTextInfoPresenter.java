package com.km.rmbank.module.club;

import com.km.rmbank.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/8/30.
 */

public class EditClubImageTextInfoPresenter extends PresenterWrapper<EditClubImageTextInfoContract.View> implements EditClubImageTextInfoContract.Presenter {

    public EditClubImageTextInfoPresenter(EditClubImageTextInfoContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void uploadImage(String imagePath, final int position) {
        mApiwrapper.imageUpload("5",imagePath)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.showImage(s,position);
                    }
                }));
    }
}
