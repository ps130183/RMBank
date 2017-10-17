package com.km.rmbank.module.personal.active;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.GoodsDetailsAdapter;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/3/17.
 */

public class ActiveGoodsDetailsFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    public static ActiveGoodsDetailsFragment newInstance(Bundle bundle) {
        ActiveGoodsDetailsFragment fragment = new ActiveGoodsDetailsFragment();
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
