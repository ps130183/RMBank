package com.km.rmbank.module.rmshop;

import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.dto.GoodsTypeDto;
import com.km.rmbank.dto.HomeGoodsTypeDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;
import com.orhanobut.logger.Logger;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/4/12.
 */

public class RmShopPresenter extends PresenterWrapper<RmShopContract.View> implements RmShopContract.Presenter {

    public RmShopPresenter(RmShopContract.View mView) {
        super(mView);
    }

    @Override
    public void getGodosTypes() {
//        mApiwrapper.getGoodsTypes()
//                .subscribe(newSubscriber(new Consumer<List<GoodsTypeDto>>() {
//                    @Override
//                    public void accept(@NonNull List<GoodsTypeDto> goodsTypeDtos) throws Exception {
//                        mView.getGoodsTypeSuccess(goodsTypeDtos);
//                    }
//                }));
        mApiwrapper.getGoodsType()
                .subscribe(newSubscriber(new Consumer<List<HomeGoodsTypeDto>>() {
                    @Override
                    public void accept(@NonNull List<HomeGoodsTypeDto> homeGoodsTypeDtos) throws Exception {
                        mView.showGoodsType(homeGoodsTypeDtos);
                    }
                }));
    }

    @Override
    public void getGoodsList(final int pageNo, String isInIndextActivity, int orderBy, String roleId) {
        mView.showLoading();
        mApiwrapper.getGoodsListOfShoppingNew(pageNo,isInIndextActivity,orderBy,roleId)
                .subscribe(newSubscriber(new Consumer<List<GoodsDto>>() {
                    @Override
                    public void accept(@NonNull List<GoodsDto> goodsDtos) throws Exception {
                        mView.showGoodsList(pageNo,goodsDtos);
                    }
                }));
    }

    @Override
    public void onCreateView() {
        getGodosTypes();
    }

}
