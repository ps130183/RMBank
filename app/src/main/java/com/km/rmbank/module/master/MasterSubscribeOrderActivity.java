package com.km.rmbank.module.master;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.MasterSubscribeOrderAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.MasterDto;
import com.km.rmbank.dto.MasterOrderDto;
import com.km.rmbank.utils.SwipeRefreshUtils;

import java.util.List;

import butterknife.BindView;

public class MasterSubscribeOrderActivity extends BaseActivity<MasterSubscribeOrderPresenter> implements MasterSubscribeOrderContract.View {

    @BindView(R.id.rv_master_and_me)
    RecyclerView rvMasterAndMe;

    @Override
    protected int getContentView() {
        return R.layout.activity_master_subscribe_order;
    }

    @Override
    protected String getTitleName() {
        return "我与大咖";
    }

    @Override
    public MasterSubscribeOrderPresenter getmPresenter() {
        return new MasterSubscribeOrderPresenter(this);
    }

    @Override
    protected void onCreate() {
        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swiper_refresh);
        initView();
    }

    private void initView(){
        RVUtils.setLinearLayoutManage(rvMasterAndMe, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvMasterAndMe);
        final MasterSubscribeOrderAdapter adapter = new MasterSubscribeOrderAdapter(this);
        rvMasterAndMe.setAdapter(adapter);
        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<MasterOrderDto>() {
            @Override
            public void onItemClick(MasterOrderDto itemData, int position) {
                mPresenter.getMasterInfo(itemData.getMacaId());
            }

        });
        adapter.addLoadMore(rvMasterAndMe, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getMasterSubscribeList(adapter.getNextPage());
            }
        });
        mPresenter.getMasterSubscribeList(1);

        SwipeRefreshUtils.initSwipeRefresh(mSwipeRefresh, new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getMasterSubscribeList(1);
            }
        });
    }

    @Override
    public void showMasterSubscribeList(List<MasterOrderDto> masterOrderDtos, int pageNo) {
        MasterSubscribeOrderAdapter adapter = (MasterSubscribeOrderAdapter) rvMasterAndMe.getAdapter();
        adapter.addData(masterOrderDtos,pageNo);
    }

    @Override
    public void showMasterInfo(MasterDto masterDto, String id) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("masterInfo",masterDto);
        bundle.putString("id",id);
        toNextActivity(MasterInfoActivity.class,bundle);
    }
}
