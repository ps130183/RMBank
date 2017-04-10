package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.dto.ReceiverAddressDto;

import butterknife.BindView;

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
        ReceiverAddressDto receiverAddressDto = getItemData(position);
        holder.tvReceiverName.setText(receiverAddressDto.getReceivePerson());
        holder.tvReceiverPhone.setText(receiverAddressDto.getReceivePersonPhone());
        holder.tvReceiverAddress.setText(receiverAddressDto.getReceiveAddress());

        holder.cbDefault.setChecked(receiverAddressDto.isDefault() == 1);
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.tv_receiver_name)
        TextView tvReceiverName;
        @BindView(R.id.tv_receiver_phone)
        TextView tvReceiverPhone;
        @BindView(R.id.tv_receiver_address)
        TextView tvReceiverAddress;

        @BindView(R.id.cb_default)
        CheckBox cbDefault;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
