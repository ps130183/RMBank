package com.km.rmbank.module.personal.account;

import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.UserAccountDetailDto;
import com.km.rmbank.dto.UserBalanceDto;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by kamangkeji on 17/4/1.
 */

public class UserAccountPresenter implements UserAccountContract.Presenter {

    private UserAccountContract.View view;
    private BaseActivity activity;

    private ApiWrapper apiWrapper;
    public UserAccountPresenter(UserAccountContract.View view, BaseActivity activity) {
        this.view = view;
        this.activity = activity;
        apiWrapper = ApiWrapper.getInstance();
    }

    @Override
    public void loadBalance() {
        apiWrapper.getUserAccountBalance()
                .subscribe(activity.newSubscriber(new Action1<UserBalanceDto>() {
                    @Override
                    public void call(UserBalanceDto userBalanceDto) {
                        view.showBalance(userBalanceDto);
                    }
                }));
    }

    @Override
    public void loadAccountDetail(final int pageNo) {
        view.showLoading();
        apiWrapper.getUserAccountDetail(pageNo)
                .subscribe(activity.newSubscriber(new Action1<List<UserAccountDetailDto>>() {
                    @Override
                    public void call(List<UserAccountDetailDto> userAccountDetailDtos) {
                        view.showAccountDetail(userAccountDetailDtos,pageNo);
                    }
                }));
    }

    @Override
    public void onCreateView() {
        loadBalance();
        view.initAccountDetail();
        loadAccountDetail(1);
    }
}
