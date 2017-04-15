package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.dto.GoodsDto;
import com.ps.androidlib.utils.glide.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/4/1.
 */

public class GoodsListShoppingAdapter extends BaseAdapter<GoodsDto> implements BaseAdapter.IAdapter<GoodsListShoppingAdapter.ViewHolder> {

    public GoodsListShoppingAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_shop_goods);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        GoodsDto goodsDto = getItemData(position);
        GlideUtils.loadImage(holder.ivGoods,goodsDto.getThumbnailUrl());
        holder.tvGoodsName.setText(goodsDto.getName());
        holder.tvGoodsPrice.setText("¥"+goodsDto.getPrice());
        holder.tvSubTitle.setText(goodsDto.getSubtitle());
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.iv_goods)
        ImageView ivGoods;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_sub_title)
        TextView tvSubTitle;

        @BindView(R.id.tv_goods_price)
        TextView tvGoodsPrice;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
