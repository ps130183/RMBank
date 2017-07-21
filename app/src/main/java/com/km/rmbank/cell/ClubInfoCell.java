package com.km.rmbank.cell;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.km.rmbank.R;
import com.km.rmbank.dto.ClubDto;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;
import com.ps.androidlib.utils.glide.GlideUtils;

/**
 * Created by kamangkeji on 17/7/7.
 */

public class ClubInfoCell extends BaseCell<ClubDto> {

    private onFollowClubListener onFollowClubListener;
    private Button btnFollowClub;

    public ClubInfoCell(ClubDto mData) {
        super(mData, R.layout.cell_club_info);
    }

    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        btnFollowClub = holder.getButton(R.id.btn_follow_club);

        ImageView ivClubLogo = holder.getImageView(R.id.iv_upload_logo);
        ImageView ivBackground = holder.getImageView(R.id.iv_background);

        if (mData != null){
            GlideUtils.loadCircleImage(ivClubLogo,mData.getClubLogo());
            if (!TextUtils.isEmpty(mData.getBackgroundImg())){
//                GlideUtils.loadImage(ivBackground,R.mipmap.timg);
                GlideUtils.loadImage(ivBackground,mData.getBackgroundImg());
            }

            holder.getTextView(R.id.tv_club_name).setText(mData.getClubName());
            holder.getTextView(R.id.tv_club_introduce).setText(mData.getContent());
        }

        btnFollowClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFollowClubListener.followClub(mData);
            }
        });
    }

    public interface onFollowClubListener{
        void followClub(ClubDto clubDto);
    }

    public void setOnFollowClubListener(ClubInfoCell.onFollowClubListener onFollowClubListener) {
        this.onFollowClubListener = onFollowClubListener;
    }

    public void setFollow(boolean isFollow){
        if (isFollow){
            btnFollowClub.setText("已关注");
        } else {
            btnFollowClub.setText("关注 +");
        }
    }

    public void setMyClub(boolean isMyClub){
        if (isMyClub){
            btnFollowClub.setVisibility(View.GONE);
        } else {
            btnFollowClub.setVisibility(View.VISIBLE);
        }
    }

}
