package com.km.rmbank.cell;

import com.km.rmbank.R;
import com.km.rv_libs.base.BaseCell;

/**
 * Created by kamangkeji on 17/5/12.
 */

public class HomeFloorFourCell extends BaseCell<String> {

    public HomeFloorFourCell(String mData, OnCellClickListener<String> onCellClickListener) {
        super(mData, R.layout.cell_home_floor_four, onCellClickListener);
    }

    @Override
    public int getItemViewType() {
        return 4;
    }
}
