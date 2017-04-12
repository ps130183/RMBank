package com.km.rmbank.module.personal.order;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.MyOrderAdapter;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.entity.OrderEntity;

import java.util.List;

import butterknife.BindView;

public class MyOrderFragment extends BaseFragment<OrderPresenter> implements OrderContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private String finishOrder;
    public static MyOrderFragment newInstance(Bundle bundle) {
        MyOrderFragment fragment = new MyOrderFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_my_order;
    }

    @Override
    public OrderPresenter getmPresenter() {
        return new OrderPresenter(this);
    }

    @Override
    protected void createView() {
        finishOrder = getArguments().getString("finishOrder");
        initRecyclerView();
    }


    private void initRecyclerView(){
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView,RVUtils.DIVIDER_COLOR_DEFAULT,2);
        final MyOrderAdapter adapter = new MyOrderAdapter(getContext());
        mRecyclerView.setAdapter(adapter);
        adapter.addLoadMore(mRecyclerView, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.loadOrder(adapter.getNextPage(),finishOrder);
            }
        });
    }

    @Override
    public void showOrderList(List<OrderEntity> orderEntities, int page) {
        MyOrderAdapter adapter = (MyOrderAdapter) mRecyclerView.getAdapter();
        adapter.addData(orderEntities,page);
    }
}
