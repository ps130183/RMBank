package com.km.rmbank.module.personal.order;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.MyOrderAdapter;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.OrderDto;

import java.util.List;

import butterknife.BindView;

public class OrderFinishedFragment extends BaseFragment<OrderPresenter> implements OrderContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    public static OrderFinishedFragment newInstance(Bundle bundle) {
        OrderFinishedFragment fragment = new OrderFinishedFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_order_finished;
    }


    @Override
    public OrderPresenter getmPresenter() {
        return new OrderPresenter(this);
    }

    @Override
    protected void createView() {
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
                mPresenter.loadOrder(adapter.getNextPage(),"4");
            }
        });
    }

    @Override
    public void showOrderList(List<OrderDto> orderEntities, int page) {
        MyOrderAdapter adapter = (MyOrderAdapter) mRecyclerView.getAdapter();
        adapter.addData(orderEntities,page);
    }
}
