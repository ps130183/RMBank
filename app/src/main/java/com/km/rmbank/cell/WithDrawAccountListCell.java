package com.km.rmbank.cell;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.km.rmbank.R;
import com.km.rmbank.dto.WithDrawAccountDto;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;
import com.orhanobut.logger.Logger;

/**
 * Created by kamangkeji on 17/3/19.
 */

public class WithDrawAccountListCell extends BaseCell<WithDrawAccountDto> implements View.OnClickListener {

    public WithDrawAccountListCell(WithDrawAccountDto mData, OnCellClickListener<WithDrawAccountDto> onCellClickListener) {
        super(mData, R.layout.item_rv_withdraw_account, onCellClickListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
//        final CheckBox isDefault = holder.findView(R.id.cb_isdefault);
//        isDefault.setChecked(mData.isDefault());
//        isDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Logger.d(mData.toString() + "  " + isChecked);
////                mData.setDefault(isChecked);
//                onCellClickListener.cellClick(mData,isDefault.getId());
//            }
//        });

        holder.getTextView(R.id.tv_edit).setOnClickListener(this);
        holder.getTextView(R.id.tv_delete).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        onCellClickListener.cellClick(mData,v.getId());
    }
}
