package com.km.rmbank.module.personal.active;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.adapter.ConvertActiveGoodsAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.ActiveGoodsDto;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ConvertActiveGoodsListActivity extends BaseActivity<ConvertActiveGoodsListPresenter> implements ConvertActiveGoodsListContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Override
    protected int getContentView() {
        return R.layout.activity_convert_active_goods_list;
    }

    @Override
    protected String getTitleName() {
        return "兑换商品";
    }

    @Override
    public ConvertActiveGoodsListPresenter getmPresenter() {
        return new ConvertActiveGoodsListPresenter(this);
    }

    @Override
    protected void onCreate() {
        setRightBtnClick("兑换清单", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toNextActivity(ConvertInventoryActivity.class);
            }
        });
        initRecyclerView();
    }

    private void initRecyclerView(){
        RVUtils.setGridLayoutManage(mRecyclerView,2);
        RVUtils.addDivideItemForRv(mRecyclerView,RVUtils.DIVIDER_COLOR_DEFAULT, GridLayoutManager.VERTICAL,2);
        RVUtils.addDivideItemForRv(mRecyclerView,RVUtils.DIVIDER_COLOR_DEFAULT, GridLayoutManager.HORIZONTAL,2);

        final ConvertActiveGoodsAdapter adapter = new ConvertActiveGoodsAdapter(this);
        mRecyclerView.setAdapter(adapter);

        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<ActiveGoodsDto>() {
            @Override
            public void onItemClick(ActiveGoodsDto itemData, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("productNo",itemData.getProductNo());
                toNextActivity(ActiveGoodsDetailActivity.class,bundle);
            }

        });

        adapter.addLoadMore(mRecyclerView, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getConvertActiveGoodsList(adapter.getNextPage());
            }
        });
        mPresenter.getConvertActiveGoodsList(1);
//        adapter.addData(activeGoodsDtos);
    }

    @Override
    public void showConvertActiveGoodsList(List<ActiveGoodsDto> activeGoodsDtos, int pageNo) {
        ConvertActiveGoodsAdapter adapter = (ConvertActiveGoodsAdapter) mRecyclerView.getAdapter();
        adapter.addData(activeGoodsDtos,pageNo);
    }
}
