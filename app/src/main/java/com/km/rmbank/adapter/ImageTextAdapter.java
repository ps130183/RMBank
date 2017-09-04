package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.ps.androidlib.utils.glide.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/8/30.
 */

public class ImageTextAdapter extends BaseAdapter<String> implements BaseAdapter.IAdapter<ImageTextAdapter.ViewHolder> {

    public ImageTextAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_image_text);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        GlideUtils.loadImageByFitWidth(holder.ivImage,getItemData(position));
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.iv_image)
        ImageView ivImage;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
