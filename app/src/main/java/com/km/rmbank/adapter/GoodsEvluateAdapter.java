package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.dto.EvaluateDto;

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

    }

    class ViewHolder extends BaseViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
