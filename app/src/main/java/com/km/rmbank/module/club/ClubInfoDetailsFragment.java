package com.km.rmbank.module.club;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.km.rmbank.R;
import com.km.rmbank.adapter.ClubInfoDetailsAdapter;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.ClubDto;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClubInfoDetailsFragment extends BaseFragment {

    @BindView(R.id.rv_club_info_details)
    RecyclerView rvClubInfoDetails;

    public static ClubInfoDetailsFragment newInstance(Bundle bundle) {
        ClubInfoDetailsFragment fragment = new ClubInfoDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_club_info_details;
    }

    @Override
    protected void createView() {
        initRecyclerView();
    }


    @Override
    public void onResume() {
        super.onResume();
//        ClubInfoDetailsAdapter adapter = (ClubInfoDetailsAdapter) rvClubInfoDetails.getAdapter();
        rvClubInfoDetails.scrollToPosition(0);
    }

    private void initRecyclerView(){

        ClubDto clubDto = getArguments().getParcelable("clubDto");

        RVUtils.setLinearLayoutManage(rvClubInfoDetails, LinearLayoutManager.VERTICAL);
        ClubInfoDetailsAdapter adapter = new ClubInfoDetailsAdapter(getContext());
        rvClubInfoDetails.setAdapter(adapter);

        adapter.addData(clubDto.getClubDetailList());
    }

}
