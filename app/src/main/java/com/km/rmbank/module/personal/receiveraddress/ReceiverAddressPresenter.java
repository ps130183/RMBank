package com.km.rmbank.module.personal.receiveraddress;

import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.ReceiverAddressDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamangkeji on 17/3/30.
 */

public class ReceiverAddressPresenter implements ReceiverAddressContract.Presenter {

    private ReceiverAddressContract.View view;
    private BaseActivity activity;

    private ApiWrapper mApiWrapper;

    public ReceiverAddressPresenter(ReceiverAddressContract.View view, BaseActivity activity) {
        this.view = view;
        this.activity = activity;
        this.mApiWrapper = ApiWrapper.getInstance();
    }

    @Override
    public void loadReceiverAddressData() {
        List<ReceiverAddressDto> receiverAddressDtos = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            receiverAddressDtos.add(new ReceiverAddressDto());
        }
        view.showReceiverAddressList(receiverAddressDtos);
    }

    @Override
    public void onCreateView() {
        view.initRecyclerView();
        loadReceiverAddressData();
    }
}
