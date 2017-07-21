package com.km.rmbank.module.club.recent;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.km.rmbank.R;
import com.km.rmbank.adapter.ActionRecentJoinMemberAdapter;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.ActionMemberDto;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActionJoinMemberFragment extends BaseFragment<ActionJoinMemberPresenter> implements ActionJoinMemberContract.View {

    @BindView(R.id.rv_action_join_member)
    RecyclerView rvActionJoinMember;

    private ActionDto mActionDto;

    public static ActionJoinMemberFragment newInstance(Bundle bundle) {
        ActionJoinMemberFragment fragment = new ActionJoinMemberFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_action_join_member;
    }

    @Override
    public ActionJoinMemberPresenter getmPresenter() {
        return new ActionJoinMemberPresenter(this);
    }

    @Override
    protected void createView() {
        mActionDto = getArguments().getParcelable("actionDto");
        initRecyclerview();
    }

    private void initRecyclerview(){
        RVUtils.setLinearLayoutManage(rvActionJoinMember, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvActionJoinMember);
        final ActionRecentJoinMemberAdapter adapter = new ActionRecentJoinMemberAdapter(getContext());
        rvActionJoinMember.setAdapter(adapter);

        adapter.addLoadMore(rvActionJoinMember, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getActionMemberList(mActionDto.getId(),adapter.getNextPage());
            }
        });
        mPresenter.getActionMemberList(mActionDto.getId(),1);
    }

    @Override
    public void showActionMemberList(List<ActionMemberDto> actionMemberDtos, int pageNo) {
        ActionRecentJoinMemberAdapter adapter = (ActionRecentJoinMemberAdapter) rvActionJoinMember.getAdapter();
        adapter.addData(actionMemberDtos,pageNo);
    }
}
