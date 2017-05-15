package com.km.rmbank.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.entity.HomeGtEntity;
import com.ps.androidlib.utils.glide.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/5/10.
 */

public class HomeGoodsTypeAdapter extends BaseAdapter<HomeGtEntity> implements BaseAdapter.IAdapter<HomeGoodsTypeAdapter.ViewHolder> {

    public HomeGoodsTypeAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_home_goods_type);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        holder.tvGoodsTypeName.setText(getItemData(position).getGtName());
        GlideUtils.loadImage(holder.ivGtImage,getItemData(position).getGtImageRes());
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.tv_goods_type_name)
        TextView tvGoodsTypeName;
        @BindView(R.id.iv_gt_image)
        ImageView ivGtImage;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
