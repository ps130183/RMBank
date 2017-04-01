package com.km.rmbank.module.actionarea;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.cell.ActionCell;
import com.km.rmbank.cell.BannerCell;
import com.km.rv_libs.TemplateAdapter;
import com.km.rv_libs.base.ICell;
import com.ps.androidlib.utils.MToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by kamangkeji on 17/3/14.
 */

public class ActionAreaFragment extends BaseFragment {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.swiper_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.recyclerview)
    RecyclerView rvActionArea;

    @BindView(R.id.fab_apply)
    FloatingActionButton fabApply;

    public static ActionAreaFragment newInstance(Bundle bundle) {
        ActionAreaFragment fragment = new ActionAreaFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home_action_area;
    }

    @Override
    protected void createView() {
        title.setText("活动专区");
        initRvActionArea();
    }

    private void initRvActionArea(){

        RVUtils.addSwipRefresh(mSwipeRefresh, new RVUtils.OnSwipeRefresh() {
            @Override
            public void onRefresh(final SwipeRefreshLayout mSwipeRefresh) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefresh.setRefreshing(false);
                    }
                },3000);
            }
        });

        List<Integer> images = new ArrayList<>();
        images.add(R.mipmap.timg);
        images.add(R.mipmap.timg);
        images.add(R.mipmap.timg);
        images.add(R.mipmap.timg);

        List<String> names = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            names.add("this is name " + i);
        }
        List<ICell> mICells = new ArrayList<>();
        mICells.add(new BannerCell(images,R.layout.action_area_banner));
        for(String name : names){
            mICells.add(new ActionCell(name,R.layout.item_rv_action_area));
        }
        RVUtils.setLinearLayoutManage(rvActionArea, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvActionArea);
        final TemplateAdapter adapter = new TemplateAdapter();
        adapter.addData(mICells);
        rvActionArea.setAdapter(adapter);

        adapter.addLoadMore(rvActionArea, new TemplateAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
//                adapter.hideLoadeMore();
            }
        });

    }

    @OnClick(R.id.fab_apply)
    public void apply(View view){
        MToast.showToast(getContext(),"报名");
    }
}
