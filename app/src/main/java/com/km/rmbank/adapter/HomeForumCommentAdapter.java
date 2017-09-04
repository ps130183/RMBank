package com.km.rmbank.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.dto.ForumDto;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/8/11.
 */

public class HomeForumCommentAdapter extends BaseAdapter<ForumDto.ForumCommentDto> implements BaseAdapter.IAdapter<HomeForumCommentAdapter.ViewHolder> {

    public HomeForumCommentAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_home_forum_comment);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        ForumDto.ForumCommentDto commentDto = getItemData(position);
        String comment = "<font color=\'#0099cf\'>" + commentDto.getNickName() + "</font>: " + commentDto.getRuleCommentContent();
        holder.tvForumComment.setText(Html.fromHtml(comment));
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.tv_forum_comment)
        TextView tvForumComment;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
