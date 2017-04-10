package com.km.rmbank.module.personal.receiveraddress;

import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamangkeji on 17/3/30.
 */

public class ReceiverAddressPresenter extends PresenterWrapper<ReceiverAddressContract.View> implements ReceiverAddressContract.Presenter {


    public ReceiverAddressPresenter(ReceiverAddressContract.View mView) {
        super(mView);
    }

    @Override
    public void loadReceiverAddressData() {
        List<ReceiverAddressDto> receiverAddressDtos = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            receiverAddressDtos.add(new ReceiverAddressDto());
        }
        mView.showReceiverAddressList(receiverAddressDtos);
    }

    @Override
    public void onCreateView() {
        mView.initRecyclerView();
        loadReceiverAddressData();
    }
}
