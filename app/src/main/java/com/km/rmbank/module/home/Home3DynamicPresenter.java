package com.km.rmbank.module.home;

import com.km.rmbank.dto.InformationDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/7/20.
 */

public class Home3DynamicPresenter extends PresenterWrapper<Home3DynamicContract.View> implements Home3DynamicContract.Presenter {

    public Home3DynamicPresenter(Home3DynamicContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getDynamicInformationList(int pageNo) {
        mView.showLoading();
        mApiwrapper.getDynamicInformationList(pageNo)
                .subscribe(newSubscriber(new Consumer<List<InformationDto>>() {
                    @Override
                    public void accept(@NonNull List<InformationDto> informationDtos) throws Exception {
                        mView.showDynamicInformationList(informationDtos);
                    }
                }));
    }
}
