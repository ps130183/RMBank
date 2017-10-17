package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.dto.ActiveGoodsDto;
import com.ps.androidlib.utils.glide.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/9/6.
 */

public class ConvertActiveGoodsAdapter extends BaseAdapter<ActiveGoodsDto> implements BaseAdapter.IAdapter<ConvertActiveGoodsAdapter.ViewHolder> {

    public ConvertActiveGoodsAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_convert_active_goods);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        ActiveGoodsDto activeGoodsDto = getItemData(position);
        GlideUtils.loadImage(holder.ivGoodsImage,activeGoodsDto.getThumbnailUrl());
        holder.tvGoodsPrice.setText(activeGoodsDto.getActiveValue()+"");
        holder.tvGoodsName.setText(activeGoodsDto.getName());
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.iv_goods_image)
        ImageView ivGoodsImage;
        @BindView(R.id.tv_goods_price)
        TextView tvGoodsPrice;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
