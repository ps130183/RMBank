package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.dto.ActionDto;
import com.ps.androidlib.utils.DateUtils;
import com.ps.androidlib.utils.glide.GlideUtils;

import java.util.Date;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/4/18.
 */

public class ActionListAdapter extends BaseAdapter<ActionDto> implements BaseAdapter.IAdapter<ActionListAdapter.ViewHolder> {
    public ActionListAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_action_area);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        ActionDto actionDto = getItemData(position);
        holder.tvActionTitle.setText(actionDto.getTitle());
        GlideUtils.loadImage(holder.ivAction,actionDto.getActivityPictureUrl());
//        holder.tvStartDate.setText("活动时间：" + DateUtils.getInstance().getDate(actionDto.getHoldDate()));
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.tv_start_date)
        TextView tvStartDate;
        @BindView(R.id.tv_action_title)
        TextView tvActionTitle;
        @BindView(R.id.iv_action)
        ImageView ivAction;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
