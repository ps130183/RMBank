package com.km.rmbank.cell;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.km.rmbank.R;
import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.InformationDto;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.glide.GlideUtils;


/**
 * Created by kamangkeji on 17/7/1.
 */

public class HomePastActionTwoCell extends BaseCell<InformationDto> {

    private Context mContext;

    public HomePastActionTwoCell(Context mContext, InformationDto mData) {
        super(mData, R.layout.cell_home_past_action_two);
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType() {
        return 2;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        int windowWidth = AppUtils.getCurWindowWidth(mContext);

        ImageView ivAction = holder.getImageView(R.id.iv_action);
        ivAction.getLayoutParams().height = (int) (windowWidth * 0.55 * 0.82);
        ivAction.getLayoutParams().width = (int) (windowWidth * 0.55);

        ImageView ivClubLogo = holder.getImageView(R.id.iv_club_logo);
        ivClubLogo.getLayoutParams().height = (int) (windowWidth * 0.38);
        ivClubLogo.getLayoutParams().width = (int) (windowWidth * 0.38);

        //以下是设置对应的俱乐部 数据
        GlideUtils.loadCircleImage(ivClubLogo,mData.getClubLogo());

        GlideUtils.loadImage(ivAction,mData.getAvatarUrl());

        holder.getTextView(R.id.tv_clud_name).setText(mData.getClubName());

        holder.getTextView(R.id.tv_action_title).setText(mData.getTitle());

    }
}
