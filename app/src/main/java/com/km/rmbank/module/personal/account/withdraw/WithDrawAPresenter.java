package com.km.rmbank.module.personal.account.withdraw;

import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.dto.WithDrawAccountDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.Map;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/4/1.
 */

public class WithDrawAPresenter extends PresenterWrapper<WithDrawAContract.View> implements WithDrawAContract.Presenter {


    public WithDrawAPresenter(WithDrawAContract.View mView) {
        super(mView);
    }

    @Override
    public void getUserBalance() {
        mApiwrapper.getUserAccountBalance()
                .subscribe(newSubscriber(new Consumer<UserBalanceDto>() {
                    @Override
                    public void accept(@NonNull UserBalanceDto userBalanceDto) throws Exception {
                        mView.showBalance(userBalanceDto);
                    }
                }));
    }

    @Override
    public void submitWithdraw(WithDrawAccountDto withDrawAccountDto, String money) {
        mView.showLoading();
        mApiwrapper.submitWithDraw(withDrawAccountDto,money)
                .subscribe(newSubscriber(new Consumer() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        mView.withdrawSuccess();
                    }
                }));
    }


    @Override
    public void onCreateView() {
        getUserBalance();
    }
}
