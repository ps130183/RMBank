package com.km.rmbank.module.personal.account;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.UserAccountDetailDto;
import com.km.rmbank.dto.UserBalanceDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/3/29.
 */

public interface UserAccountContract {
    interface View extends BaseView{
        void initAccountDetail();
        void showAccountDetail(List<UserAccountDetailDto> userAccountDetailDtos,int curPage);
        void showBalance(UserBalanceDto userBalanceDto);
    }
    interface Presenter extends BasePresenter{
        void loadBalance();
        void loadAccountDetail(int pageNo);
    }
}
