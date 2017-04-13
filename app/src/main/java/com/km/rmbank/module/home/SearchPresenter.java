package com.km.rmbank.module.home;

import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/4/13.
 */

public class SearchPresenter extends PresenterWrapper<SearchContract.View> implements SearchContract.Presenter {

    public SearchPresenter(SearchContract.View mView) {
        super(mView);
    }

    @Override
    public void getSearchGoods(final int pageNo, String name) {
        mView.showLoading();
        mApiwrapper.getGoodsListOfSearch(pageNo,name)
                .subscribe(newSubscriber(new Consumer<List<GoodsDto>>() {
                    @Override
                    public void accept(@NonNull List<GoodsDto> goodsDtos) throws Exception {
                        mView.showSearchResult(goodsDtos,pageNo);
                    }
                }));
    }

    @Override
    public void onCreateView() {
        mView.initRecyclerview();
    }
}
