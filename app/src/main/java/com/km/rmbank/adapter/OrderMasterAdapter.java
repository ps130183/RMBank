package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.dto.MasterDto;
import com.ps.androidlib.utils.BannerUtils;
import com.ps.androidlib.utils.glide.GlideUtils;
import com.youth.banner.Banner;

import butterknife.BindView;

/**
 * Created by PengSong on 17/10/17.
 */

public class OrderMasterAdapter extends BaseAdapter<MasterDto> implements BaseAdapter.IAdapter<OrderMasterAdapter.ViewHolder> {

    public OrderMasterAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_order_master);
        setiAdapter(this);
    }


    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        MasterDto masterDto = getItemData(position);
        GlideUtils.loadCircleImage(holder.ivProtrait,masterDto.getPortraitUrl());
        holder.tvMasterTitle.setText(masterDto.getHeadings());
        holder.tvMasterName.setText(masterDto.getName());
        holder.tvMasterHint.setText(masterDto.getIntroduce());
        holder.tvPersonNumber.setText(masterDto.getNum() + "人拜访过");
    }

    class ViewHolder extends BaseViewHolder {

        @BindView(R.id.iv_protrait)
        ImageView ivProtrait;
        @BindView(R.id.tv_master_title)
        TextView tvMasterTitle;
        @BindView(R.id.tv_master_name)
        TextView tvMasterName;
        @BindView(R.id.tv_master_hint)
        TextView tvMasterHint;
        @BindView(R.id.tv_person_number)
        TextView tvPersonNumber;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
