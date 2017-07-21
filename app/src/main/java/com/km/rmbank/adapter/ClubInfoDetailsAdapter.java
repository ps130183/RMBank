package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.dto.ClubDto;
import com.ps.androidlib.utils.glide.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/7/7.
 */

public class ClubInfoDetailsAdapter extends BaseAdapter<ClubDto.ClubDetailBean> implements BaseAdapter.IAdapter<ClubInfoDetailsAdapter.ViewHolder> {

    public ClubInfoDetailsAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_club_info_details);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        ClubDto.ClubDetailBean detailBean = getItemData(position);
        GlideUtils.loadImageByFitWidth(holder.ivClubIntro,detailBean.getClubImage());
        holder.tvClubIntro.setText(detailBean.getClubContent());
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.iv_club_intro)
        ImageView ivClubIntro;
        @BindView(R.id.tv_club_intro)
        TextView tvClubIntro;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
