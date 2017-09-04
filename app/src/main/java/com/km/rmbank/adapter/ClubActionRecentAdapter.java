package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.dto.ActionDto;
import com.ps.androidlib.utils.glide.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/7/7.
 */

public class ClubActionRecentAdapter extends BaseAdapter<ActionDto> implements BaseAdapter.IAdapter<ClubActionRecentAdapter.ViewHolder> {

    public ClubActionRecentAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_club_action_recent);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        ActionDto actionDto = getItemData(position);
        holder.tvActionTile.setText(actionDto.getTitle());
        holder.tvStartTime.setText("举办时间：" + actionDto.getHoldDate());
        GlideUtils.loadImage(holder.ivActionImg,actionDto.getActivityPictureUrl());

        String actionType = actionDto.getActivityType();
        String actionStatus = "";
        if("0".equals(actionType)){
            actionStatus = "审核中";
        } else if ("1".equals(actionType)){
            actionStatus = "审核通过";
        } else if (("2").equals(actionType)){
            actionStatus = "审核拒绝";
        }
        holder.tvActionStatus.setText(actionStatus);
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.tv_action_title)
        TextView tvActionTile;
        @BindView(R.id.tv_start_time)
        TextView tvStartTime;
        @BindView(R.id.iv_action_img)
        ImageView ivActionImg;
        @BindView(R.id.tv_action_status)
        TextView tvActionStatus;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
