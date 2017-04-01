package com.km.rv_libs.cell;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.km.rv_libs.R;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;

/**
 * Created by kamangkeji on 17/3/16.
 */

public class LoadMoreCell extends BaseCell {

    public LoadMoreCell(Object mData) {
        super(mData);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_load_more,parent,false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType() {
        return Integer.MAX_VALUE - 2;
    }
}
