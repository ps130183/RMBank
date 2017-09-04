package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.glide.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/8/11.
 */

public class HomeForumImgAdapter extends BaseAdapter<String> implements BaseAdapter.IAdapter<HomeForumImgAdapter.ViewHolde> {

    public HomeForumImgAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_home_forum_img_content);
        setiAdapter(this);
    }

    @Override
    public ViewHolde createViewHolder(View view, int viewType) {
        return new ViewHolde(view);
    }

    @Override
    public void createView(ViewHolde holder, int position) {
        String imgUrl = getItemData(position);
        GlideUtils.loadImage(holder.ivForumImg,imgUrl);
    }

    class ViewHolde extends BaseViewHolder{

        @BindView(R.id.iv_forum_img)
        ImageView ivForumImg;

        public ViewHolde(View itemView) {
            super(itemView);
        }
    }
}
