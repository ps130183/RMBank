package com.km.rmbank.module.personal.goodsmanager;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.util.Attributes;
import com.km.rmbank.R;
import com.km.rmbank.adapter.RepleaseGoodsListAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.GoodsDto;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.DialogUtils;

import java.util.List;

import butterknife.BindView;

public class RepleaseGoodsListFragment extends BaseFragment<RepleaseGoodsListPresenter> implements RepleaseGoodsListContract.View, RepleaseGoodsListAdapter.onClickSoldOutListener {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    public static RepleaseGoodsListFragment newInstance(Bundle bundle) {
        RepleaseGoodsListFragment fragment = new RepleaseGoodsListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getContentView() {
        return R.layout.fragment_replease_goods_list;
    }

    @Override
    public RepleaseGoodsListPresenter getmPresenter() {
        return new RepleaseGoodsListPresenter(this, (BaseActivity) getActivity());
    }

    @Override
    protected void createView() {
//        initRecyclerView();
//        mPresenter.loadRepleaseGoods(1);
    }

    @Override
    public void initRecyclerView() {
        Logger.d("initRecyclerView");
        RVUtils.setLinearLayoutManage(mRecyclerview, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerview,RVUtils.DIVIDER_COLOR_ACCOUNT_DETAILS,2);
        RepleaseGoodsListAdapter adapter = new RepleaseGoodsListAdapter(getContext());
        adapter.setMode(Attributes.Mode.Single);
        mRecyclerview.setAdapter(adapter);

        adapter.addOnClickSoldOutListener(this);
    }

    @Override
    public void showRepleaseGoods(List<GoodsDto> goodsEntities) {
        RepleaseGoodsListAdapter adapter = (RepleaseGoodsListAdapter) mRecyclerview.getAdapter();
        adapter.addData(goodsEntities);
    }

    @Override
    public void clickSoldOut(GoodsDto entity, final int position, final SwipeLayout mSwipeLayout) {
        DialogUtils.showDefaultAlertDialog("是否要下架该产品？", new DialogUtils.ClickListener() {
            @Override
            public void clickConfirm() {
                showToast("下架");
                mSwipeLayout.close(true);
            }
        });
    }
}
