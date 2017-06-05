package com.km.rmbank.module.nearbyvip;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.NearbyVipDto;
import com.km.rmbank.dto.UserCardDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/6/5.
 */

public interface NearbyVipContract {
    interface View extends BaseView{
        void initRecyclerView();
        void getNearbyVipSuccess(List<NearbyVipDto> nearbyVipDtos, int pageNo);
        void showUserCart(UserCardDto userCardDto,String phone);
    }

    interface Presenter extends BasePresenter{
        void getNearbyVip(int pageNo);
        void getUserCardInfo(String url);
    }
}
