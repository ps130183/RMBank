package com.km.rmbank.module.home;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.km.rmbank.R;
import com.km.rmbank.adapter.GoodsListShoppingAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.module.rmshop.goods.GoodsActivity;
import com.ps.androidlib.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.View {

    @BindView(R.id.et_search)
    EditText etSearch;
    private String searchContent= "";

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    @Override
    protected int getContentView() {
        return R.layout.activity_search;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "";
    }

    @NonNull
    @Override
    protected List<CenterViewInterface> getCenterViews() {
        List<CenterViewInterface> centerViewInterfaces = new ArrayList<>();
        centerViewInterfaces.add(new SearchCenterView());
        return centerViewInterfaces;
    }

    @Override
    public SearchPresenter getmPresenter() {
        return new SearchPresenter(this);
    }

    @Override
    protected void onCreate() {

    }

    @Override
    public void initRecyclerview() {
        RVUtils.setLinearLayoutManage(mRecyclerview, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerview);
        final GoodsListShoppingAdapter adapter = new GoodsListShoppingAdapter(this);
        mRecyclerview.setAdapter(adapter);
        adapter.addLoadMore(mRecyclerview, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                if (adapter.getNextPage() > 1 && !TextUtils.isEmpty(searchContent)){
                    mPresenter.getSearchGoods(adapter.getNextPage(),searchContent);
                }
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
    public void showSearchResult(List<GoodsDto> goodsDtos, int pageNo) {
        GoodsListShoppingAdapter adapter = (GoodsListShoppingAdapter) mRecyclerview.getAdapter();
        adapter.addData(goodsDtos,pageNo);
    }

    class SearchCenterView implements CenterViewInterface{

        @Override
        public View getView() {
            return ViewUtils.getView(SearchActivity.this,R.layout.search);
        }

        @Override
        public void setViewWidget(View view) {

        }
    }

    @OnClick(R.id.btn_search)
    public void search(View view){
        GoodsListShoppingAdapter adapter = (GoodsListShoppingAdapter) mRecyclerview.getAdapter();
        adapter.clearAllData();
        searchContent = etSearch.getText().toString();
        if (TextUtils.isEmpty(searchContent)){
            showToast("请输入搜索内容");
            return;
        }
        mPresenter.getSearchGoods(adapter.getNextPage(),searchContent);
    }


}
