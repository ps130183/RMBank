package com.km.rmbank.module.home;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.HomeClubTabAdapter;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.ClubDto;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeClubTabFragment extends BaseFragment<HomeClubTabPresenter> implements HomeClubTanContract.View {

    @BindView(R.id.rv_club)
    RecyclerView rvClub;

    public static HomeClubTabFragment newInstance(Bundle bundle) {
        HomeClubTabFragment fragment = new HomeClubTabFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home_club_tab;
    }

    @Override
    public HomeClubTabPresenter getmPresenter() {
        return new HomeClubTabPresenter(this);
    }

    @Override
    protected void createView() {
        String type = getArguments().getString("clubType","");
        initRvClub();
        mPresenter.getClubInfos(type);
    }

    private void initRvClub(){
        RVUtils.setGridLayoutManage(rvClub,4);
        HomeClubTabAdapter adapter = new HomeClubTabAdapter(getContext());
        rvClub.setAdapter(adapter);
    }

    @Override
    public void showClubInfos(List<ClubDto> clubDtos) {
        HomeClubTabAdapter adapter = (HomeClubTabAdapter) rvClub.getAdapter();
        adapter.addData(clubDtos);
    }
}
