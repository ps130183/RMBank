package com.km.rmbank.module.personal.setting;

import com.km.rmbank.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/6/5.
 */

public class SettingPresenter extends PresenterWrapper<SettingContract.View> implements SettingContract.Presenter {

    public SettingPresenter(SettingContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void updateAllowUserCard(final boolean isAllow) {
        mView.showLoading();
        String allowStatus = isAllow ? "0" : "1";
        mApiwrapper.updateAllowUserCard(allowStatus)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.showAllowUserCardResult(isAllow);
                    }
                }));
    }
}
