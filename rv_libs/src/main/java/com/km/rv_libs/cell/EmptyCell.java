package com.km.rv_libs.cell;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.km.rv_libs.R;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;

/**
 * Created by kamangkeji on 17/3/15.
 */

public class EmptyCell extends BaseCell<Integer> {

    private int emptyLayoutRes = R.layout.cell_empty;

    public EmptyCell(Integer mData) {
        super(mData);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mData > 0){
            emptyLayoutRes = mData;
        }
        return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(emptyLayoutRes,parent,false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType() {
        return Integer.MAX_VALUE - 1;
    }

    public void setEmptyLayoutRes(int emptyLayoutRes) {
        this.emptyLayoutRes = emptyLayoutRes;
    }
}
