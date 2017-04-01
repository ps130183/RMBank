package com.km.rmbank.module.personal.shopcart;

import android.os.Handler;

import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.entity.ShoppingCartEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by kamangkeji on 17/3/20.
 */

public class ShoppingCartPresenter implements ShoppingCartContact.Presenter {

    private ShoppingCartContact.View view;
    private BaseActivity mActivity;

    public ShoppingCartPresenter(ShoppingCartContact.View view, BaseActivity mActivity) {
        this.view = view;
        this.mActivity = mActivity;
    }

    @Override
    public void loadShoppingCartDatas() {
        Random random = new Random();
        final List<ShoppingCartEntity> shoppingCartEntities = new ArrayList<>();
        for (int i = 0; i < random.nextInt(3)+1; i++){
            List<GoodsDto> goodsEntities = new ArrayList<>();
            for (int j = 0; j < random.nextInt(5)+1; j++){
                goodsEntities.add(new GoodsDto());
            }
            ShoppingCartEntity entity = new ShoppingCartEntity();
            entity.setGoodsEntities(goodsEntities);
            shoppingCartEntities.add(entity);
        }
        view.showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.hideLoading();
                view.showShoppingCartDatas(shoppingCartEntities);
            }
        },3000);
    }

    @Override
    public void onCreateView() {
        loadShoppingCartDatas();
    }
}
