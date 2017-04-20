package com.km.rmbank.module.actionarea;

import com.km.rmbank.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/4/20.
 */

public class InformationDetailPresenter extends PresenterWrapper<InformationDetailContract.View> implements InformationDetailContract.Presenter {

    public InformationDetailPresenter(InformationDetailContract.View mView) {
        super(mView);
    }

    @Override
    public void getInformationDetail(String id) {
        mView.showLoading();
        mApiwrapper.getInformationDetail(id)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.showInfomationDetail(s);
                    }
                }));
    }

    @Override
    public void onCreateView() {
        mView.initWebView();
    }
}
