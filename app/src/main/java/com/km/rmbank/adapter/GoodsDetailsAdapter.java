package com.km.rmbank.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.glide.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/4/24.
 */

public class GoodsDetailsAdapter extends BaseAdapter<String> implements BaseAdapter.IAdapter<GoodsDetailsAdapter.ViewHolde> {
    public GoodsDetailsAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_goods_details);
        setiAdapter(this);
    }

    @Override
    public ViewHolde createViewHolder(View view, int viewType) {
        return new ViewHolde(view);
    }

    @Override
    public void createView(ViewHolde holder, int position) {
        GlideUtils.loadImage(holder.ivGoodsDetails,getItemData(position));
//        GlideUtils.loadImage(holder.ivGoodsDetails,R.mipmap.timg);
    }

    class ViewHolde extends BaseViewHolder{

        @BindView(R.id.iv_goods_details)
        ImageView ivGoodsDetails;

        public ViewHolde(View itemView) {
            super(itemView);
        }
    }
}
