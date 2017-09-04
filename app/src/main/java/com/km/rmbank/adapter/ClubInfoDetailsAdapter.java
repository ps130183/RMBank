package com.km.rmbank.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.RVUtils;
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
        if (detailBean.getClubImageList() == null || detailBean.getClubImageList().size() == 0) {
            holder.rvImage.setVisibility(View.GONE);
        } else {
            holder.rvImage.setVisibility(View.VISIBLE);
            holder.adapter.addData(detailBean.getClubImageList());
        }
        holder.tvClubIntro.setText(detailBean.getClubContent());
    }

    class ViewHolder extends BaseViewHolder {

        @BindView(R.id.rv_image)
        RecyclerView rvImage;
        ImageTextAdapter adapter;


        @BindView(R.id.tv_club_intro)
        TextView tvClubIntro;

        public ViewHolder(View itemView) {
            super(itemView);
            initRv();
        }

        private void initRv() {
            RVUtils.setLinearLayoutManage(rvImage, LinearLayoutManager.VERTICAL);
            RVUtils.addDivideItemForRv(rvImage, RVUtils.DIVIDER_COLOR_WHITE);
            adapter = new ImageTextAdapter(mContext);
            rvImage.setAdapter(adapter);
        }
    }
}
