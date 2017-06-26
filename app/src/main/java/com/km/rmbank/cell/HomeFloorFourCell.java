package com.km.rmbank.cell;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.dto.HomeNewRecommendDto;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;
import com.ps.androidlib.utils.glide.GlideUtils;

import java.util.List;

/**
 * Created by kamangkeji on 17/5/12.
 */

public class HomeFloorFourCell extends BaseCell<HomeNewRecommendDto> implements View.OnClickListener {

    private int itemViewType = 4000;

    public HomeFloorFourCell(HomeNewRecommendDto mData, OnCellClickListener<HomeNewRecommendDto> onCellClickListener) {
        super(mData, R.layout.cell_home_floor_four, onCellClickListener);
    }

    @Override
    public int getItemViewType() {
        return itemViewType;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        TextView tvFloorTitle = holder.getTextView(R.id.tv_floor_title);
        tvFloorTitle.setText(mData.getRecommendName());
        TextView tvFloorSubTitle = holder.getTextView(R.id.tv_floor_sub_title);
        tvFloorSubTitle.setText(mData.getSubtitle());

        List<HomeNewRecommendDto.TypeListBean> goodsList = mData.getTypeList();

        LinearLayout llGoods1 = holder.findView(R.id.ll_goods1);
        LinearLayout llGoods2 = holder.findView(R.id.ll_goods2);
        LinearLayout llGoods3 = holder.findView(R.id.ll_goods3);
        LinearLayout llGoods4 = holder.findView(R.id.ll_goods4);

        TextView tvGoodsTitle1 = holder.getTextView(R.id.tv_goods_title1);
        TextView tvGoodsTitle2 = holder.getTextView(R.id.tv_goods_title2);
        TextView tvGoodsTitle3 = holder.getTextView(R.id.tv_goods_title3);
        TextView tvGoodsTitle4 = holder.getTextView(R.id.tv_goods_title4);

        TextView tvGoodsSubTitle1 = holder.getTextView(R.id.tv_goods_sub_title1);
        TextView tvGoodsSubTitle2 = holder.getTextView(R.id.tv_goods_sub_title2);
        TextView tvGoodsSubTitle3 = holder.getTextView(R.id.tv_goods_sub_title3);
        TextView tvGoodsSubTitle4 = holder.getTextView(R.id.tv_goods_sub_title4);

        ImageView ivGoodsImage1 = holder.getImageView(R.id.iv_goods_image1);
        ImageView ivGoodsImage2 = holder.getImageView(R.id.iv_goods_image2);
        ImageView ivGoodsImage3 = holder.getImageView(R.id.iv_goods_image3);
        ImageView ivGoodsImage4 = holder.getImageView(R.id.iv_goods_image4);

        tvGoodsTitle1.setText(goodsList.get(0).getProductTypeName());
        tvGoodsSubTitle1.setText(goodsList.get(0).getDescribes());
        GlideUtils.loadImage(ivGoodsImage1,goodsList.get(0).getProductTypeImage());

        tvGoodsTitle2.setText(goodsList.get(1).getProductTypeName());
        tvGoodsSubTitle2.setText(goodsList.get(1).getDescribes());
        GlideUtils.loadImage(ivGoodsImage2,goodsList.get(1).getProductTypeImage());

        tvGoodsTitle3.setText(goodsList.get(2).getProductTypeName());
        tvGoodsSubTitle3.setText(goodsList.get(2).getDescribes());
        GlideUtils.loadImage(ivGoodsImage3,goodsList.get(2).getProductTypeImage());

        tvGoodsTitle4.setText(goodsList.get(3).getProductTypeName());
        tvGoodsSubTitle4.setText(goodsList.get(3).getDescribes());
        GlideUtils.loadImage(ivGoodsImage4,goodsList.get(3).getProductTypeImage());

        tvFloorTitle.setOnClickListener(null);
        tvFloorSubTitle.setOnClickListener(this);

        llGoods1.setOnClickListener(this);
        llGoods2.setOnClickListener(this);
        llGoods3.setOnClickListener(this);
        llGoods4.setOnClickListener(this);

        tvGoodsTitle1.setOnClickListener(this);
        tvGoodsTitle2.setOnClickListener(this);
        tvGoodsTitle3.setOnClickListener(this);
        tvGoodsTitle4.setOnClickListener(this);

        tvGoodsSubTitle1.setOnClickListener(this);
        tvGoodsSubTitle2.setOnClickListener(this);
        tvGoodsSubTitle3.setOnClickListener(this);
        tvGoodsSubTitle4.setOnClickListener(this);

        ivGoodsImage1.setOnClickListener(this);
        ivGoodsImage2.setOnClickListener(this);
        ivGoodsImage3.setOnClickListener(this);
        ivGoodsImage4.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int position = -1;
        switch (v.getId()){
            case R.id.tv_floor_sub_title:
                position = 0;
                break;
            case R.id.ll_goods1:
            case R.id.tv_goods_title1:
            case R.id.tv_goods_sub_title1:
            case R.id.iv_goods_image1:
                position = 1;
                break;
            case R.id.ll_goods2:
            case R.id.tv_goods_title2:
            case R.id.tv_goods_sub_title2:
            case R.id.iv_goods_image2:
                position = 2;
                break;
            case R.id.ll_goods3:
            case R.id.tv_goods_title3:
            case R.id.tv_goods_sub_title3:
            case R.id.iv_goods_image3:
                position = 3;
                break;
            case R.id.ll_goods4:
            case R.id.tv_goods_title4:
            case R.id.tv_goods_sub_title4:
            case R.id.iv_goods_image4:
                position = 4;
                break;
        }
        onCellClickListener.cellClick(mData,position);
    }

    public void setItemViewType(int itemViewType) {
        this.itemViewType += itemViewType;
    }
}
