package com.km.rmbank.cell;

import com.km.rmbank.R;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;

import java.util.List;

/**
 * Created by kamangkeji on 17/3/17.
 */

public class DefaultCell extends BaseCell<String> {
    public DefaultCell(String mData) {
        super(mData, R.layout.item_rv_default);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

    }

    @Override
    public int getItemViewType() {
        return Integer.MAX_VALUE;
    }
}
