package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.dto.EvaluateDto;
import com.ps.androidlib.utils.glide.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/4/13.
 */

public class GoodsEvluateAdapter extends BaseAdapter<EvaluateDto> implements BaseAdapter.IAdapter<GoodsEvluateAdapter.ViewHolder> {

    public GoodsEvluateAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_goods_evaluate);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        EvaluateDto evaluateDto = getItemData(position);
        holder.tvShopName.setText(evaluateDto.getUserName());
        holder.tvContent.setText(evaluateDto.getContent());
        holder.tvTime.setText(evaluateDto.getFormatCreateDate());
        GlideUtils.loadCircleImage(holder.ivProtrait,evaluateDto.getPortraitUrl());
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.iv_protrait)
        ImageView ivProtrait;
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_content)
        TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
