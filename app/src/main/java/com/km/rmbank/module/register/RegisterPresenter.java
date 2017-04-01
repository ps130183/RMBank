package com.km.rmbank.module.register;

import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.DefaultDto;

import rx.functions.Action1;

/**
 * Created by kamangkeji on 17/3/21.
 */

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View view;
    private BaseActivity mActivity;

    private ApiWrapper mApiwrapper;

    public RegisterPresenter(RegisterContract.View view, BaseActivity mActivity) {
        this.view = view;
        this.mActivity = mActivity;
        mApiwrapper = ApiWrapper.getInstance();
    }

    @Override
    public void getCode(String mobilePhone) {
        view.showLoading();
        mApiwrapper.getPhoneCode(mobilePhone)
                .subscribe(mActivity.newSubscriber(new Action1<DefaultDto>() {
                    @Override
                    public void call(DefaultDto defaultDto) {
                        view.getCodeSuccess();
                    }
                }));
    }

    @Override
    public void onCreateView() {

    }
}
