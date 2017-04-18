package com.km.rmbank.module.actionarea.apply;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.ActionListAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.ActionDto;

import java.util.List;

import butterknife.BindView;

public class ActionListActivity extends BaseActivity<ActionListPresenter> implements ActionListContract.View {

    String loadUrl = "http://192.168.31.220:8080/Aiyg/app/html/activityList.html";
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    @Override
    protected int getContentView() {
        return R.layout.activity_apply_action;
    }

    @Override
    protected String getTitleName() {
        return "活动列表";
    }

    @Override
    public ActionListPresenter getmPresenter() {
        return new ActionListPresenter(this);
    }

    @Override
    protected void onCreate() {
    }

    @Override
    public void initRecyclerview() {
        RVUtils.setLinearLayoutManage(mRecyclerview, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerview);
        final ActionListAdapter adapter = new ActionListAdapter(this);
        mRecyclerview.setAdapter(adapter);
        adapter.addLoadMore(mRecyclerview, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getActionList(adapter.getNextPage());
            }
        });
        mPresenter.getActionList(adapter.getNextPage());

        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<ActionDto>() {

            @Override
            public void onItemClick(ActionDto itemData, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("actionDto",itemData);
                toNextActivity(ActionDetailActivity.class,bundle);
            }
        });
    }

    @Override
    public void showActionList(List<ActionDto> actionDtos,int pageNo) {
        ActionListAdapter adapter = (ActionListAdapter) mRecyclerview.getAdapter();
        adapter.addData(actionDtos,pageNo);
    }
}
