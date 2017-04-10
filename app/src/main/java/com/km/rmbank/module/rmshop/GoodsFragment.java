package com.km.rmbank.module.rmshop;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.GoodsListShoppingAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.module.rmshop.goods.GoodsActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/3/14.
 */

public class GoodsFragment extends BaseFragment<GoodsPresenter> implements GoodsContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView rvGoods;

    String tabname;
    int tabid;

    public static GoodsFragment newInstance(Bundle bundle) {
        GoodsFragment fragment = new GoodsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.recyclerview;
    }

    @Override
    public GoodsPresenter getmPresenter() {
        return new GoodsPresenter(this);
    }

    @Override
    protected void createView() {
        Bundle bundle = getArguments();
        tabname = bundle.getString("tabname");
        tabid = bundle.getInt("tabid");
    }



    @Override
    public void onResume() {
        super.onResume();
//        Logger.d("goodsFragment   onResume \n" + " tabname = " + tabname + " tabid = " + tabid);
    }

    @Override
    public void initGoodsList() {
        RVUtils.setLinearLayoutManage(rvGoods, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvGoods);
        final GoodsListShoppingAdapter adapter = new GoodsListShoppingAdapter(getContext());
        rvGoods.setAdapter(adapter);
        adapter.addLoadMore(rvGoods, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                Logger.d(GoodsFragment.this.tabname);
                mPresenter.loadGoodsList(adapter.getNextPage());
            }
        });
        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<GoodsDto>() {
            @Override
            public void onItemClick(GoodsDto itemData, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("productNo",itemData.getProductNo());
                toNextActivity(GoodsActivity.class,bundle);
            }

        });
    }

    @Override
    public void showGoodsList(List<GoodsDto> goodsDtos, int pageNo) {
        GoodsListShoppingAdapter adapter = (GoodsListShoppingAdapter) rvGoods.getAdapter();
        adapter.addData(goodsDtos,pageNo);
    }
}
