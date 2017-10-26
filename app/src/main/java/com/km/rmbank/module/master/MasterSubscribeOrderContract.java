package com.km.rmbank.module.master;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.MasterDto;
import com.km.rmbank.dto.MasterOrderDto;

import java.util.List;

/**
 * Created by PengSong on 17/10/26.
 */

public interface MasterSubscribeOrderContract {
    interface View extends BaseView{
        void showMasterSubscribeList(List<MasterOrderDto> masterOrderDtos,int pageNo);
        void showMasterInfo(MasterDto masterDto, String id);
    }
    interface Presenter extends BasePresenter{
        void getMasterSubscribeList(int pageNo);
        void getMasterInfo(String id);
    }
}
