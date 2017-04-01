package com.km.rv_libs;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.km.rv_libs.base.BaseAdapter;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;
import com.km.rv_libs.base.ICell;
import com.km.rv_libs.cell.EmptyCell;
import com.km.rv_libs.cell.LoadMoreCell;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamangkeji on 17/3/15.
 */

public class TemplateAdapter extends BaseAdapter {

    public TemplateAdapter() {

    }

    @Override
    protected void onViewHolderBound(BaseViewHolder holder, int position) {

    }

    /**
     * 设置空数据情况
     * @param emptyLayoutRes
     */
    public void setEmptyLayoutRes(@LayoutRes int emptyLayoutRes){
        emptyCell.setEmptyLayoutRes(emptyLayoutRes);
    }


}
