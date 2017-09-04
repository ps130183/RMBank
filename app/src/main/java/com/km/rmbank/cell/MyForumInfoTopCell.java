package com.km.rmbank.cell;

import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.dto.ForumInfoDto;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;

/**
 * Created by kamangkeji on 17/8/14.
 */

public class MyForumInfoTopCell extends BaseCell<String> {

    private TextView tvLikeNumber;
    private TextView tvCommentNumber;
    private TextView tvForumNumber;

    public MyForumInfoTopCell(String mData) {
        super(mData, R.layout.cell_my_forum_info_top);
    }

    @Override
    public int getItemViewType() {
        return 0;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        tvLikeNumber = holder.getTextView(R.id.tv_like_number);
        tvCommentNumber = holder.getTextView(R.id.tv_comment_number);
        tvForumNumber = holder.getTextView(R.id.tv_forum_number);
    }

    public void setData(ForumInfoDto forumInfoDto){
        tvLikeNumber.setText(forumInfoDto.getPraises() + "");
        tvCommentNumber.setText(forumInfoDto.getCommentsNumber()+"");
        tvForumNumber.setText(forumInfoDto.getPosts()+"");
    }
}
