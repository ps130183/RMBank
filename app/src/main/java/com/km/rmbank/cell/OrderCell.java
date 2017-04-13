package com.km.rmbank.cell;

import com.km.rmbank.R;
import com.km.rmbank.dto.OrderDto;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;

/**
 * Created by kamangkeji on 17/3/24.
 */

public class OrderCell extends BaseCell<OrderDto> {

    public OrderCell(OrderDto mData, OnCellClickListener<OrderDto> onCellClickListener) {
        super(mData, R.layout.item_rv_order_list, onCellClickListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

    }
}
