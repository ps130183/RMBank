package com.km.rmbank.module.personal.goodsmanager;

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
import com.km.rmbank.adapter.MyOrderAdapter;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.dto.OrderDto;
import com.km.rmbank.module.personal.order.detail.OrderDetailsActivity;

import java.util.List;

import butterknife.BindView;

public class OrderManagerFragment extends BaseFragment<OrderManagerPresenter> implements OrderManagerContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    public static OrderManagerFragment newInstance(Bundle args) {
        OrderManagerFragment fragment = new OrderManagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_order_manager;
    }

    @Override
    public OrderManagerPresenter getmPresenter() {
        return new OrderManagerPresenter(this);
    }

    @Override
    protected void createView() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getSellOrder(1);
    }

    @Override
    public void initRecyclerView() {
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView);
        final MyOrderAdapter adapter = new MyOrderAdapter(getContext());
        adapter.setUser(false);
        mRecyclerView.setAdapter(adapter);
        adapter.addLoadMore(mRecyclerView, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getSellOrder(adapter.getNextPage());
            }
        });
        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<OrderDto>() {
            @Override
            public void onItemClick(OrderDto itemData, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("orderDto",itemData);
                toNextActivity(OrderDetailsActivity.class,bundle);
            }
        });
    }

    @Override
    public void getSellOrderSuccess(List<OrderDto> goodsDtos, int pageNo) {
        MyOrderAdapter adapter = (MyOrderAdapter) mRecyclerView.getAdapter();
        adapter.addData(goodsDtos,pageNo);
    }
}
