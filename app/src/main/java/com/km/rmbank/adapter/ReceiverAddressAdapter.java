package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.BaseSwipeRvAdapter;
import com.km.rmbank.dto.ReceiverAddressDto;
import com.ps.androidlib.utils.DialogUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/3/30.
 */

public class ReceiverAddressAdapter extends BaseSwipeRvAdapter<ReceiverAddressDto> implements BaseSwipeRvAdapter.IAdapter<ReceiverAddressAdapter.ViewHolder> {

    private onSetDefaultListener onSetDefaultListener;
    private onDeleteListener onDeleteListener;

    public ReceiverAddressAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_receiver_address);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(final ViewHolder holder, int position) {
        final ReceiverAddressDto receiverAddressDto = getItemData(position);
        holder.tvReceiverName.setText(receiverAddressDto.getReceivePerson());
        holder.tvReceiverPhone.setText(receiverAddressDto.getReceivePersonPhone());
        holder.tvReceiverAddress.setText(receiverAddressDto.getReceiveAddress());

        holder.cbDefault.setChecked(receiverAddressDto.isDefault() == 1);
        holder.cbDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSetDefaultListener != null){
                    onSetDefaultListener.setDefault(receiverAddressDto);
                }
            }
        });

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteListener != null){
                    DialogUtils.showDefaultAlertDialog("是否要删除该收货地址？", new DialogUtils.ClickListener() {
                        @Override
                        public void clickConfirm() {
                            holder.getmSwiperLayout().close(true);
                            onDeleteListener.deleteReceiverAddress(receiverAddressDto);
                        }
                    });
                }
            }
        });
    }

    class ViewHolder extends BaseSwipeViewHolder{

        @BindView(R.id.tv_receiver_name)
        TextView tvReceiverName;
        @BindView(R.id.tv_receiver_phone)
        TextView tvReceiverPhone;
        @BindView(R.id.tv_receiver_address)
        TextView tvReceiverAddress;

        @BindView(R.id.cb_default)
        CheckBox cbDefault;

        @BindView(R.id.tv_delete)
        TextView tvDelete;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface onSetDefaultListener{
        void setDefault(ReceiverAddressDto receiverAddressDto);
    }

    public void setOnSetDefaultListener(ReceiverAddressAdapter.onSetDefaultListener onSetDefaultListener) {
        this.onSetDefaultListener = onSetDefaultListener;
    }

    public interface onDeleteListener{
        void deleteReceiverAddress(ReceiverAddressDto receiverAddressDto);
    }

    public void setOnDeleteListener(ReceiverAddressAdapter.onDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }
}
