package com.km.rmbank.cell;

import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.dto.HomeNewRecommendDto;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;

import java.util.List;

/**
 * Created by kamangkeji on 17/5/12.
 */

public class HomeFloorThreeCell extends BaseCell<HomeNewRecommendDto> implements View.OnClickListener {

    public HomeFloorThreeCell(HomeNewRecommendDto mData, OnCellClickListener<HomeNewRecommendDto> onCellClickListener) {
        super(mData, R.layout.cell_home_floor_three, onCellClickListener);
    }

    @Override
    public int getItemViewType() {
        return 3;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        TextView tvFloorTitle = holder.getTextView(R.id.tv_floor_title);
        tvFloorTitle.setText(mData.getRecommendName());
        TextView tvFloorSubTitle = holder.getTextView(R.id.tv_floor_sub_title);
        tvFloorSubTitle.setText(mData.getSubtitle());

        TextView tvFloor1 = holder.getTextView(R.id.tv_floor1);
        TextView tvFloor2 = holder.getTextView(R.id.tv_floor2);
        TextView tvFloor3 = holder.getTextView(R.id.tv_floor3);
        TextView tvFloor4 = holder.getTextView(R.id.tv_floor4);
        TextView tvFloor5 = holder.getTextView(R.id.tv_floor5);
        TextView tvFloor6 = holder.getTextView(R.id.tv_floor6);

        List<HomeNewRecommendDto.TypeListBean> typeListBeanList = mData.getTypeList();
        tvFloor1.setText(typeListBeanList.get(0).getProductTypeName());
        tvFloor2.setText(typeListBeanList.get(1).getProductTypeName());
        tvFloor3.setText(typeListBeanList.get(2).getProductTypeName());
        tvFloor4.setText(typeListBeanList.get(3).getProductTypeName());
        tvFloor5.setText(typeListBeanList.get(4).getProductTypeName());
        tvFloor6.setText(typeListBeanList.get(5).getProductTypeName());

        tvFloorSubTitle.setOnClickListener(this);
        tvFloor1.setOnClickListener(this);
        tvFloor2.setOnClickListener(this);
        tvFloor3.setOnClickListener(this);
        tvFloor4.setOnClickListener(this);
        tvFloor5.setOnClickListener(this);
        tvFloor6.setOnClickListener(this);
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
            case R.id.tv_floor5:
                position = 5;
                break;
            case R.id.tv_floor6:
                position = 6;
                break;
        }
        onCellClickListener.cellClick(mData,position);
    }
}
