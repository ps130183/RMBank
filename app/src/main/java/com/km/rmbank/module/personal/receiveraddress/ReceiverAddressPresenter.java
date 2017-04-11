package com.km.rmbank.module.personal.receiveraddress;

import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/3/30.
 */

public class ReceiverAddressPresenter extends PresenterWrapper<ReceiverAddressContract.View> implements ReceiverAddressContract.Presenter {


    public ReceiverAddressPresenter(ReceiverAddressContract.View mView) {
        super(mView);
    }

    @Override
    public void loadReceiverAddressData() {
        mView.showLoading();
        mApiwrapper.getReceiverAddressList()
                .subscribe(newSubscriber(new Consumer<List<ReceiverAddressDto>>() {
                    @Override
                    public void accept(@NonNull List<ReceiverAddressDto> receiverAddressDtos) throws Exception {
                        mView.showReceiverAddressList(receiverAddressDtos);
                    }
                }));
    }

    @Override
    public void setDefaultReceiverAddress(ReceiverAddressDto receiverAddress) {
        mApiwrapper.setDefaultReceiverAddress(receiverAddress.getId())
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.setDefaultReceiverAddressSuccess();
                    }
                }));
    }

    @Override
    public void deleteReceiverAddress(final ReceiverAddressDto receiverAddressDto) {
        mView.showLoading();
        mApiwrapper.deleteReceiverAddress(receiverAddressDto.getId())
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.deleteReceiverSuccess(receiverAddressDto);
                    }
                }));
    }

    @Override
    public void onCreateView() {
        mView.initRecyclerView();
    }
}
