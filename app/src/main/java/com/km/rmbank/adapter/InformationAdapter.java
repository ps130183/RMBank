package com.km.rmbank.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.dto.InformationDto;
import com.ps.androidlib.utils.BannerUtils;
import com.ps.androidlib.utils.glide.GlideUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/4/21.
 */

public class InformationAdapter extends BaseAdapter<InformationDto> implements BaseAdapter.IAdapter<InformationAdapter.ViewHolder>,
        BaseAdapter.IHeaderAdapter<InformationAdapter.HeaderViewHolder>{

    private List<Integer> bannerImages;
    private OnBannerCliclListener onBannerCliclListener;

    public InformationAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_infromation);
        setiAdapter(this);
        setmHeaderLayoutRes(R.layout.action_area_banner);
        setiHeaderAdapter(this);
        bannerImages = new ArrayList<>();
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        InformationDto informationDto = getItemData(position);
        holder.tvInformationTitle.setText(informationDto.getTitle());
        GlideUtils.loadImage(holder.ivInformation,informationDto.getAvatarUrl());
    }

    @Override
    public HeaderViewHolder createHeaderViewHolder(View view, int viewType) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void createHeaderView(HeaderViewHolder holder, int position) {
        BannerUtils.initBannerFromRes(holder.banner, bannerImages, new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (onBannerCliclListener != null){
                    onBannerCliclListener.clickBanner(position);
                }
            }
        });
    }

    class ViewHolder extends BaseViewHolder{
        @BindView(R.id.tv_information_title)
        TextView tvInformationTitle;
        @BindView(R.id.tv_page_view)
        TextView tvPageView;
        @BindView(R.id.iv_information)
        ImageView ivInformation;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    class HeaderViewHolder extends BaseHeaderViewHolder{
        @BindView(R.id.banner)
        Banner banner;
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setBannerImages(List<Integer> bannerImages) {
        this.bannerImages = bannerImages;
    }

    public interface OnBannerCliclListener{
        void clickBanner(int position);
    }

    public void setOnBannerCliclListener(OnBannerCliclListener onBannerCliclListener) {
        this.onBannerCliclListener = onBannerCliclListener;
    }
}
