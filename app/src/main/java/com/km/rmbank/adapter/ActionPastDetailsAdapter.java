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
 * Created by kamangkeji on 17/7/18.
 */

public class ActionPastDetailsAdapter extends BaseAdapter<ActionPastDto.DynamicBean> implements BaseAdapter.IAdapter<ActionPastDetailsAdapter.ViewHolder> {

    public ActionPastDetailsAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_action_past_details);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        ActionPastDto.DynamicBean bean = getItemData(position);
        GlideUtils.loadImage(holder.ivActionImg,bean.getDynamicImage());
        holder.tvContent.setText(bean.getDynamicImageContent());
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.iv_action_img)
        ImageView ivActionImg;
        @BindView(R.id.tv_content)
        TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
