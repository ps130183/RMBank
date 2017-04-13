package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.dto.MessageDto;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/4/13.
 */

public class MessageAdapter extends BaseAdapter<MessageDto> implements BaseAdapter.IAdapter<MessageAdapter.ViewHolder> {
    public MessageAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_home_message);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        MessageDto messageDto = getItemData(position);
        holder.tvMessageTime.setText(messageDto.getFormatCreateDate());
        holder.tvMessageContent.setText(messageDto.getContent());
    }

    class ViewHolder extends BaseViewHolder{
        @BindView(R.id.tv_message_title)
        TextView tvMessageTitle;
        @BindView(R.id.tv_message_time)
        TextView tvMessageTime;
        @BindView(R.id.tv_message_content)
        TextView tvMessageContent;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
