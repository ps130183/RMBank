package com.km.rmbank.module.personal.active;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.ConvertInventoryAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.ActiveGoodsOrderListDto;

import java.util.List;

import butterknife.BindView;

public class ConvertInventoryActivity extends BaseActivity<ConvertInventoryPresenter> implements ConvertInventoryContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Override
    protected int getContentView() {
        return R.layout.activity_convert_inventory;
    }

    @Override
    protected String getTitleName() {
        return "兑换清单";
    }

    @Override
    public ConvertInventoryPresenter getmPresenter() {
        return new ConvertInventoryPresenter(this);
    }

    @Override
    protected void onCreate() {
        initRecyclerView();
    }

    private void initRecyclerView(){
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
//        RVUtils.addDivideItemForRv(mRecyclerView);
        RVUtils.addDivideItemForRv(mRecyclerView,RVUtils.DIVIDER_COLOR_DEFAULT,2);

        final ConvertInventoryAdapter adapter = new ConvertInventoryAdapter(this);
        mRecyclerView.setAdapter(adapter);

        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<ActiveGoodsOrderListDto>() {
            @Override
            public void onItemClick(ActiveGoodsOrderListDto itemData, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("orderDetail",itemData);
                toNextActivity(ActiveGoodsInfoActivity.class,bundle);
            }
        });

        adapter.addLoadMore(mRecyclerView, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getActiveGoodsOrderList(adapter.getNextPage());
            }
        });
        mPresenter.getActiveGoodsOrderList(1);
    }

    @Override
    public void showActiveGoodsOrderList(List<ActiveGoodsOrderListDto> activeGoodsOrderListDtos, int pageNo) {
        ConvertInventoryAdapter adapter = (ConvertInventoryAdapter) mRecyclerView.getAdapter();
        adapter.addData(activeGoodsOrderListDtos,pageNo);
    }
}
