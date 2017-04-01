package com.km.rv_libs.base;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kamangkeji on 17/3/15.
 */

public interface ICell {

    /**
     * 回收资源
     *
     */
    public void releaseResource();

    /**
     * 创建viewHolder
     * @param parent
     * @param viewType
     * @return
     */
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * 数据绑定
     * @param holder
     * @param position
     */
    public void onBindViewHolder(BaseViewHolder holder, int position);

    /**
     * 获取viewType
     * @return
     */
    public int getItemViewType();

    public interface OnCellClickListener<T>{
        void cellClick(T mData, int position);
    }
}
