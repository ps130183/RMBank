package com.km.rmbank.module.rmshop.goods;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.GoodsDetailsAdapter;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.cell.GoodsDetailsCell;
import com.km.rv_libs.TemplateAdapter;
import com.km.rv_libs.base.ICell;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/3/17.
 */

public class GoodsDetailsFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    private int[] bannerRes = {R.mipmap.timg,R.mipmap.timg,R.mipmap.timg,R.mipmap.timg,R.mipmap.timg};

    public static GoodsDetailsFragment newInstance(Bundle bundle) {
        GoodsDetailsFragment fragment = new GoodsDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_goods_details;
    }

    @Override
    protected void createView() {
        List<String> goodsDetails = getArguments().getStringArrayList("goodsDetails");
        initRecyclerView(goodsDetails);
    }

    private void initRecyclerView(List<String> goodsDetails){

        RVUtils.setLinearLayoutManage(mRecyclerview, LinearLayoutManager.VERTICAL);
//        TemplateAdapter adapter = new TemplateAdapter();
        GoodsDetailsAdapter adapter = new GoodsDetailsAdapter(getContext());
        mRecyclerview.setAdapter(adapter);
//        adapter.addData(iCells);
        adapter.addData(goodsDetails);
        Logger.d(adapter.getAllData().toString());
    }
}
