package com.km.rmbank.module.personal.account.withdraw;

import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.dto.WithDrawAccountDto;

import rx.functions.Action1;

/**
 * Created by kamangkeji on 17/4/1.
 */

public class WithDrawAPresenter implements WithDrawAContract.Presenter {

    private WithDrawAContract.View view;
    private BaseActivity activity;

    private ApiWrapper apiWrapper;
    public WithDrawAPresenter(WithDrawAContract.View view, BaseActivity activity) {
        this.view = view;
        this.activity = activity;
        apiWrapper = ApiWrapper.getInstance();
    }

    @Override
    public void getUserBalance() {
        apiWrapper.getUserAccountBalance()
                .subscribe(activity.newSubscriber(new Action1<UserBalanceDto>() {
                    @Override
                    public void call(UserBalanceDto userBalanceDto) {
                        view.showBalance(userBalanceDto);
                    }
                }));
    }

    @Override
    public void submitWithdraw(WithDrawAccountDto withDrawAccountDto, String money) {
        view.showLoading();
        apiWrapper.submitWithDraw(withDrawAccountDto,money)
                .subscribe(activity.newSubscriber(new Action1() {
                    @Override
                    public void call(Object o) {
                        view.withdrawSuccess();
                    }
                }));
    }

    @Override
    public void onCreateView() {
        getUserBalance();
    }
}
