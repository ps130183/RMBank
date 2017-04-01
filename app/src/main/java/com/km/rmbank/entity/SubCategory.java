package com.km.rmbank.entity;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.marshalchen.ultimaterecyclerview.expanx.SmartItem;
import com.marshalchen.ultimaterecyclerview.expanx.Util.easyTemplateChild;
import com.ps.androidlib.utils.MToast;

/**
 * Created by hesk on 16/7/15.
 */
public class SubCategory extends easyTemplateChild<SmartItem, TextView, RelativeLayout> {
    Context mContext;
    private CheckBox checkBox;
    public SubCategory(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
    }

    @Override
    protected void request_api(String[] n, String title) {
        super.request_api(n, title);
        MToast.showToast(mContext,title);
    }
}
