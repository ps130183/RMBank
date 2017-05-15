package com.km.rmbank.cell;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.km.rmbank.R;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;
import com.ps.androidlib.utils.AppUtils;

/**
 * Created by kamangkeji on 17/5/11.
 */

public class HomeFloorOneCell extends BaseCell<String> {

    public HomeFloorOneCell(String mData, OnCellClickListener<String> onCellClickListener) {
        super(mData, R.layout.cell_home_floor_one, onCellClickListener);
    }

    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        //计算图片高度
        ImageView ivContent = holder.findView(R.id.iv_content);
        Context context = ivContent.getContext();

        int windowWidth = AppUtils.getCurWindowWidth(context);
        int ivHeight = windowWidth / 317 * 107;
        ivContent.getLayoutParams().height = ivHeight;
    }


}
