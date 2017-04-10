package com.km.rmbank.module.personal.account.withdraw;

import com.km.rmbank.dto.WithDrawAccountDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


/**
 * Created by kamangkeji on 17/4/1.
 */

public class WithDrawPresenter extends PresenterWrapper<WithDrawContract.View> implements WithDrawContract.Presenter {


    public WithDrawPresenter(WithDrawContract.View mView) {
        super(mView);
    }

    @Override
    public void createWithDrawAccount(WithDrawAccountDto withDrawAccountDto) {
        mApiwrapper.createWithDrawAccount(withDrawAccountDto)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.creatOrUpdateSuccess();
                    }
                }));
    }

    @Override
    public void updateWithDrawAccount(WithDrawAccountDto withDrawAccountDto) {
        mApiwrapper.updateWithDrawAccount(withDrawAccountDto)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.creatOrUpdateSuccess();
                    }
                }));
    }

    @Override
    public void getWithDrawList() {
        mView.showLoading();
        mApiwrapper.getWithDrawAccount()
                .subscribe(newSubscriber(new Consumer<List<WithDrawAccountDto>>() {
                    @Override
                    public void accept(@NonNull List<WithDrawAccountDto> withDrawAccountDtos) throws Exception {
                        mView.showWithDrawList(withDrawAccountDtos);
                    }

                }));
    }

    @Override
    public void deleteWithdrawAccount(final WithDrawAccountDto withDrawAccountDto) {
        mApiwrapper.deleteWithdrawAccount(withDrawAccountDto.getId())
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.deleteSuccess(withDrawAccountDto);
                    }
                }));
    }

    @Override
    public void onCreateView() {

    }
}
