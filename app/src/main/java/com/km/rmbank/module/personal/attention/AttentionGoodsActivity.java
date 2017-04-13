package com.km.rmbank.module.personal.attention;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.GoodsListShoppingAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.module.rmshop.goods.GoodsActivity;

import java.util.List;

import butterknife.BindView;

public class AttentionGoodsActivity extends BaseActivity<AttentionGoodsPresenter> implements AttentionGoodsContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private GoodsListShoppingAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_attention_goods;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "我的关注";
    }

    @Override
    public AttentionGoodsPresenter getmPresenter() {
        return new AttentionGoodsPresenter(this);
    }

    @Override
    protected void onCreate() {

    }

    @Override
    public void initAttentionGoods() {
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView);
        adapter = new GoodsListShoppingAdapter(this);
        mRecyclerView.setAdapter(adapter);
        adapter.addLoadMore(mRecyclerView, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getAttentionGoods(adapter.getNextPage());
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
    public void getAttentionGoodsSuccess(List<GoodsDto> goodsDtos, int pageNo) {
        adapter.addData(goodsDtos,pageNo);
    }
}
