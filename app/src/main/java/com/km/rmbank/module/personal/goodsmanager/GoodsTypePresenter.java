package com.km.rmbank.module.personal.goodsmanager;

import com.km.rmbank.R;
import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.dto.GoodsTypeDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/4/6.
 */

public class GoodsTypePresenter extends PresenterWrapper<GoodsTypeContract.View> implements GoodsTypeContract.Presenter {

    private int[] backgroundRes = {R.drawable.shape_new_goods_select_goods_type_1,
            R.drawable.shape_new_goods_select_goods_type_2,
            R.drawable.shape_new_goods_select_goods_type_3,
            R.drawable.shape_new_goods_select_goods_type_4,
            R.drawable.shape_new_goods_select_goods_type_5,
            R.drawable.shape_new_goods_select_goods_type_6,
            R.drawable.shape_new_goods_select_goods_type_7};

    private Random mRandom = new Random();


    public GoodsTypePresenter(GoodsTypeContract.View mView) {
        super(mView);
    }

    @Override
    public void getGoodsType() {
//        List<GoodsTypeDto> goodsTypeDtos = new ArrayList<>();
//        goodsTypeDtos.add(new GoodsTypeDto("武器"));
//        goodsTypeDtos.add(new GoodsTypeDto("渔业"));
//        goodsTypeDtos.add(new GoodsTypeDto("海产品"));
//        goodsTypeDtos.add(new GoodsTypeDto("煤炭"));
//        goodsTypeDtos.add(new GoodsTypeDto("互联网"));
//        goodsTypeDtos.add(new GoodsTypeDto("林木"));
        mApiwrapper.getGoodsTypes()
                .doOnNext(new Consumer<List<GoodsTypeDto>>() {
                    @Override
                    public void accept(@NonNull List<GoodsTypeDto> goodsTypeDtos) throws Exception {
                        for (GoodsTypeDto goodsTypeDto : goodsTypeDtos){
                            goodsTypeDto.setBackgroundRes(backgroundRes[mRandom.nextInt(backgroundRes.length)]);
                        }
                    }
                })
                .subscribe(newSubscriber(new Consumer<List<GoodsTypeDto>>() {
                    @Override
                    public void accept(@NonNull List<GoodsTypeDto> goodsTypeDtos) throws Exception {
                        mView.showGoodsType(goodsTypeDtos);
                    }
                }));

    }

    @Override
    public void onCreateView() {
        mView.initGoodsTypeView();
        getGoodsType();
    }
}
