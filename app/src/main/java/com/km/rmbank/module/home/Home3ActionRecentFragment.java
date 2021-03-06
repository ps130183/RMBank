package com.km.rmbank.module.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.km.rmbank.R;
import com.km.rmbank.adapter.ActionRecentHome3Adapter;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.module.club.recent.ActionRecentInfoActivity;
import com.km.rmbank.utils.SwipeRefreshUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home3ActionRecentFragment extends BaseFragment<Home3ActionRecentPresenter> implements Home3ActionRecentContract.View {


    @BindView(R.id.rv_action_recent)
    RecyclerView rvActionRecent;

    public static Home3ActionRecentFragment newInstance(Bundle bundle) {

        Home3ActionRecentFragment fragment = new Home3ActionRecentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home3_action_recent;
    }

    @Override
    public Home3ActionRecentPresenter getmPresenter() {
        return new Home3ActionRecentPresenter(this);
    }

    @Override
    protected void createView() {
        initRecentAction();
        SwipeRefreshUtils.initSwipeRefresh(mSwipeRefresh, new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Observable.just(1)
                        .delay(3, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(@NonNull Integer integer) throws Exception {
                                mPresenter.getActionRecent(1);
                            }
                        });
            }
        });
    }

    private void initRecentAction(){
        RVUtils.setLinearLayoutManage(rvActionRecent, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvActionRecent,RVUtils.DIVIDER_COLOR_WHITE,1);
        final ActionRecentHome3Adapter adapter = new ActionRecentHome3Adapter(getContext());
        rvActionRecent.setAdapter(adapter);

        adapter.addLoadMore(rvActionRecent, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getActionRecent(adapter.getNextPage());
            }
        });
        mPresenter.getActionRecent(1);

        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<ActionDto>() {
            @Override
            public void onItemClick(ActionDto itemData, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("actionId",itemData.getId());
                toNextActivity(ActionRecentInfoActivity.class,bundle);
            }

        });
    }

    @Override
    public void showActionRecent(List<ActionDto> actionDtos, int pageNo) {
        ActionRecentHome3Adapter adapter = (ActionRecentHome3Adapter) rvActionRecent.getAdapter();
        adapter.addData(actionDtos,pageNo);
    }
}
