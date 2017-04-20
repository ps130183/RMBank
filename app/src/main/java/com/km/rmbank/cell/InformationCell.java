package com.km.rmbank.cell;

import com.km.rmbank.R;
import com.km.rmbank.dto.InformationDto;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;
import com.ps.androidlib.utils.glide.GlideUtils;

/**
 * Created by kamangkeji on 17/4/20.
 */

public class InformationCell extends BaseCell<InformationDto> {

    public InformationCell(InformationDto mData,OnCellClickListener onCellClickListener) {
        super(mData, R.layout.item_rv_infromation);
        setOnCellClickListener(onCellClickListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.getTextView(R.id.tv_information_title).setText(mData.getTitle());
        GlideUtils.loadImage(holder.getImageView(R.id.iv_information),mData.getAvatarUrl());
    }
}
