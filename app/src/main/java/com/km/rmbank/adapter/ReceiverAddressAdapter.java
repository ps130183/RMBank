package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.dto.ReceiverAddressDto;

/**
 * Created by kamangkeji on 17/3/30.
 */

public class ReceiverAddressAdapter extends BaseAdapter<ReceiverAddressDto> implements BaseAdapter.IAdapter<ReceiverAddressAdapter.ViewHolder> {

    public ReceiverAddressAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_receiver_address);
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
