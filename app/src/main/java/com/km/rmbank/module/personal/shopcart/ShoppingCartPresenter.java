package com.km.rmbank.module.personal.shopcart;

import android.os.Handler;

import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.dto.ShoppingCartDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/3/20.
 */

public class ShoppingCartPresenter extends PresenterWrapper<ShoppingCartContact.View> implements ShoppingCartContact.Presenter {


    public ShoppingCartPresenter(ShoppingCartContact.View mView) {
        super(mView);
    }

    @Override
    public void loadShoppingCartDatas() {
//        Random random = new Random();
        mView.showLoading();

        mApiwrapper.getShoppingCartList()
                .subscribe(newSubscriber(new Consumer<List<ShoppingCartDto>>() {
                    @Override
                    public void accept(@NonNull List<ShoppingCartDto> shoppingCartDtos) throws Exception {
                        mView.showShoppingCartDatas(shoppingCartDtos);
                    }
                }));
    }

    @Override
    public void updateGoodsCount(final GoodsDetailsDto productNo, final String optionType) {
        mApiwrapper.updateCountOnShopCartForGoods(productNo.getProductNo(),optionType)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.updateGoodsCountSuccess(productNo,optionType);
                    }
                }));
    }

    @Override
    public void createOrder(String productNos) {
        mView.showLoading();
        mApiwrapper.createOrder(productNos)
                .subscribe(newSubscriber(new Consumer<List<ShoppingCartDto>>() {
                    @Override
                    public void accept(@NonNull List<ShoppingCartDto> shoppingCartDtos) throws Exception {
                        mView.createOrderSuccess(shoppingCartDtos);
                    }
                }));
    }

    @Override
    public void deleteGoods(GoodsDetailsDto goodsDetailsDto, final int positionOnParent, final int positionOnSub) {
        mApiwrapper.deleteShoppingCartGoods(goodsDetailsDto)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.deleteSuccess(positionOnParent,positionOnSub);
                    }
                }));
    }

    @Override
    public void onCreateView() {

    }
}
