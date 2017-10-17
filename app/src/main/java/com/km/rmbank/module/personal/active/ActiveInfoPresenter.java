package com.km.rmbank.module.personal.active;

import com.km.rmbank.dto.ActiveValueDetailDto;
import com.km.rmbank.dto.ActiveValueDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/9/11.
 */

public class ActiveInfoPresenter extends PresenterWrapper<ActiveInfoContract.View> implements ActiveInfoContract.Presenter {

    public ActiveInfoPresenter(ActiveInfoContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getActiveValue() {
        mView.showLoading();
        mApiwrapper.getActiveValue()
                .subscribe(newSubscriber(new Consumer<ActiveValueDto>() {
                    @Override
                    public void accept(@NonNull ActiveValueDto activeValueDto) throws Exception {
                        mView.showActiveValue(activeValueDto);
                    }
                }));
    }

    @Override
    public void getActiveValueDetail(final int pageNo) {
        mApiwrapper.getActiveValueDetail(pageNo)
                .subscribe(newSubscriber(new Consumer<List<ActiveValueDetailDto>>() {
                    @Override
                    public void accept(@NonNull List<ActiveValueDetailDto> activeValueDetailDtos) throws Exception {
                        mView.showActiveValueDetail(activeValueDetailDtos,pageNo);
                    }
                }));
    }
}
