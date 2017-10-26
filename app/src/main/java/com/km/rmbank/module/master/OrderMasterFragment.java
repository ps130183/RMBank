package com.km.rmbank.module.master;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.OrderMasterAdapter;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.MasterBannerDto;
import com.km.rmbank.dto.MasterDto;
import com.km.rmbank.utils.SwipeRefreshUtils;
import com.ps.androidlib.utils.BannerUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderMasterFragment extends BaseFragment<OrderMasterPresenter> implements OrderMasterContract.View {

    @BindView(R.id.title)
    TextView tvTitle;


    @BindView(R.id.rv_master)
    RecyclerView rvMaster;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.banner)
    Banner banner;


    public static OrderMasterFragment newInstance(Bundle bundle) {
        OrderMasterFragment fragment = new OrderMasterFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_order_master;
    }

    @Override
    public OrderMasterPresenter getmPresenter() {
        return new OrderMasterPresenter(this);
    }

    @Override
    protected void createView() {
        tvTitle.setText("遇见大咖");

        initRvMaster();
    }

    private void initRvMaster() {
        RVUtils.setLinearLayoutManage(rvMaster, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvMaster);
        final OrderMasterAdapter adapter = new OrderMasterAdapter(getActivity());
        rvMaster.setAdapter(adapter);

        adapter.addLoadMore(rvMaster, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getMasterList(adapter.getNextPage());
            }
        });

        SwipeRefreshUtils.initSwipeRefresh(mSwipeRefresh, new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getMasterList(1);
                mPresenter.getMasterBannerList();
            }
        });

        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<MasterDto>() {
            @Override
            public void onItemClick(MasterDto itemData, int position) {
                showMasterInfo(itemData,itemData.getId());
            }
        });

        mPresenter.getMasterList(1);
        mPresenter.getMasterBannerList();
    }


    @Override
    public void showMastersInfo(List<MasterDto> masterDtos, int pageNo) {
        OrderMasterAdapter adapter = (OrderMasterAdapter) rvMaster.getAdapter();
        adapter.addData(masterDtos,pageNo);
    }

    @Override
    public void showMasterBannerList(final List<MasterBannerDto> bannerDtos) {
        final List<String> bannerList = new ArrayList<>();
        for (MasterBannerDto bannerDto : bannerDtos){
            bannerList.add(bannerDto.getRepresentativeImag());
        }
        BannerUtils.initBannerFromUrl(banner, bannerList, new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                mPresenter.getMasterInfo(bannerDtos.get(position).getId());
            }
        });
    }

    @Override
    public void showMasterInfo(MasterDto masterDto,String id) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("masterInfo",masterDto);
        bundle.putString("id",id);
        toNextActivity(MasterInfoActivity.class,bundle);
    }
}
