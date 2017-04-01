package com.km.rmbank.module.personal.receiveraddress;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ReceiverAddressDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/3/30.
 */

public interface ReceiverAddressContract {
    interface View extends BaseView{
        void initRecyclerView();
        void showReceiverAddressList(List<ReceiverAddressDto> receiverAddressDtos);
    }
    interface Presenter extends BasePresenter{
        void loadReceiverAddressData();
    }
}
