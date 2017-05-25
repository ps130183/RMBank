package com.km.rmbank.cell;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.dto.HomeNewRecommendDto;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.glide.GlideUtils;

/**
 * Created by kamangkeji on 17/5/11.
 */

public class HomeFloorOneCell extends BaseCell<HomeNewRecommendDto> implements View.OnClickListener {

    public HomeFloorOneCell(HomeNewRecommendDto mData, OnCellClickListener<HomeNewRecommendDto> onCellClickListener) {
        super(mData, R.layout.cell_home_floor_one, onCellClickListener);
    }

    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        //计算图片高度
        ImageView ivContent = holder.findView(R.id.iv_content);
        GlideUtils.loadImage(ivContent,mData.getIndexActivityImage());
        Context context = ivContent.getContext();

        int windowWidth = AppUtils.getCurWindowWidth(context);
        int ivHeight = windowWidth / 317 * 107;
        ivContent.getLayoutParams().height = ivHeight;

        TextView tvFloorTitle = holder.getTextView(R.id.tv_floor_title);
        tvFloorTitle.setText(mData.getRecommendName());
        TextView tvFloorSubTitle = holder.getTextView(R.id.tv_floor_sub_title);
        tvFloorSubTitle.setText(mData.getSubtitle());

        TextView tvFloor1 = holder.getTextView(R.id.tv_floor1);
        TextView tvFloor2 = holder.getTextView(R.id.tv_floor2);
        TextView tvFloor3 = holder.getTextView(R.id.tv_floor3);
        TextView tvFloor4 = holder.getTextView(R.id.tv_floor4);

        tvFloor1.setText(mData.getTypeList().get(0).getProductTypeName());
        tvFloor2.setText(mData.getTypeList().get(1).getProductTypeName());
        tvFloor3.setText(mData.getTypeList().get(2).getProductTypeName());
        tvFloor4.setText(mData.getTypeList().get(3).getProductTypeName());

        tvFloorSubTitle.setOnClickListener(this);
        tvFloor1.setOnClickListener(this);
        tvFloor2.setOnClickListener(this);
        tvFloor3.setOnClickListener(this);
        tvFloor4.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int position = -1;
        switch (v.getId()){
            case R.id.tv_floor_sub_title:
                position = 0;
                break;
            case R.id.tv_floor1:
                position = 1;
                break;
            case R.id.tv_floor2:
                position = 2;
                break;
            case R.id.tv_floor3:
                position = 3;
                break;
            case R.id.tv_floor4:
                position = 4;
                break;

        }
        onCellClickListener.cellClick(mData,position);
    }
}
