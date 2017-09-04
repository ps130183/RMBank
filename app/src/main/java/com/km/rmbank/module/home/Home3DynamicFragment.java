package com.km.rmbank.module.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.Home3ActionPastAdapter;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.InformationDto;
import com.km.rmbank.module.club.past.ActionPastDetailActivity;
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
public class Home3DynamicFragment extends BaseFragment<Home3DynamicPresenter> implements Home3DynamicContract.View {

    @BindView(R.id.rv_home_dynamic)
    RecyclerView rvHomeDynamic;

    public static Home3DynamicFragment newInstance(Bundle bundle) {

        Home3DynamicFragment fragment = new Home3DynamicFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home3_dynamic;
    }

    @Override
    public Home3DynamicPresenter getmPresenter() {
        return new Home3DynamicPresenter(this);
    }

    @Override
    protected void createView() {
        initRecycler();
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
                                mPresenter.getDynamicInformationList(1);
                            }
                        });
            }
        });
    }

    private void initRecycler(){
        RVUtils.setLinearLayoutManage(rvHomeDynamic, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvHomeDynamic,RVUtils.DIVIDER_COLOR_DEFAULT,1);
        final Home3ActionPastAdapter adapter = new Home3ActionPastAdapter(getContext());

        rvHomeDynamic.setAdapter(adapter);

        adapter.addLoadMore(rvHomeDynamic, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getDynamicInformationList(adapter.getNextPage());
            }
        });
        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<InformationDto>() {
            @Override
            public void onItemClick(InformationDto itemData, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("actionPastId",itemData.getId());
                toNextActivity(ActionPastDetailActivity.class,bundle);
            }

        });

        mPresenter.getDynamicInformationList(1);
    }

    @Override
    public void showDynamicInformationList(List<InformationDto> informationDtos,int pageNo) {
        Home3ActionPastAdapter adapter = (Home3ActionPastAdapter) rvHomeDynamic.getAdapter();
        adapter.addData(informationDtos,pageNo);
    }

}
