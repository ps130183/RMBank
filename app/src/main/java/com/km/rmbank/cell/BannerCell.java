package com.km.rmbank.cell;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.km.rmbank.R;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.BannerUtils;
import com.ps.androidlib.utils.MToast;
import com.ps.androidlib.utils.ViewUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;

/**
 * Created by kamangkeji on 17/3/15.
 */

public class BannerCell extends BaseCell<List<Integer>> {

    public BannerCell(List<Integer> mData, int layoutRes) {
        super(mData, layoutRes);
        setOnCellClickListener(new OnCellClickListener<List<Integer>>() {
            @Override
            public void cellClick(List<Integer> mdata, int position) {
                Logger.d(mdata.get(position) + "\n  position " + position);
            }
        });
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder,position);
        final Banner banner = holder.findView(R.id.banner);
        BannerUtils.initBanner(banner, mData, new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
//                MToast.showToast();
                onCellClickListener.cellClick(mData,position);
            }
        });
    }

    @Override
    public int getItemViewType() {
        return 2;
    }

}
