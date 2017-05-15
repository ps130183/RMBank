package com.km.rmbank.cell;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.google.zxing.oned.rss.FinderPattern;
import com.km.rmbank.R;
import com.km.rmbank.module.home.SearchActivity;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.glide.GlideUtils;
import com.rey.material.widget.FrameLayout;

/**
 * Created by kamangkeji on 17/5/12.
 */

public class HomeHeaderCell extends BaseCell<String> {

    public HomeHeaderCell(String mData, OnCellClickListener<String> onCellClickListener) {
        super(mData, R.layout.cell_home_header, onCellClickListener);
    }

    @Override
    public int getItemViewType() {
        return Integer.MAX_VALUE - 1;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ImageView flContent = holder.findView(R.id.iv_home_header);
        GlideUtils.loadImageBlur(flContent,R.mipmap.ic_home_header);
        final Context context = flContent.getContext();

        int windowWidth = AppUtils.getCurWindowWidth(context);
        flContent.getLayoutParams().height = windowWidth / 4 * 3;

        holder.findView(R.id.rl_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,SearchActivity.class));
//                toNextActivity(SearchActivity.class);
            }
        });
        holder.findView(R.id.tv_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,SearchActivity.class));
            }
        });
    }
}
