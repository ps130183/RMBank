package com.km.rmbank.module.personal.shopcart.createorder;

import android.os.Handler;

import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.entity.ShoppingCartEntity;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by kamangkeji on 17/3/21.
 */

public class CreateOrderPresenter extends PresenterWrapper<CreateOrderContact.View> implements CreateOrderContact.Presenter {


    public CreateOrderPresenter(CreateOrderContact.View mView) {
        super(mView);
    }

    @Override
    public void loadOrderDatas() {
        Random random = new Random();
        final List<ShoppingCartEntity> shoppingCartEntities = new ArrayList<>();
        for (int i = 0; i < 2; i++){
            List<GoodsDto> goodsEntities = new ArrayList<>();
            for (int j = 0; j < random.nextInt(3)+1; j++){
                goodsEntities.add(new GoodsDto());
            }
            ShoppingCartEntity entity = new ShoppingCartEntity();
            entity.setGoodsEntities(goodsEntities);
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
    public void onCreateView() {
        loadOrderDatas();
    }
}
