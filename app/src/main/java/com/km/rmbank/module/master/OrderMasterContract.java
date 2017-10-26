package com.km.rmbank.module.master;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.MasterBannerDto;
import com.km.rmbank.dto.MasterDto;

import java.util.List;

/**
 * Created by PengSong on 17/10/17.
 */

public interface OrderMasterContract {
    interface View extends BaseView{
        void showMastersInfo(List<MasterDto> masterDtos,int pageNo);
        void showMasterBannerList(List<MasterBannerDto> bannerDtos);
        void showMasterInfo(MasterDto masterDto,String id);
    }
    interface Presenter extends BasePresenter{
        void getMasterList(int pageNo);
        void getMasterBannerList();
        void getMasterInfo(String id);
    }
}
