package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.dto.NearbyVipDto;
import com.ps.androidlib.utils.StringUtils;
import com.ps.androidlib.utils.glide.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/6/5.
 */

public class NearbyVipAdapter extends BaseAdapter<NearbyVipDto> implements BaseAdapter.IAdapter<NearbyVipAdapter.ViewHolder> {

    public NearbyVipAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_nearby_vip);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        NearbyVipDto nearbyVipDto = getItemData(position);
        GlideUtils.loadCircleImage(holder.ivProtrait,nearbyVipDto.getPortraitUrl());
        holder.tvNickname.setText(nearbyVipDto.getNickName());
        holder.tvPhone.setText(StringUtils.hidePhone(nearbyVipDto.getMobilePhone()));
        holder.tvDistance.setText(nearbyVipDto.getDistance());
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.iv_protrait)
        ImageView ivProtrait;
        @BindView(R.id.tv_nickname)
        TextView tvNickname;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.tv_distance)
        TextView tvDistance;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
