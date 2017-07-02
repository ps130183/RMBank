package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.dto.ClubDto;
import com.ps.androidlib.utils.glide.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/7/1.
 */

public class HomeClubTabAdapter extends BaseAdapter<ClubDto> implements BaseAdapter.IAdapter<HomeClubTabAdapter.ViewHolder> {

    public HomeClubTabAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_home_club_tab);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        ClubDto clubDto = getItemData(position);
        GlideUtils.loadCircleImage(holder.ivClubLogo,clubDto.getClubLogo());
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.iv_club_logo)
        ImageView ivClubLogo;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
