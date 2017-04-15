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
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.module.payment.PaymentActivity;
import com.km.rmbank.module.personal.order.detail.OrderDetailsActivity;

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

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadOrder(1,finishOrder);
    }

    private void initRecyclerView(){
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView,RVUtils.DIVIDER_COLOR_DEFAULT,2);
        final MyOrderAdapter adapter = new MyOrderAdapter(getContext());
        mRecyclerView.setAdapter(adapter);
        adapter.addLoadMore(mRecyclerView, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                if (adapter.getNextPage() > 1 ){
                    mPresenter.loadOrder(adapter.getNextPage(),finishOrder);
                }
            }
        });
        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<OrderDto>() {
            @Override
            public void onItemClick(OrderDto itemData, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("orderDto",itemData);
                bundle.putBoolean("isUser",true);
                toNextActivity(OrderDetailsActivity.class,bundle);
            }

        });

        adapter.setOnClickBtnActionListener(new MyOrderAdapter.onClickBtnActionListener() {
            @Override
            public void clickBtnAction(OrderDto orderDto,int status) {
                switch (status){
                    case 1://去付款
                        mPresenter.getPayOrder(orderDto);
                        break;
                    case 2:
                    case 3://确认收货

                        break;
                }
            }
        });
    }

    @Override
    public void showOrderList(List<OrderDto> orderEntities, int page) {
        MyOrderAdapter adapter = (MyOrderAdapter) mRecyclerView.getAdapter();
        adapter.addData(orderEntities,page);
    }

    @Override
    public void getPayOrderSuccess(PayOrderDto payOrderDto) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("payOrderDto",payOrderDto);
        toNextActivity(PaymentActivity.class,bundle);
    }
}
