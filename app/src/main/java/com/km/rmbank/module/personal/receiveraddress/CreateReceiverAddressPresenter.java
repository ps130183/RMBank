package com.km.rmbank.module.personal.receiveraddress;

import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/4/10.
 */

public class CreateReceiverAddressPresenter extends PresenterWrapper<CreateReceiverAddressContract.View> implements CreateReceiverAddressContract.Presenter {

    public CreateReceiverAddressPresenter(CreateReceiverAddressContract.View mView) {
        super(mView);
    }

    @Override
    public void createReceiverAddress(ReceiverAddressDto receiverAddressDto) {
        mView.showLoading();
        mApiwrapper.newReceiverAddress(receiverAddressDto)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.createReceiverAddressSuccess(s);
                    }
                }));
    }

    @Override
    public void updateReceiverAddress(final ReceiverAddressDto receiverAddressDto) {
        mView.showLoading();
        mApiwrapper.updateReceiverAddress(receiverAddressDto)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.createReceiverAddressSuccess(receiverAddressDto.getId());
                    }
                }));
    }

    @Override
    public void onCreateView() {

    }
}
