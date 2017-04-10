package com.km.rmbank.module.personal.vip;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.MemberTypeDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/4/6.
 */

public interface SelectMemberTypeContract {
    interface View extends BaseView{
        void showMemberTypeInfo(List<MemberTypeDto> memberTypeDtos);
    }
    interface Presenter extends BasePresenter{
        void getMemberTypeInfo();
    }
}
