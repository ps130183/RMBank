package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.dto.ActionPastDto;
import com.ps.androidlib.utils.glide.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/7/7.
 */

public class ClubActionPastAdapter extends BaseAdapter<ActionPastDto> implements BaseAdapter.IAdapter<ClubActionPastAdapter.ViewHolder> {

    public ClubActionPastAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_club_action_past);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        ActionPastDto actionPastDto = getItemData(position);
        holder.tvActionTitle.setText(actionPastDto.getTitle());
        holder.tvCount.setText("浏览次数：" + actionPastDto.getViewCount());
        String[] avatarUrls = actionPastDto.getAvatarUrl().split("#");
        GlideUtils.loadImage(holder.ivAvatar,avatarUrls[0]);
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.tv_action_title)
        TextView tvActionTitle;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.iv_avatar)
        ImageView ivAvatar;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
