package com.km.rmbank.module.actionarea;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.InformationAdapter;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.InformationDto;
import com.km.rmbank.module.actionarea.apply.ActionListActivity;
import com.km.rv_libs.base.ICell;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by kamangkeji on 17/3/14.
 */

public class InformationFragment extends BaseFragment<InformationPresenter> implements InformationContract.View {

    @BindView(R.id.title)
    TextView title;

//    @BindView(R.id.swiper_refresh)
//    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.recyclerview)
    RecyclerView rvActionArea;

    @BindView(R.id.fab_apply)
    FloatingActionButton fabApply;

    public static InformationFragment newInstance(Bundle bundle) {
        InformationFragment fragment = new InformationFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home_information;
    }

    @Override
    public InformationPresenter getmPresenter() {
        return new InformationPresenter(this);
    }

    @Override
    protected void createView() {
        title.setText("资讯");
    }


    @OnClick(R.id.fab_apply)
    public void apply(View view){
        toNextActivity(ActionListActivity.class);
    }

    @Override
    public void initAction(List<String> bannerImages) {

        RVUtils.setLinearLayoutManage(rvActionArea, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvActionArea);
        final InformationAdapter adapter = new InformationAdapter(getContext());
        adapter.setBannerImages(bannerImages);
        rvActionArea.setAdapter(adapter);
        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<InformationDto>() {
            @Override
            public void onItemClick(InformationDto itemData, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("informationDto",itemData);
                toNextActivity(InformationDetailActivity.class,bundle);
            }
        });

        adapter.setOnBannerCliclListener(new InformationAdapter.OnBannerCliclListener() {
            @Override
            public void clickBanner(int position) {
                showToast("position = " + position);
            }
        });
        adapter.addLoadMore(rvActionArea, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getActionList(adapter.getNextPage());
            }
        });
        mPresenter.getActionList(adapter.getNextPage());
    }

    @Override
    public void getActionListSuccess(List<InformationDto> informationDtos, int pageNo) {
        InformationAdapter adapter = (InformationAdapter) rvActionArea.getAdapter();
        adapter.addData(informationDtos,pageNo);
    }

    @Override
    public void showInformationBanner(final List<InformationDto> informationDtos) {

        List<String> bannerImage = new ArrayList<>();
        for (InformationDto informationDto : informationDtos){
            bannerImage.add(informationDto.getAvatarUrl());
        }
        initAction(bannerImage);
        InformationAdapter adapter = (InformationAdapter) rvActionArea.getAdapter();
        adapter.setOnBannerCliclListener(new InformationAdapter.OnBannerCliclListener() {
            @Override
            public void clickBanner(int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("informationDto",informationDtos.get(position));
                toNextActivity(InformationDetailActivity.class,bundle);
            }
        });
    }

}
