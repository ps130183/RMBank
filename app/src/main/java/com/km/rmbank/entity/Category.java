package com.km.rmbank.entity;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.expanx.SmartItem;
import com.marshalchen.ultimaterecyclerview.expanx.Util.easyTemplateParent;
import com.ps.androidlib.utils.MToast;

/**
 * Created by hesk on 16/7/15.
 */
public class Category extends easyTemplateParent<SmartItem, RelativeLayout, TextView> {

    Context mContext;

    public Category(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
    }

    @Override
    public int openDegree() {
        expand.setVisibility(View.VISIBLE);
        return 0;
    }

    @Override
    public int closeDegree() {
        expand.setVisibility(View.GONE);
        return super.closeDegree();
    }

    @Override
    protected void updateCountNumber(String text) {
        super.updateCountNumber(text);

    }

    @Override
    public void onParentItemClick(String path) {
        super.onParentItemClick(path);

    }



    interface OnUpdateCountNumberListener{
        void updateCountHint(String text);
    }
}
