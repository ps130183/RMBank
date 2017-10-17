package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.dto.ActiveGoodsOrderListDto;
import com.ps.androidlib.utils.glide.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/9/6.
 */

public class ConvertInventoryAdapter extends BaseAdapter<ActiveGoodsOrderListDto> implements BaseAdapter.IAdapter<ConvertInventoryAdapter.ViewHolder> {

    public ConvertInventoryAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_convert_active_goods_inventory);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        ActiveGoodsOrderListDto activeGoodsOrderListDto = getItemData(position);

        GlideUtils.loadImage(holder.ivGoodsImage, activeGoodsOrderListDto.getThumbnailUrl());
        holder.tvGoodsName.setText(activeGoodsOrderListDto.getName());
        holder.tvGoodsPrice.setText(activeGoodsOrderListDto.getActiveValue()+"");
        if (activeGoodsOrderListDto.getStatus() == 1){
            holder.tvStatus.setText("未发货");
        } else {
            holder.tvStatus.setText("已发货");
        }
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.iv_goods_image)
        ImageView ivGoodsImage;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_goods_price)
        TextView tvGoodsPrice;

        @BindView(R.id.tv_status)
        TextView tvStatus;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
