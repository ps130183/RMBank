package com.km.rmbank.module.personal.active;

import com.km.rmbank.dto.ActiveGoodsDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/9/12.
 */

public class ConvertActiveGoodsListPresenter extends PresenterWrapper<ConvertActiveGoodsListContract.View> implements ConvertActiveGoodsListContract.Presenter {

    public ConvertActiveGoodsListPresenter(ConvertActiveGoodsListContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getConvertActiveGoodsList(final int pageNo) {
        mApiwrapper.getConvertActiveGoodsList(pageNo)
                .subscribe(newSubscriber(new Consumer<List<ActiveGoodsDto>>() {
                    @Override
                    public void accept(@NonNull List<ActiveGoodsDto> activeGoodsDtos) throws Exception {
                        mView.showConvertActiveGoodsList(activeGoodsDtos,pageNo);
                    }
                }));
    }
}
