package com.km.rmbank.module.register;

import com.km.rmbank.dto.DefaultDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/3/21.
 */

public class RegisterPresenter extends PresenterWrapper<RegisterContract.View> implements RegisterContract.Presenter {


    public RegisterPresenter(RegisterContract.View mView) {
        super(mView);
    }

    @Override
    public void getCode(String mobilePhone) {
        mView.showLoading();
        mApiwrapper.getPhoneCode(mobilePhone)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String defaultDto) throws Exception {
                        mView.getCodeSuccess();
                    }
                }));
    }

    @Override
    public void onCreateView() {

    }
}
