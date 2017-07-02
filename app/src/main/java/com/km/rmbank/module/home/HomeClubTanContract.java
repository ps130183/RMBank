package com.km.rmbank.module.home;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ClubDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/7/1.
 */

public interface HomeClubTanContract {
    interface View extends BaseView{
        void showClubInfos(List<ClubDto> clubDtos);
    }

    interface Presenter extends BasePresenter{
        void getClubInfos(String type);
    }

}
