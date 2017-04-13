package com.km.rmbank.module.rmshop.goods;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.km.rmbank.R;
import com.km.rmbank.adapter.GoodsEvluateAdapter;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.EvaluateDto;

import java.util.List;

import butterknife.BindView;

public class GoodsEvaluateFragment extends BaseFragment<GoodsEvaluatePresenter> implements GoodsEvaluateContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    public static GoodsEvaluateFragment newInstance(Bundle bundle) {
        GoodsEvaluateFragment fragment = new GoodsEvaluateFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_goods_evaluate;
    }

    @Override
    public GoodsEvaluatePresenter getmPresenter() {
        return new GoodsEvaluatePresenter(this);
    }

    @Override
    protected void createView() {

    }

    @Override
    public void initRecyclerview() {
        RVUtils.setLinearLayoutManage(mRecyclerview, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerview);
        GoodsEvluateAdapter adapter = new GoodsEvluateAdapter(getContext());
        mRecyclerview.setAdapter(adapter);
    }

    @Override
    public void showUserEvaluate(List<EvaluateDto> evaluateDtos, int pageNo) {
        GoodsEvluateAdapter adapter = (GoodsEvluateAdapter) mRecyclerview.getAdapter();
        adapter.addData(evaluateDtos,pageNo);
    }
}
