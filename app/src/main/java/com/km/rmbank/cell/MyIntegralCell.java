package com.km.rmbank.cell;

import com.km.rmbank.R;
import com.km.rmbank.entity.AccountDetailsEntity;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;

/**
 * Created by kamangkeji on 17/3/18.
 */

public class MyIntegralCell extends BaseCell<AccountDetailsEntity> {

    public MyIntegralCell(AccountDetailsEntity mData) {
        super(mData, R.layout.item_rv_integral_details);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }
}
