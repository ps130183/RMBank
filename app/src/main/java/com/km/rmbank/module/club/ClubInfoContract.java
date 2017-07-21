package com.km.rmbank.module.club;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ClubDto;

/**
 * Created by kamangkeji on 17/7/12.
 */

public interface ClubInfoContract {
    interface View extends BaseView{
        void showClubInfo(ClubDto clubDto);
        void followClubSuccess();
    }
    interface Presenter extends BasePresenter{
        void getMyClubInfo(String clubId);
        void followClub(String clubId);
    }
}
