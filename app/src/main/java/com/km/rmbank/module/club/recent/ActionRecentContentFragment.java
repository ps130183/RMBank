package com.km.rmbank.module.club.recent;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.ActionRecentGuestAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.ActionDto;
import com.ps.androidlib.utils.glide.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActionRecentContentFragment extends BaseFragment {

    @BindView(R.id.rv_guest)
    RecyclerView rvGuest;

    @BindView(R.id.iv_action_img)
    ImageView ivActionImg;
    @BindView(R.id.tv_club_name)
    TextView tvClubName;
    @BindView(R.id.tv_action_address)
    TextView tvActionAddress;
    @BindView(R.id.tv_hold_time)
    TextView tvHoldTime;
    @BindView(R.id.tv_actionflow)
    TextView tvActionFlow;

    private ActionDto mActionDto;

    public static ActionRecentContentFragment newInstance(Bundle bundle) {
        ActionRecentContentFragment fragment = new ActionRecentContentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_action_recent_content;
    }

    @Override
    protected void createView() {
        mActionDto = getArguments().getParcelable("actionDto");

//        GlideUtils.loadImage(ivActionImg,mActionDto.getActivityPictureUrl());
        String clubName = TextUtils.isEmpty(mActionDto.getClubName()) ? "玩转地球商旅学苑" : mActionDto.getClubName();
        tvClubName.setText(clubName);
        tvActionAddress.setText(mActionDto.getAddress());
        tvHoldTime.setText(mActionDto.getHoldDate());
        tvActionFlow.setText(mActionDto.getFlow());

        initRecyclerview();
    }

    private void initRecyclerview(){
        RVUtils.setLinearLayoutManage(rvGuest, LinearLayoutManager.VERTICAL);
        ActionRecentGuestAdapter adapter = new ActionRecentGuestAdapter(getContext());
        rvGuest.setAdapter(adapter);

        adapter.addData(mActionDto.getGuestList());
    }

}
