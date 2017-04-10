package com.km.rmbank.module.personal.receiveraddress;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ReceiverAddressDto;

/**
 * Created by kamangkeji on 17/4/10.
 */

public interface CreateReceiverAddressContract {
    interface View extends BaseView{
        void createReceiverAddressSuccess();
    }
    interface Presenter extends BasePresenter{
        void createReceiverAddress(ReceiverAddressDto receiverAddressDto);
    }
}
