package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.dto.MasterOrderDto;
import com.ps.androidlib.utils.DateUtils;
import com.ps.androidlib.utils.glide.GlideUtils;

import java.util.Date;

import butterknife.BindView;

/**
 * Created by PengSong on 17/10/26.
 */

public class MasterSubscribeOrderAdapter extends BaseAdapter<MasterOrderDto> implements BaseAdapter.IAdapter<MasterSubscribeOrderAdapter.ViewHolder> {

    public MasterSubscribeOrderAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_master_and_me);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        MasterOrderDto orderDto = getItemData(position);
        GlideUtils.loadCircleImage(holder.ivProtrait,orderDto.getPortraitUrl());
        holder.tvName.setText(orderDto.getMacaName());
        String status;
        switch (orderDto.getStatus()){
            case "1":
                status = "预约中";
                break;
            case "2":
                status = "预约成功";
                break;
            case "3":
                status = "预约失败";
                break;
            default:
                status = "";
                break;
        }
        holder.tvStatus.setText(status);
        holder.tvProjectName.setText(orderDto.getMacaWorks());
        holder.tvPrice.setText("¥" + orderDto.getMoney());
        holder.tvCreateTime.setText(DateUtils.getInstance().dateToString(new Date(orderDto.getUpdateDate()),DateUtils.YMDHM));

    }


    class ViewHolder extends BaseViewHolder {

        @BindView(R.id.iv_protrait)
        ImageView ivProtrait;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_project_name)
        TextView tvProjectName;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        @BindView(R.id.tv_create_time)
        TextView tvCreateTime;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
