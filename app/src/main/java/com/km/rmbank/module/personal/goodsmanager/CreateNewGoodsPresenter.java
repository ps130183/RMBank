package com.km.rmbank.module.personal.goodsmanager;

import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.utils.fileupload.FileUploadingListener;
import com.km.rmbank.utils.retrofit.PresenterWrapper;
import com.orhanobut.logger.Logger;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * Created by kamangkeji on 17/4/7.
 */

public class CreateNewGoodsPresenter extends PresenterWrapper<CreateNewGoodsContract.View> implements CreateNewGoodsContract.Presenter {

    public CreateNewGoodsPresenter(CreateNewGoodsContract.View mView) {
        super(mView);
    }

    @Override
    public void uploadImage(String photoList, final int photoType, final int position) {
        FileUploadingListener listener = new FileUploadingListener() {
            @Override
            public void onProgress(final int progress) {
                Observable.just(progress)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(@NonNull Integer integer) throws Exception {
                                mView.showImageUploadingProgress(photoType,progress,position);
                            }
                        });
            }
        };
            mApiwrapper.imageUpload("2", photoList, listener)
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(@NonNull String s) throws Exception {
                            mView.showImageUploadResult(photoType,s);
                        }
                    });
    }

    @Override
    public void createNewGoods(GoodsDetailsDto goodsDetailsDto) {
        mView.showLoading();
        mApiwrapper.createNewGoods(goodsDetailsDto)
                .subscribe(newSubscriber(new Consumer() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        mView.createNewGoodsSuccess();
                    }

                }));
    }

    @Override
    public void onCreateView() {

    }
}
