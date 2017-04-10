package com.km.rmbank.module.personal.account;

import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.UserAccountDetailDto;
import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/4/1.
 */

public class UserAccountPresenter extends PresenterWrapper<UserAccountContract.View> implements UserAccountContract.Presenter {


    public UserAccountPresenter(UserAccountContract.View mView) {
        super(mView);
    }

    @Override
    public void loadBalance() {
        mApiwrapper.getUserAccountBalance()
                .subscribe(newSubscriber(new Consumer<UserBalanceDto>() {
                    @Override
                    public void accept(@NonNull UserBalanceDto userBalanceDto) throws Exception {
                        mView.showBalance(userBalanceDto);
                    }
                }));
    }

    @Override
    public void loadAccountDetail(final int pageNo) {
        mView.showLoading();
        mApiwrapper.getUserAccountDetail(pageNo)
                .subscribe(newSubscriber(new Consumer<List<UserAccountDetailDto>>() {
                    @Override
                    public void accept(@NonNull List<UserAccountDetailDto> userAccountDetailDtos) throws Exception {
                        mView.showAccountDetail(userAccountDetailDtos,pageNo);
                    }

                }));
    }

    @Override
    public void onCreateView() {
        loadBalance();
        mView.initAccountDetail();
        loadAccountDetail(1);
    }
}
