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
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.cell.ActionCell;
import com.km.rmbank.cell.BannerCell;
import com.km.rmbank.dto.ActionDto;
import com.km.rv_libs.TemplateAdapter;
import com.km.rv_libs.base.BaseAdapter;
import com.km.rv_libs.base.ICell;
import com.ps.androidlib.utils.MToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by kamangkeji on 17/3/14.
 */

public class ActionAreaFragment extends BaseFragment<ActionPresenter> implements ActionContract.View {

    @BindView(R.id.title)
    TextView title;

//    @BindView(R.id.swiper_refresh)
//    SwipeRefreshLayout mSwipeRefresh;
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
    public ActionPresenter getmPresenter() {
        return new ActionPresenter(this);
    }

    @Override
    protected void createView() {
        title.setText("活动专区");
    }


    @OnClick(R.id.fab_apply)
    public void apply(View view){
        MToast.showToast(getContext(),"报名");
    }

    @Override
    public void initAction() {

//        RVUtils.addSwipRefresh(mSwipeRefresh, new RVUtils.OnSwipeRefresh() {
//            @Override
//            public void onRefresh(final SwipeRefreshLayout mSwipeRefresh) {
////                new Handler().postDelayed(new Runnable() {
////                    @Override
////                    public void run() {
////                        mSwipeRefresh.setRefreshing(false);
////                    }
////                },3000);
//                TemplateAdapter adapter = (TemplateAdapter) rvActionArea.getAdapter();
//                if (adapter != null){
//                    adapter.clear();
//                    adapter.addData(adapter.getHeaderCells());
//                    mPresenter.getActionList(adapter.getNextPage());
//                }
//
//            }
//        });

        List<Integer> images = new ArrayList<>();
        images.add(R.mipmap.timg);
        images.add(R.mipmap.timg);
        images.add(R.mipmap.timg);
        images.add(R.mipmap.timg);

        RVUtils.setLinearLayoutManage(rvActionArea, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvActionArea);
        final TemplateAdapter adapter = new TemplateAdapter();
        adapter.addHeader(new BannerCell(images,R.layout.action_area_banner));
        rvActionArea.setAdapter(adapter);

        adapter.addLoadMore(rvActionArea, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getActionList(adapter.getNextPage());
            }
        });
    }

    @Override
    public void getActionListSuccess(List<ActionDto> actionDtos, int pageNo) {
        TemplateAdapter adapter = (TemplateAdapter) rvActionArea.getAdapter();
        adapter.addData(getActionListCell(actionDtos));
    }

    private List<ICell> getActionListCell(List<ActionDto> actionDtos){
        List<ICell> mICells = new ArrayList<>();
        for(ActionDto name : actionDtos){
            mICells.add(new ActionCell(name));
        }
        return mICells;
    }
}
