package com.km.rmbank.cell;

import android.content.Context;
import android.widget.ImageView;

import com.km.rmbank.R;
import com.km.rmbank.dto.InformationDto;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.glide.GlideUtils;

/**
 * Created by kamangkeji on 17/7/14.
 */

public class Home3ClubAction2Cell extends BaseCell<InformationDto> {


    public Home3ClubAction2Cell(Context mContext, InformationDto mData) {
        super(mData, R.layout.cell_home3_club_action2);
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType() {
        return 2;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        int imageWidth = (AppUtils.getCurWindowWidth(mContext) - AppUtils.dip2px(mContext,16)) / 2;
        ImageView ivActionImg1 = holder.getImageView(R.id.iv_action_img1);
        ImageView ivActionImg2 = holder.getImageView(R.id.iv_action_img2);

        ivActionImg1.getLayoutParams().width = imageWidth;
        ivActionImg1.getLayoutParams().height = imageWidth / 148 * 98;

        ivActionImg2.getLayoutParams().width = imageWidth;
        ivActionImg2.getLayoutParams().height = imageWidth / 148 * 98;
        if (mData == null) return;

        holder.getTextView(R.id.tv_action_title).setText(mData.getTitle());

        String[] avatarUrls = mData.getAvatarUrl().split("#");
        GlideUtils.loadImage(ivActionImg1,avatarUrls[1]);
        GlideUtils.loadImage(ivActionImg2,avatarUrls[2]);
    }
}
