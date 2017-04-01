package com.km.rv_libs.base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by kamangkeji on 17/3/15.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> views;
    private View mItemView;
    public BaseViewHolder(View itemView) {
        super(itemView);
        views = new SparseArray<>();
        mItemView = itemView;

    }

    /**
     * 获取ItemView
     * @return
     */
    public View getItemView() {
        return mItemView;
    }

    public View getView(int resId) {
        return findView(resId);
    }

    public TextView getTextView(int resId){
        return findView(resId);
    }

    public ImageView getImageView(int resId){
        return findView(resId);
    }

    public Button getButton(int resId){
        return findView(resId);
    }

    @SuppressWarnings("unchecked")
    public  <V extends View> V findView(int viewId){
        View view = views.get(viewId);
        if(view == null){
            view = mItemView.findViewById(viewId);
            views.put(viewId,view);
        }
        return (V) view;
    }

    public void setText(int resId,CharSequence text){
        getTextView(resId).setText(text);
    }

    public void setText(int resId,int strId){
        getTextView(resId).setText(strId);
    }
}
