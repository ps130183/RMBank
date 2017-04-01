package com.km.rmbank.module.personal.order;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.cell.OrderCell;
import com.km.rmbank.entity.OrderEntity;
import com.km.rv_libs.TemplateAdapter;
import com.km.rv_libs.base.ICell;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OrderUnFinishFragment extends BaseFragment<OrderPresenter> implements OrderContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    public static OrderUnFinishFragment newInstance(Bundle bundle) {
        OrderUnFinishFragment fragment = new OrderUnFinishFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_order_un_finish;
    }

    @Override
    public OrderPresenter getmPresenter() {
        return new OrderPresenter(this, (BaseActivity) getActivity());
    }

    @Override
    protected void createView() {
        initRecyclerView();
        mPresenter.loadOrder(1,false);
    }


    private void initRecyclerView(){
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView,RVUtils.DIVIDER_COLOR_DEFAULT,2);
        TemplateAdapter adapter = new TemplateAdapter();
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showOrderList(List<OrderEntity> orderEntities, int page) {
        TemplateAdapter adapter = (TemplateAdapter) mRecyclerView.getAdapter();
        List<ICell> iCells = new ArrayList<>();
        for (OrderEntity entity : orderEntities){
            iCells.add(new OrderCell(entity,null));
        }
        adapter.addData(iCells);
    }
}
