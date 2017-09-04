package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.dto.InformationDto;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.glide.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/7/31.
 */

public class Home3ActionPastAdapter extends BaseAdapter<InformationDto> implements BaseAdapter.IAdapter<Home3ActionPastAdapter.ViewHolder> {

    private boolean isClub = false;

    public Home3ActionPastAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_home3_action_recent);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        InformationDto informationDto = getItemData(position);
        String actionUrl = "";
        if (informationDto.getAvatarUrl() != null && informationDto.getAvatarUrl().indexOf("#") > 0){
            actionUrl = informationDto.getAvatarUrl().split("#")[0];
        } else {
            actionUrl = informationDto.getAvatarUrl();
        }
        GlideUtils.loadImage(holder.ivActionImg,actionUrl);
        holder.tvActionName.setText(informationDto.getTitle());
        if (isClub){
            GlideUtils.loadCircleImage(holder.ivClubLogo,informationDto.getClubLogo());
            holder.tvClubNameTime.setText(informationDto.getClubName() + "    浏览量：" + informationDto.getViewCount());
        } else {
            holder.tvClubNameTime.setText("浏览量：" + informationDto.getViewCount());
        }

    }

    class ViewHolder extends BaseViewHolder{
        @BindView(R.id.iv_action_img)
        ImageView ivActionImg;
        @BindView(R.id.iv_club_logo)
        ImageView ivClubLogo;
        @BindView(R.id.tv_action_name)
        TextView tvActionName;
        @BindView(R.id.tv_club_name_time)
        TextView tvClubNameTime;

        @BindView(R.id.rl_club_info)
        RelativeLayout rlClubInfo;

        public ViewHolder(View itemView) {
            super(itemView);

            int windowWidth = AppUtils.getCurWindowWidth(mContext);
            ivActionImg.getLayoutParams().height = windowWidth / 320 * 152;
            rlClubInfo.getLayoutParams().height = windowWidth / 320 * 46;

            if (!isClub){
                ivClubLogo.setVisibility(View.INVISIBLE);
                ivClubLogo.getLayoutParams().width = 1;
            }

        }
    }

    public void setClub(boolean club) {
        isClub = club;
    }
}
