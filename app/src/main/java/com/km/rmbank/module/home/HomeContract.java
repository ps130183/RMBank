package com.km.rmbank.module.home;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.HomeRecommendDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/4/12.
 */

public interface HomeContract {
    interface View extends BaseView{
        void getRecommendSuccess(List<HomeRecommendDto> homeRecommendDtos);
    }
    interface Presenter extends BasePresenter{
        void getRecommend();
    }
}
