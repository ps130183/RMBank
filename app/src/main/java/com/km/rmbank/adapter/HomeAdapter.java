package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.dto.HomeRecommendDto;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.glide.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/3/14.
 */

public class HomeAdapter extends BaseAdapter<HomeRecommendDto> implements BaseAdapter.IAdapter<HomeAdapter.ViewHolder> {

    private OnClickGoodsListener onClickGoodsListener;

    public HomeAdapter(Context mContext) {
        super(mContext, R.layout.item_rc_home);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        final HomeRecommendDto homeRecommendDto = getItemData(position);
        holder.tvTitle.setText(homeRecommendDto.getRecommendName());
        GlideUtils.loadImageCenterCrop(holder.imageHome1,homeRecommendDto.getProductReconmmendList().get(0).getThumbnailUrl());
        GlideUtils.loadImageCenterCrop(holder.imageHome2,homeRecommendDto.getProductReconmmendList().get(1).getThumbnailUrl());
        GlideUtils.loadImageCenterCrop(holder.imageHome3,homeRecommendDto.getProductReconmmendList().get(2).getThumbnailUrl());
        holder.imageHome1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickGoodsListener != null){
                    onClickGoodsListener.clickGoods(homeRecommendDto.getProductReconmmendList().get(0));
                }
            }
        });
        holder.imageHome2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickGoodsListener != null){
                    onClickGoodsListener.clickGoods(homeRecommendDto.getProductReconmmendList().get(1));
                }
            }
        });
        holder.imageHome3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickGoodsListener != null){
                    onClickGoodsListener.clickGoods(homeRecommendDto.getProductReconmmendList().get(2));
                }
            }
        });
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.image_home1)
        ImageView imageHome1;
        @BindView(R.id.image_home2)
        ImageView imageHome2;
        @BindView(R.id.image_home3)
        ImageView imageHome3;

        public ViewHolder(View itemView) {
            super(itemView);
            initImgaeViewSize();
        }

        private void initImgaeViewSize(){
            int curWidth = AppUtils.getCurWindowWidth(mContext);
            int realWidth = curWidth - AppUtils.dip2px(mContext,32);
            int realHeight = (realWidth / 2) - AppUtils.dip2px(mContext,8);
            imageHome1.getLayoutParams().height = realWidth / 2;
            imageHome2.getLayoutParams().height = realHeight / 2;
            imageHome3.getLayoutParams().height = realHeight / 2;
        }
    }

    public interface OnClickGoodsListener{
        void clickGoods(HomeRecommendDto.ProductReconmmendListBean bean);
    }

    public void setOnClickGoodsListener(OnClickGoodsListener onClickGoodsListener) {
        this.onClickGoodsListener = onClickGoodsListener;
    }
}
