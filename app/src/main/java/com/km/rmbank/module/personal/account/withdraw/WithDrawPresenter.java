package com.km.rmbank.module.personal.account.withdraw;

import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.WithDrawAccountDto;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by kamangkeji on 17/4/1.
 */

public class WithDrawPresenter implements WithDrawContract.Presenter {

    private WithDrawContract.View view;
    private BaseActivity activity;

    private ApiWrapper apiWrapper;
    public WithDrawPresenter(WithDrawContract.View view, BaseActivity activity) {
        this.view = view;
        this.activity = activity;
        apiWrapper = ApiWrapper.getInstance();
    }

    @Override
    public void createWithDrawAccount(WithDrawAccountDto withDrawAccountDto) {
        apiWrapper.createWithDrawAccount(withDrawAccountDto)
                .subscribe(activity.newSubscriber(new Action1() {
                    @Override
                    public void call(Object o) {
                        view.creatOrUpdateSuccess();
                    }
                }));
    }

    @Override
    public void updateWithDrawAccount(WithDrawAccountDto withDrawAccountDto) {
        apiWrapper.updateWithDrawAccount(withDrawAccountDto)
                .subscribe(activity.newSubscriber(new Action1() {
                    @Override
                    public void call(Object o) {
                        view.creatOrUpdateSuccess();
                    }
                }));
    }

    @Override
    public void getWithDrawList() {
        view.showLoading();
        apiWrapper.getWithDrawAccount()
                .subscribe(activity.newSubscriber(new Action1<List<WithDrawAccountDto>>() {
                    @Override
                    public void call(List<WithDrawAccountDto> withDrawAccountDtos) {
                        view.showWithDrawList(withDrawAccountDtos);
                    }
                }));
    }

    @Override
    public void onCreateView() {

    }
}
