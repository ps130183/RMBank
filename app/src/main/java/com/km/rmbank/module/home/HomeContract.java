package com.km.rmbank.module.home;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.BannerDto;
import com.km.rmbank.dto.HomeGoodsTypeDto;
import com.km.rmbank.dto.HomeNewRecommendDto;
import com.km.rmbank.dto.HomeRecommendDto;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.entity.HomeDataEntity;

import java.util.List;

/**
 * Created by kamangkeji on 17/4/12.
 */

public interface HomeContract {
    interface View extends BaseView{
        void getRecommendSuccess(List<HomeRecommendDto> homeRecommendDtos,int pageNo);
        void showHomeBanner(List<BannerDto> bannerDtos);
        void showHomeGoodsType(List<HomeGoodsTypeDto> homeGoodsTypeDtos);
        void ShowHomeNewRecommend(List<HomeNewRecommendDto> homeNewRecommendDtos);
    }
    interface Presenter extends BasePresenter{
        void getRecommend(int pageNo);
        void getHomeBanner();
        void getHomeGoodsType();
        void getHomeNewRecommend(int pageNo);
    }
}
