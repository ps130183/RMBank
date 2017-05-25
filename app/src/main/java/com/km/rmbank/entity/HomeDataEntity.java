package com.km.rmbank.entity;

import com.km.rmbank.dto.HomeGoodsTypeDto;
import com.km.rmbank.dto.HomeNewRecommendDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/5/24.
 */

public class HomeDataEntity {
    private List<HomeGoodsTypeDto> homeGoodsTypeDtos;
    private List<HomeNewRecommendDto> homeNewRecommendDtos;

    public List<HomeGoodsTypeDto> getHomeGoodsTypeDtos() {
        return homeGoodsTypeDtos;
    }

    public void setHomeGoodsTypeDtos(List<HomeGoodsTypeDto> homeGoodsTypeDtos) {
        this.homeGoodsTypeDtos = homeGoodsTypeDtos;
    }

    public List<HomeNewRecommendDto> getHomeNewRecommendDtos() {
        return homeNewRecommendDtos;
    }

    public void setHomeNewRecommendDtos(List<HomeNewRecommendDto> homeNewRecommendDtos) {
        this.homeNewRecommendDtos = homeNewRecommendDtos;
    }
}
