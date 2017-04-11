package com.km.rmbank.module.personal.shopcart.createorder;

import android.os.Handler;

import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.dto.ShoppingCartDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/3/21.
 */

public class CreateOrderPresenter extends PresenterWrapper<CreateOrderContact.View> implements CreateOrderContact.Presenter {


    public CreateOrderPresenter(CreateOrderContact.View mView) {
        super(mView);
    }

    @Override
    public void getDefaultReceiverAddress() {
        mApiwrapper.getDefaultReceiverAddress()
                .subscribe(newSubscriber(new Consumer<ReceiverAddressDto>() {
                    @Override
                    public void accept(@NonNull ReceiverAddressDto receiverAddressDto) throws Exception {
                        mView.showDefaultReceiverAddress(receiverAddressDto);
                    }
                }));
    }

    @Override
    public void loadOrderDatas() {
        Random random = new Random();
        final List<ShoppingCartDto> shoppingCartEntities = new ArrayList<>();
        for (int i = 0; i < 2; i++){
            List<GoodsDetailsDto> goodsEntities = new ArrayList<>();
            for (int j = 0; j < random.nextInt(3)+1; j++){
                goodsEntities.add(new GoodsDetailsDto());
            }
            ShoppingCartDto entity = new ShoppingCartDto();
            entity.setProductList(goodsEntities);
            shoppingCartEntities.add(entity);
        }
        mView.showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mView.hideLoading();
                mView.showOrderDatas(shoppingCartEntities);
            }
        },3000);
    }

    @Override
    public void submitOrder(String productNos, String productCounts, String receiveAddressId, String freight,String integral) {
        mView.showLoading();
        mApiwrapper.submitOrder(productNos,productCounts,receiveAddressId,freight,integral)
                .subscribe(newSubscriber(new Consumer<PayOrderDto>() {
                    @Override
                    public void accept(@NonNull PayOrderDto payOrderDto) throws Exception {
                        mView.submitOrderSuccess(payOrderDto);
                    }
                }));
    }

    @Override
    public void onCreateView() {
        getDefaultReceiverAddress();
//        loadOrderDatas();
    }
}
