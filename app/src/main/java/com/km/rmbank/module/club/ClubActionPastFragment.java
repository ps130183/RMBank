package com.km.rmbank.module.club;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.km.rmbank.R;
import com.km.rmbank.adapter.ActionPastDetailsAdapter;
import com.km.rmbank.adapter.ClubActionPastAdapter;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.ActionPastDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.module.club.past.ActionPastDetailActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClubActionPastFragment extends BaseFragment<ClubActionPastPresenter> implements ClubActionPastContract.View {

    @BindView(R.id.rv_action_past)
    RecyclerView rvActionPast;

    private ClubDto mClubDto;
    private boolean isMyClub;

    public static ClubActionPastFragment newInstance(Bundle bundle) {

        ClubActionPastFragment fragment = new ClubActionPastFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_club_action_past;
    }

    @Override
    public ClubActionPastPresenter getmPresenter() {
        return new ClubActionPastPresenter(this);
    }

    @Override
    protected void createView() {
        mClubDto = getArguments().getParcelable("clubDto");
        isMyClub = getArguments().getBoolean("isMyClub");
        initRecyclerview();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getActionPasList(mClubDto.getId(),1);
    }

    private void initRecyclerview(){
        RVUtils.setLinearLayoutManage(rvActionPast, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvActionPast);
        final ClubActionPastAdapter adapter = new ClubActionPastAdapter(getContext());
        rvActionPast.setAdapter(adapter);

        adapter.addLoadMore(rvActionPast, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getActionPasList(mClubDto.getId(),adapter.getNextPage());
            }
        });

        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<ActionPastDto>() {
            @Override
            public void onItemClick(ActionPastDto itemData, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("actionPastId",itemData.getId());
                bundle.putBoolean("isMyClub",isMyClub);
                toNextActivity(ActionPastDetailActivity.class,bundle);
            }
        });

    }

    @Override
    public void showActionPast(List<ActionPastDto> actionPastDtos, int pageNo) {
        ClubActionPastAdapter adapter = (ClubActionPastAdapter) rvActionPast.getAdapter();
        adapter.addData(actionPastDtos,pageNo);
    }
}
