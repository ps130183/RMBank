package com.km.rmbank.cell;

import android.view.View;

import com.km.rmbank.R;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;

/**
 * Created by kamangkeji on 17/6/5.
 */

public class HomeVipCell extends BaseCell<String> {

    public HomeVipCell(String mData, OnCellClickListener<String> onCellClickListener) {
        super(mData, R.layout.cell_home_vip, onCellClickListener);
    }

    @Override
    public int getItemViewType() {
        return 5;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.getImageView(R.id.iv_vip1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCellClickListener.cellClick("",0);
            }
        });
        holder.getImageView(R.id.iv_vip2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCellClickListener.cellClick("",1);
            }
        });
    }
}
