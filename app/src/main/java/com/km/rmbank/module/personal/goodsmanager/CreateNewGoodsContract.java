package com.km.rmbank.module.personal.goodsmanager;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.dto.GoodsTypeDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/4/7.
 */

public interface CreateNewGoodsContract {
    interface View extends BaseView{
        void showImageUploadResult(int photoType,String photoUrl);
        void createNewGoodsSuccess();
        void showImageUploadingProgress(int photoType,int progress,int position);
        void showGoodsInfo(GoodsDetailsDto goodsDetailsDto);
    }
    interface Presenter extends BasePresenter{
        void uploadImage(String photoList,int photoType,int position);
        void createNewGoods(GoodsDetailsDto goodsDetailsDto);
        void getGoodsInfo(String productNo);
        void updateGoodsInfo(GoodsDetailsDto goodsDetailsDto);
    }
}
