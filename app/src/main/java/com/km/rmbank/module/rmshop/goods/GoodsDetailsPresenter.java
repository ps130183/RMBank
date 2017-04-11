package com.km.rmbank.module.rmshop.goods;

import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kamangkeji on 17/4/6.
 */

public class GoodsDetailsPresenter extends PresenterWrapper<GoodsDetailsContract.View> implements GoodsDetailsContract.Presenter {


    public GoodsDetailsPresenter(GoodsDetailsContract.View mView) {
        super(mView);
    }

    @Override
    public void getGoodsDetails(String productNo) {
        mView.showLoading();
        Flowable<GoodsDetailsDto> goodsDetailsDtoFlowable = mApiwrapper.getGoodsDetails(productNo).subscribeOn(Schedulers.io());
        Flowable<ReceiverAddressDto> receiverAddressDtoFlowable = mApiwrapper.getDefaultReceiverAddress().subscribeOn(Schedulers.io());
        Flowable.zip(goodsDetailsDtoFlowable, receiverAddressDtoFlowable, new BiFunction<GoodsDetailsDto, ReceiverAddressDto, GoodsDetailsDto>() {
            @Override
            public GoodsDetailsDto apply(@NonNull GoodsDetailsDto goodsDetailsDto, @NonNull ReceiverAddressDto receiverAddressDto) throws Exception {
                goodsDetailsDto.setReceiverAddressDto(receiverAddressDto);
                return goodsDetailsDto;
            }
        }).subscribe(newSubscriber(new Consumer<GoodsDetailsDto>() {
                    @Override
                    public void accept(@NonNull GoodsDetailsDto goodsDetailsDto) throws Exception {
                        mView.showGoodsDetails(goodsDetailsDto);
                    }
                }));
    }

    @Override
    public void followGoods(String productNo) {
        mApiwrapper.followGodos(productNo)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.followGoodsSuccess();
                    }
                }));
    }

    @Override
    public void addShoppingCart(String productNo, String count) {
        mApiwrapper.addShoppingCart(productNo,count)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.addShoppingCartSuccess();
                    }
                }));
    }

    @Override
    public void onCreateView() {

    }
}
