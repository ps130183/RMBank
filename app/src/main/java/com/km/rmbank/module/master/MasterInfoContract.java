package com.km.rmbank.module.master;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.MasterWorkDto;
import com.km.rmbank.dto.PayOrderDto;

import java.util.List;

/**
 * Created by PengSong on 17/10/17.
 */

public interface MasterInfoContract {
    interface View extends BaseView{
        void showMasterWorkList(List<MasterWorkDto> masterWorkDtos,int pageNo);
        void createOrderSuccess(PayOrderDto payOrderDto);
    }
    interface Presenter extends BasePresenter{
        void getMasterWork(String id,int pageNo);
        void createMasterOrder(String macaId,String macaWorksId,String money);
    }
}
