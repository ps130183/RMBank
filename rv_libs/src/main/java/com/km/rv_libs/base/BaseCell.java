package com.km.rv_libs.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import java.security.PolicySpi;

/**
 * Created by kamangkeji on 17/3/15.
 */

public abstract class BaseCell<T> implements ICell {

    public T mData;
    protected int layoutRes;

    protected OnCellClickListener<T> onCellClickListener;

    public BaseCell(T mData) {
        this.mData = mData;
    }

    public BaseCell(T mData, int layoutRes,OnCellClickListener<T> onCellClickListener) {
        this.onCellClickListener = onCellClickListener;
        this.mData = mData;
        this.layoutRes = layoutRes;
    }

    public BaseCell(T mData, int layoutRes) {
        this.mData = mData;
        this.layoutRes = layoutRes;
    }

    @Override
    public void releaseResource() {

    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutRes <= 0){
            throw new RuntimeException("请设置Cell的布局文件");
        }
//        Logger.d(this.toString() + "\n  " + layoutRes);

        return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutRes,parent,false));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCellClickListener != null){
                    onCellClickListener.cellClick(mData,position);
                }
            }
        });
    }

    @Override
    public int getItemViewType() {
        return 0;
    }

    public void setOnCellClickListener(OnCellClickListener onCellClickListener) {
        this.onCellClickListener = onCellClickListener;
    }

    public void setmData(T mData) {
        this.mData = mData;

    }
}
