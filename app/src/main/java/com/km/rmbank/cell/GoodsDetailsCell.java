package com.km.rmbank.cell;

import com.km.rmbank.R;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;
import com.ps.androidlib.utils.GlideUtils;

/**
 * Created by kamangkeji on 17/3/18.
 */

public class GoodsDetailsCell extends BaseCell<String> {

    public GoodsDetailsCell(String mData) {
        super(mData, R.layout.item_rv_goods_details);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        GlideUtils.loadImage(holder.getImageView(R.id.iv_goods_details),mData);
    }
}
