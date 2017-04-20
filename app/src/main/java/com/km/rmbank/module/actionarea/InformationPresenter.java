package com.km.rmbank.module.actionarea;

import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.InformationDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/4/12.
 */

public class InformationPresenter extends PresenterWrapper<InformationContract.View> implements InformationContract.Presenter {

    public InformationPresenter(InformationContract.View mView) {
        super(mView);
    }

    @Override
    public void getActionList(final int pageNo) {
//        mView.showLoading();
        mApiwrapper.getInformationList(pageNo)
                .subscribe(newSubscriber(new Consumer<List<InformationDto>>() {
                    @Override
                    public void accept(@NonNull List<InformationDto> informationDtos) throws Exception {
                        mView.getActionListSuccess(informationDtos,pageNo);
                    }
                }));
    }

    @Override
    public void onCreateView() {
        mView.initAction();
    }
}
