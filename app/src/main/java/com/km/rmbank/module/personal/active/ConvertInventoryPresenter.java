package com.km.rmbank.module.personal.active;

import com.km.rmbank.dto.ActiveGoodsOrderListDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/9/13.
 */

public class ConvertInventoryPresenter extends PresenterWrapper<ConvertInventoryContract.View> implements ConvertInventoryContract.Presenter {

    public ConvertInventoryPresenter(ConvertInventoryContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getActiveGoodsOrderList(final int pageNo) {
       mApiwrapper.getActiveGoodsOrdetList(pageNo)
               .subscribe(newSubscriber(new Consumer<List<ActiveGoodsOrderListDto>>() {
                   @Override
                   public void accept(@NonNull List<ActiveGoodsOrderListDto> activeGoodsOrderListDtos) throws Exception {
                       mView.showActiveGoodsOrderList(activeGoodsOrderListDtos,pageNo);
                   }
               }));
    }
}
