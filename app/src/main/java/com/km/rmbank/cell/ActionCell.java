package com.km.rmbank.cell;

import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.dto.ActionDto;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.glide.GlideUtils;

/**
 * Created by kamangkeji on 17/3/15.
 */

public class ActionCell extends BaseCell<ActionDto> {


    public ActionCell(ActionDto mData) {
        super(mData, R.layout.item_rv_action_area);
        setOnCellClickListener(new OnCellClickListener<ActionDto>() {
            @Override
            public void cellClick(ActionDto mData, int position) {
                Logger.d(mData.getTitle());
            }
        });
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder,position);
        TextView name = holder.findView(R.id.tv_action_title);
        ImageView ivAction = holder.getImageView(R.id.iv_action);
        name.setText(mData.getTitle());
        GlideUtils.loadImage(ivAction,mData.getActivityPictureUrl());
    }

}
