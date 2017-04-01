package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;

import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.entity.ActionAreaEntity;

/**
 * Created by kamangkeji on 17/3/16.
 */

public class ActionAreaAdapter extends BaseAdapter<ActionAreaEntity> implements BaseAdapter.IAdapter<ActionAreaAdapter.ViewHolder> {

    public ActionAreaAdapter(Context mContext, int itemLayoutRes) {
        super(mContext, itemLayoutRes);
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

    class HeaderViewHolder extends BaseViewHolder{

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
