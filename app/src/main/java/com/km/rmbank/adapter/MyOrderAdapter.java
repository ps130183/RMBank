package com.km.rmbank.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.OrderDto;
import com.ps.androidlib.utils.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/4/12.
 */

public class MyOrderAdapter extends BaseAdapter<OrderDto> implements BaseAdapter.IAdapter<MyOrderAdapter.ViewHolder> {

    public MyOrderAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_order_list);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        OrderDto orderDto = getItemData(position);

        holder.tvShopName.setText(orderDto.getShopName());
        GlideUtils.loadImage(holder.ivGoods,orderDto.getThumbnailUrl());
        holder.tvGoodsName.setText(orderDto.getProductName());
        holder.tvGoodsCount.setText(orderDto.getProductCount()+"");
        holder.tvTotalMoney.setText(orderDto.getTotalPrice()+"");
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.tv_order_status)
        TextView tvOrderStatus;

        @BindView(R.id.iv_goods)
        ImageView ivGoods;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_goods_count)
        TextView tvGoodsCount;
        @BindView(R.id.tv_total_money)
        TextView tvTotalMoney;

        public ViewHolder(View itemView) {
            super(itemView);
        }

    }
}
