package com.km.rmbank.cell;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.ViewUtils;

import java.util.List;

/**
 * Created by kamangkeji on 17/3/15.
 */

public class ActionCell extends BaseCell<String> {


    public ActionCell(String mData, int layoutRes) {
        super(mData, layoutRes);
        setOnCellClickListener(new OnCellClickListener<String>() {
            @Override
            public void cellClick(String mData, int position) {
                Logger.d(mData);
            }
        });
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder,position);
        TextView name = holder.findView(R.id.tv_action_title);
        name.setText(mData);
        Logger.d(name + "\nactionCell position = " + position);
    }

}
