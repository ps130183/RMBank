package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.entity.OrderEntity;

/**
 * Created by kamangkeji on 17/4/12.
 */

public class MyOrderAdapter extends BaseAdapter<OrderEntity> implements BaseAdapter.IAdapter<MyOrderAdapter.ViewHolder> {

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

    }

    class ViewHolder extends BaseViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
