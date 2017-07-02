package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.entity.HomePersonalFuntionEntity;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.glide.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/7/1.
 */

public class HomePersonalFuntionAdapter extends BaseAdapter<HomePersonalFuntionEntity> implements BaseAdapter.IAdapter<HomePersonalFuntionAdapter.ViewHolder> {

    private int imageWidth = AppUtils.dip2px(mContext,50);
    private float fontSize = AppUtils.dip2px(mContext,14);

    public HomePersonalFuntionAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_home_personal_funtion);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        HomePersonalFuntionEntity entity = getItemData(position);
        holder.tvFunctionName.setText(entity.getFuntionName());
        if (entity.getFuntionImgRes() > 0){
            GlideUtils.loadImage(holder.ivFunctionRes,entity.getFuntionImgRes());
        }
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.iv_function_res)
        ImageView ivFunctionRes;
        @BindView(R.id.tv_function_name)
        TextView tvFunctionName;

        public ViewHolder(View itemView) {
            super(itemView);
            ivFunctionRes.getLayoutParams().width = imageWidth;
            ivFunctionRes.getLayoutParams().height = imageWidth;

            tvFunctionName.setTextSize(fontSize);
        }
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }
}
