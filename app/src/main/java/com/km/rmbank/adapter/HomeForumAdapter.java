package com.km.rmbank.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.ForumDto;
import com.ps.androidlib.utils.glide.GlideUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/8/11.
 */

public class HomeForumAdapter extends BaseAdapter<ForumDto> implements BaseAdapter.IAdapter<HomeForumAdapter.ViewHolder>,BaseAdapter.IHeaderAdapter<HomeForumAdapter.HeaderViewHolder> {

    private OnClickMoreCommentListener onClickMoreCommentListener;
    private OnClickLikeOrCommentListener onClickLikeOrCommentListener;
    private OnClickImageListener onClickImageListener;

    public HomeForumAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_home_forum);
        setiAdapter(this);
        setiHeaderAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(final ViewHolder holder, final int position) {
        final ForumDto mForumDto = getItemData(position);
        if (mForumDto.getForumImgContents() != null){
            holder.rvForumImgContent.setVisibility(View.VISIBLE);
            final List<String> forumImgContents = mForumDto.getForumImgContents();
            if (mForumDto.getForumImgContents().size() > 0 && forumImgContents.size() <= 3){
                holder.forumImgAdapter.addData(forumImgContents);
            } else if (forumImgContents.size() > 3){
                holder.forumImgAdapter.addData(forumImgContents.subList(0,3));
            }
            holder.forumImgAdapter.setItemClickListener(new ItemClickListener<String>() {
                @Override
                public void onItemClick(String itemData, int position) {
                    if (onClickImageListener != null){
                        onClickImageListener.clickImages(forumImgContents);
                    }
                }

            });
        } else {
            holder.rvForumImgContent.setVisibility(View.GONE);
        }

        //个人信息
        GlideUtils.loadCircleImage(holder.ivUserPortrait,mForumDto.getPortraitUrl());
        holder.tvUserNickName.setText(mForumDto.getNickName());
        holder.tvReleaseTime.setText(mForumDto.getCreateTime());
        holder.tvForumTitle.setText(mForumDto.getRuleTitle());
        if (!TextUtils.isEmpty(mForumDto.getRuleContent())){
            holder.tvForumContent.setVisibility(View.VISIBLE);
            holder.tvForumContent.setText(mForumDto.getRuleContent());
        } else {
            holder.tvForumContent.setVisibility(View.GONE);
        }



        //设置点赞 图标
        Drawable drawable = null;
        if ("0".equals(mForumDto.getIsNotPraise())){
            drawable= mContext.getResources().getDrawable(R.mipmap.ic_home_forum_like_unpress);
        } else {
            drawable= mContext.getResources().getDrawable(R.mipmap.ic_home_forum_lick_pressed);
        }
        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        holder.tvForumLike.setCompoundDrawables(drawable,null,null,null);
        holder.tvForumLike.setText("(" + mForumDto.getPraise() + ")");


        holder.tvForumComment.setText("(" + mForumDto.getCommentsNumber() + ")");
        if (mForumDto.getRuleCommentsList().size() == 0){
            holder.rvForumComment.setVisibility(View.GONE);
        } else {
            holder.rvForumComment.setVisibility(View.VISIBLE);
            holder.commentAdapter.addData(mForumDto.getRuleCommentsList(),1);
        }

        if (mForumDto.getMoreForumCommentDtos() != null && mForumDto.getMoreForumCommentDtos().size() >= 0){
            holder.viewLineBottom.setVisibility(View.VISIBLE);
            holder.tvMoreComment.setVisibility(View.GONE);
            holder.commentAdapter.addData(mForumDto.getMoreForumCommentDtos(),2);
        } else if (mForumDto.getRuleCommentsList().size() == 0){
            holder.tvMoreComment.setVisibility(View.GONE);
            holder.viewLineBottom.setVisibility(View.GONE);
        } else if (mForumDto.getRuleCommentsList().size() != 0 && mForumDto.getCommentNumberStatus() == 0){
            holder.tvMoreComment.setVisibility(View.GONE);
            holder.viewLineBottom.setVisibility(View.VISIBLE);
        }else {
            holder.tvMoreComment.setVisibility(View.VISIBLE);
            holder.viewLineBottom.setVisibility(View.VISIBLE);
        }


        holder.tvMoreComment.setOnClickListener(new View.OnClickListener() {//查看更多 评论
            @Override
            public void onClick(View v) {
                if (onClickMoreCommentListener != null){
                    onClickMoreCommentListener.onClickMoreComment(mForumDto,position);
                }
            }
        });

        holder.tvForumLike.setOnClickListener(new View.OnClickListener() {//点赞
            @Override
            public void onClick(View v) {
                if (onClickLikeOrCommentListener != null){
                    onClickLikeOrCommentListener.clickLikeForum(mForumDto,position);
                }
            }
        });
        holder.tvForumComment.setOnClickListener(new View.OnClickListener() {//评论
            @Override
            public void onClick(View v) {
                if (onClickLikeOrCommentListener != null){
                    onClickLikeOrCommentListener.clickCommentForum(mForumDto,position);
                }
            }
        });

    }

    @Override
    public HeaderViewHolder createHeaderViewHolder(View view, int viewType) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void createHeaderView(HeaderViewHolder holder, int position) {

    }

    class HeaderViewHolder extends BaseHeaderViewHolder{

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ViewHolder extends BaseViewHolder{

        @BindView(R.id.iv_user_portrait)
        ImageView ivUserPortrait;
        @BindView(R.id.tv_user_nick_name)
        TextView tvUserNickName;
        @BindView(R.id.tv_release_time)
        TextView tvReleaseTime;
        @BindView(R.id.tv_forum_title)
        TextView tvForumTitle;
        @BindView(R.id.tv_forum_content)
        TextView tvForumContent;

        @BindView(R.id.view_line_bottom)
        View viewLineBottom;
        @BindView(R.id.rv_forum_content)
        RecyclerView rvForumImgContent;
        HomeForumImgAdapter forumImgAdapter;

        @BindView(R.id.rv_forum_comment)
        RecyclerView rvForumComment;
        HomeForumCommentAdapter commentAdapter;

        @BindView(R.id.tv_more_comment)
        TextView tvMoreComment;

        @BindView(R.id.tv_forum_like)
        TextView tvForumLike;
        @BindView(R.id.tv_forum_comment)
        TextView tvForumComment;

        public ViewHolder(View itemView) {
            super(itemView);
            initForumImg();
            initForumComment();
        }

        private void initForumImg(){
            RVUtils.setGridLayoutManage(rvForumImgContent,3);
            forumImgAdapter = new HomeForumImgAdapter(mContext);
            rvForumImgContent.setAdapter(forumImgAdapter);
        }

        private void initForumComment(){
            RVUtils.setLinearLayoutManage(rvForumComment, LinearLayoutManager.VERTICAL);
            commentAdapter = new HomeForumCommentAdapter(mContext);
            rvForumComment.setAdapter(commentAdapter);
        }
    }

    /**--------------------------查看更多评论--------------------------------------*/
    public interface OnClickMoreCommentListener{
        void onClickMoreComment(ForumDto forumDto,int position);
    }

    public void setOnClickMoreCommentListener(OnClickMoreCommentListener onClickMoreCommentListener) {
        this.onClickMoreCommentListener = onClickMoreCommentListener;
    }

    public void setMoreComment(List<ForumDto.ForumCommentDto> commentDtos,ViewHolder holder){
        holder.commentAdapter.addData(commentDtos,2);
        holder.commentAdapter.notifyDataSetChanged();
        holder.tvMoreComment.setVisibility(View.GONE);
    }

    /**--------------------------查看更多评论--------------------------------------*/
    /**--------------------------查看点赞  和 评论--------------------------------------*/
    public interface OnClickLikeOrCommentListener{
        void clickLikeForum(ForumDto forumDto,int position);
        void clickCommentForum(ForumDto forumDto,int position);
    }

    public void setOnClickLikeOrCommentListener(OnClickLikeOrCommentListener onClickLikeOrCommentListener) {
        this.onClickLikeOrCommentListener = onClickLikeOrCommentListener;
    }
    /**--------------------------查看点赞  和 评论--------------------------------------*/

    public interface OnClickImageListener{
        void clickImages(List<String> pictureUrls);
    }

    public void setOnClickImageListener(OnClickImageListener onClickImageListener) {
        this.onClickImageListener = onClickImageListener;
    }
}
