package com.km.rmbank.module.personal.leader;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.ActionDto;
import com.ps.androidlib.utils.DateUtils;
import com.ps.commonadapter.adapter.CommonAdapter;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class MeetingListActivity extends BaseActivity<MeetingListPresenter> implements MeetingListContract.View {

    @BindView(R.id.rv_meeting_list)
    RecyclerView rvMeetingList;

    private List<ActionDto> meetingDtos;
    private RecyclerAdapterHelper<ActionDto> mHelper;
    @Override
    protected int getContentView() {
        return R.layout.activity_meeting_list;
    }

    @Override
    public MeetingListPresenter getmPresenter() {
        return new MeetingListPresenter(this);
    }

    @Override
    protected String getTitleName() {
        return "助教管理";
    }

    @Override
    protected void onCreate() {
        initRecycler();
    }

    private void initRecycler(){
        meetingDtos = new ArrayList<>();
        mHelper = new RecyclerAdapterHelper<>(rvMeetingList);

        mHelper.addLinearLayoutManager();
        mHelper.addCommonAdapter(R.layout.item_rv_meeting_list,  meetingDtos, new RecyclerAdapterHelper.CommonConvert<ActionDto>() {
            @Override
            public void convert(CommonViewHolder holder, ActionDto mData) {
                holder.setText(R.id.tv_meeting_name,mData.getTitle());
                holder.setText(R.id.tv_start_time, DateUtils.getInstance().dateToString(new Date(mData.getStartDate()),DateUtils.YMDHM));
            }
        }).create();

        mHelper.getmCommonAdapter().setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void itemClick(CommonViewHolder holder, int position) {
                ActionDto actionDto = meetingDtos.get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("actionDto",actionDto);
                toNextActivity(EntranceSignInActivity.class,bundle);
            }
        });

        mPresenter.loadActionList();

    }

    @Override
    public void showActionList(List<ActionDto> meetingDtos) {
        this.meetingDtos.clear();
        this.meetingDtos.addAll(meetingDtos);
        mHelper.getmAdapter().notifyDataSetChanged();
    }
}
