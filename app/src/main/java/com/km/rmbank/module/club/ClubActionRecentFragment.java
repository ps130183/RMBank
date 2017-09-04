package com.km.rmbank.module.club;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.km.rmbank.R;
import com.km.rmbank.adapter.ClubActionRecentAdapter;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.module.club.recent.ActionRecentInfoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClubActionRecentFragment extends BaseFragment<ClubActionRecentPresenter> implements ClubActionRecentContract.View {

    private ClubDto mClubDto;
    @BindView(R.id.rv_action_recent)
    RecyclerView rvActionRecent;

    private boolean isMyClub;

    public static ClubActionRecentFragment newInstance(Bundle bundle) {
        ClubActionRecentFragment fragment = new ClubActionRecentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_club_action_recent;
    }

    @Override
    public ClubActionRecentPresenter getmPresenter() {
        return new ClubActionRecentPresenter(this);
    }

    @Override
    protected void createView() {
        mClubDto = getArguments().getParcelable("clubDto");
        isMyClub = getArguments().getBoolean("isMyClub");
        if (mClubDto == null){
            return;
        }
        initRecyclerview();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getActionRecent(mClubDto.getId(),1);
    }

    private void initRecyclerview(){

        RVUtils.setLinearLayoutManage(rvActionRecent, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvActionRecent);
        final ClubActionRecentAdapter adapter = new ClubActionRecentAdapter(getContext());
        rvActionRecent.setAdapter(adapter);

        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<ActionDto>() {
            @Override
            public void onItemClick(ActionDto itemData, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("actionId",itemData.getId());
                bundle.putBoolean("isMyClub",isMyClub);
                bundle.putString("clubId",mClubDto.getId());
                toNextActivity(ActionRecentInfoActivity.class,bundle);
            }
        });

        adapter.addLoadMore(rvActionRecent, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getActionRecent(mClubDto.getId(),adapter.getNextPage());
            }
        });
    }

    @Override
    public void showActionRecent(List<ActionDto> actionDtos, int pageNo) {
        ClubActionRecentAdapter adapter = (ClubActionRecentAdapter) rvActionRecent.getAdapter();
        adapter.addData(actionDtos,pageNo);
    }
}
