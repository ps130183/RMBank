package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;

import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.dto.GoodsDto;

/**
 * Created by kamangkeji on 17/3/14.
 */

public class ShopGoodsAdapter extends BaseAdapter<GoodsDto> implements BaseAdapter.IAdapter<ShopGoodsAdapter.ViewHolder> {
    public ShopGoodsAdapter(Context mContext, int itemLayoutRes) {
        super(mContext, itemLayoutRes);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {

    }

    class ViewHolder extends BaseViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
