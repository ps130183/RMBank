package com.km.rmbank.cell;

import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;
import com.ps.androidlib.utils.glide.GlideUtils;

/**
 * Created by kamangkeji on 17/3/17.
 */

public class PersonalUserInfoCell extends BaseCell<UserInfoDto> implements View.OnClickListener {

    public PersonalUserInfoCell(UserInfoDto mData) {
        super(mData,R.layout.personal_user_info);
    }

    public PersonalUserInfoCell(UserInfoDto mData, OnCellClickListener<UserInfoDto> onCellClickListener) {
        super(mData, R.layout.personal_user_info, onCellClickListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        if (mData !=null){
            GlideUtils.loadCircleImage(holder.getImageView(R.id.iv_user_portrait), mData.getPortraitUrl());
            holder.getTextView(R.id.tv_user_nick_name).setText(mData.getNickName());
        }

        holder.getImageView(R.id.iv_user_portrait).setOnClickListener(this);
        holder.getTextView(R.id.tv_user_account).setOnClickListener(this);
        holder.getTextView(R.id.tv_edit_card).setOnClickListener(this);
        holder.getTextView(R.id.tv_vip).setOnClickListener(this);
        holder.getTextView(R.id.tv_setting).setOnClickListener(this);
        holder.getImageView(R.id.iv_shop_cart).setOnClickListener(this);

    }

    @Override
    public int getItemViewType() {
        return 0;
    }

    @Override
    public void onClick(View v) {
        onCellClickListener.cellClick(mData,v.getId());
    }
}
