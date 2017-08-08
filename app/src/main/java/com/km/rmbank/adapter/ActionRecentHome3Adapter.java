package com.km.rmbank.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ActionDto;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.DateUtils;
import com.ps.androidlib.utils.glide.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/7/19.
 */

public class ActionRecentHome3Adapter extends BaseAdapter<ActionDto> implements BaseAdapter.IAdapter<ActionRecentHome3Adapter.ViewHolder> {

    public ActionRecentHome3Adapter(Context mContext) {
        super(mContext, R.layout.item_rv_home3_action_recent);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        ActionDto actionDto = getItemData(position);
        GlideUtils.loadImage(holder.ivActionImg,actionDto.getActivityPictureUrl());
        GlideUtils.loadCircleImage(holder.ivClubLogo,actionDto.getClubLogo());

        holder.tvActionName.setText(actionDto.getTitle());
        String clubName = TextUtils.isEmpty(actionDto.getClubName()) ? "玩转地球商旅学苑" : actionDto.getClubName();
        holder.tvClubNameTime.setText(clubName + "    " + DateUtils.getInstance().getDate(actionDto.getStartDate()));
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.iv_action_img)
        ImageView ivActionImg;
        @BindView(R.id.rl_club_info)
        RelativeLayout rlClubInfo;

        @BindView(R.id.iv_club_logo)
        ImageView ivClubLogo;
        @BindView(R.id.tv_action_name)
        TextView tvActionName;
        @BindView(R.id.tv_club_name_time)
        TextView tvClubNameTime;

        public ViewHolder(View itemView) {
            super(itemView);
            int windowWidth = AppUtils.getCurWindowWidth(mContext);
            ivActionImg.getLayoutParams().height = windowWidth / 320 * 152;
            rlClubInfo.getLayoutParams().height = windowWidth / 320 * 46;
        }
    }
}
