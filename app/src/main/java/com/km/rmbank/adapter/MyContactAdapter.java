package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.dto.MyFriendsDto;
import com.ps.androidlib.utils.glide.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/6/8.
 */

public class MyContactAdapter extends BaseAdapter<MyFriendsDto> implements BaseAdapter.IAdapter<MyContactAdapter.ViewHolder> {

    public MyContactAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_my_contact);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        MyFriendsDto myFriendsDto = getItemData(position);
        GlideUtils.loadCircleImage(holder.ivProtrait,myFriendsDto.getPortraitUrl());
        holder.tvNickname.setText(myFriendsDto.getNickName());
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.iv_protrait)
        ImageView ivProtrait;
        @BindView(R.id.tv_nickname)
        TextView tvNickname;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
