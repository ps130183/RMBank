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

public class Home3ClubAction1Cell extends BaseCell<InformationDto> {


    public Home3ClubAction1Cell(Context mContext,InformationDto mData) {
        super(mData, R.layout.cell_home3_club_action1);
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        int windowWidth = AppUtils.getCurWindowWidth(mContext);
        ImageView ivActionImg = holder.getImageView(R.id.iv_action_img);
        ivActionImg.getLayoutParams().height = windowWidth / 320 * 125;
        if (mData == null) return;

        String[] avatarUrls = mData.getAvatarUrl().split("#");
        GlideUtils.loadImage(ivActionImg,avatarUrls[0]);

        holder.getTextView(R.id.tv_action_title).setText(mData.getTitle());
    }
}
