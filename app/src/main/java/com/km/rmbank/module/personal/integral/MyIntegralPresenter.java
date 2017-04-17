package com.km.rmbank.module.personal.integral;

import com.km.rmbank.dto.IntegralDetailsDto;
import com.km.rmbank.dto.IntegralDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/4/15.
 */

public class MyIntegralPresenter extends PresenterWrapper<MyIntegralContract.View> implements MyIntegralContract.Presenter {

    public MyIntegralPresenter(MyIntegralContract.View mView) {
        super(mView);
    }

    @Override
    public void getUserIntegralInfo() {
        mApiwrapper.getIntegralInfo()
                .subscribe(newSubscriber(new Consumer<IntegralDto>() {
                    @Override
                    public void accept(@NonNull IntegralDto integralDto) throws Exception {
                        mView.showUserIntegralInfo(integralDto);
                    }
                }));
    }

    @Override
    public void getIntegralDetails(final int pageNo) {
        mApiwrapper.getIntegralDetailsList(pageNo)
                .subscribe(newSubscriber(new Consumer<List<IntegralDetailsDto>>() {
                    @Override
                    public void accept(@NonNull List<IntegralDetailsDto> integralDetailsDtos) throws Exception {
                        mView.showIntegralDetails(integralDetailsDtos,pageNo);
                    }
                }));
    }

    @Override
    public void onCreateView() {

    }
}
