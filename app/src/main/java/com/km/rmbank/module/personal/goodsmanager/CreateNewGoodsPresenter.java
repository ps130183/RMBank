package com.km.rmbank.module.personal.goodsmanager;

import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.dto.GoodsTypeDto;
import com.km.rmbank.dto.HomeGoodsTypeDto;
import com.km.rmbank.utils.fileupload.FileUploadingListener;
import com.km.rmbank.utils.retrofit.PresenterWrapper;
import com.orhanobut.logger.Logger;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
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
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.createNewGoodsSuccess();
                    }
                }));
    }

    @Override
    public void getGoodsInfo(String productNo) {
        mView.showLoading();
        Flowable<GoodsDetailsDto> goodsInfo = mApiwrapper.getGoodsInfo(productNo).subscribeOn(Schedulers.io());
        goodsInfo.subscribe(newSubscriber(new Consumer<GoodsDetailsDto>() {
            @Override
            public void accept(@NonNull GoodsDetailsDto goodsDetailsDto) throws Exception {
                mView.showGoodsInfo(goodsDetailsDto);
            }
        }));
//        final Flowable<List<HomeGoodsTypeDto>> goodsType = mApiwrapper.getGoodsTypeForCreateGoods().subscribeOn(Schedulers.io());
//
//        Flowable.zip(goodsInfo, goodsType, new BiFunction<GoodsDetailsDto, List<HomeGoodsTypeDto>, GoodsDetailsDto>() {
//            @Override
//            public GoodsDetailsDto apply(@NonNull GoodsDetailsDto goodsDetailsDto, @NonNull List<HomeGoodsTypeDto> goodsTypeDtos) throws Exception {
//                String typeId = goodsDetailsDto.getIsInIndexActivity();
//                for (HomeGoodsTypeDto typeDto : goodsTypeDtos){
//                    if (typeId.equals(typeDto.getId())){
//                        goodsDetailsDto.setGoodsTypeDto(typeDto);
//                        break;
//                    }
//                }
//                return goodsDetailsDto;
//            }
//        }).observeOn(AndroidSchedulers.mainThread()).subscribe(newSubscriber(new Consumer<GoodsDetailsDto>() {
//            @Override
//            public void accept(@NonNull GoodsDetailsDto goodsDetailsDto) throws Exception {
//                mView.showGoodsInfo(goodsDetailsDto);
//            }
//        }));
    }

    @Override
    public void updateGoodsInfo(GoodsDetailsDto goodsDetailsDto) {
        mView.showLoading();
        mApiwrapper.updateGoods(goodsDetailsDto)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.createNewGoodsSuccess();
                    }
                }));
    }

    @Override
    public void onCreateView() {

    }
}
