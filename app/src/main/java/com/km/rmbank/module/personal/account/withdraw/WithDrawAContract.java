package com.km.rmbank.module.personal.account.withdraw;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.dto.WithDrawAccountDto;

/**
 * Created by kamangkeji on 17/4/1.
 */

public interface WithDrawAContract {
    interface View extends BaseView{
        void showBalance(UserBalanceDto userBalanceDto);
        void withdrawSuccess();
    }
    interface Presenter extends BasePresenter{
        void getUserBalance();
        void submitWithdraw(WithDrawAccountDto withDrawAccountDto,String money);
    }
}
