package com.km.rmbank.module.personal.active;

import com.km.rmbank.dto.ActiveGoodsDto;
import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kamangkeji on 17/9/12.
 */

public class ActiveGoodsDetailPresenter extends PresenterWrapper<ActiveGoodsDetailContract.View> implements ActiveGoodsDetailContract.Presenter {

    public ActiveGoodsDetailPresenter(ActiveGoodsDetailContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getActiveGoodsDetailInfo(String productNo) {
        mView.showLoading();
        Flowable<ActiveGoodsDto> activeGoodsDtoFlowable = mApiwrapper.getConvertActiveGoodsDetail(productNo).subscribeOn(Schedulers.io());
        Flowable<ReceiverAddressDto> receiverAddressDtoFlowable = mApiwrapper.getDefaultReceiverAddress().subscribeOn(Schedulers.io());
        Flowable.zip(activeGoodsDtoFlowable, receiverAddressDtoFlowable, new BiFunction<ActiveGoodsDto, ReceiverAddressDto, ActiveGoodsDto>() {
            @Override
            public ActiveGoodsDto apply(@NonNull ActiveGoodsDto goodsDetailsDto, @NonNull ReceiverAddressDto receiverAddressDto) throws Exception {
                goodsDetailsDto.setReceiverAddressDto(receiverAddressDto);
                return goodsDetailsDto;
            }
        }).subscribe(newSubscriber(new Consumer<ActiveGoodsDto>() {
            @Override
            public void accept(@NonNull ActiveGoodsDto goodsDetailsDto) throws Exception {
                mView.showActiveGoodsDetailInfo(goodsDetailsDto);
            }
        }));
    }

    @Override
    public void convertActiveGoods(String productNo, String productCount, String receiveAddressId) {
        mView.showLoading();
        mApiwrapper.convertActiveGoods(productNo,productCount,receiveAddressId)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.convertSuccess();
                    }
                }));
    }
}
