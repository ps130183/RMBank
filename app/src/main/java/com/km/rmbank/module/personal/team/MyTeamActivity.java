package com.km.rmbank.module.personal.team;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.MyTeamParentAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.MyTeamDto;
import com.km.rmbank.entity.TeamEntity;
import com.km.rmbank.dto.UserDto;
import com.km.rmbank.module.personal.userinfo.EditUserCardActivity;
import com.km.rmbank.module.personal.userinfo.UserCardInfoActivity;
import com.km.rmbank.module.personal.userinfo.UserCardInfoContract;

import java.util.List;

import butterknife.BindView;

public class MyTeamActivity extends BaseActivity<MyTeamPresenter> implements MyTeamConteact.View, MyTeamParentAdapter.onClickUserListener {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_team_number)
    TextView tvTeamNumber;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_team;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "我的团队";
    }

    @Override
    public MyTeamPresenter getmPresenter() {
        return new MyTeamPresenter(this);
    }

    @Override
    protected void onCreate() {

    }


    @Override
    public void initRecycler() {
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView);
        MyTeamParentAdapter adapter = new MyTeamParentAdapter(this);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnClickUserListener(this);
    }

    @Override
    public void showMyTeam(List<MyTeamDto> teamEntities) {
        MyTeamParentAdapter adapter = (MyTeamParentAdapter) mRecyclerView.getAdapter();
        adapter.addData(teamEntities);
        tvTeamNumber.setText(adapter.getMemberCount()+"");
    }


    @Override
    public void clickUser(MyTeamDto.MemberDtoListBean itemData, int position) {
//        showToast(itemData.toString());
        Bundle bundle = new Bundle();
//        bundle.putParcelable("memberDto",itemData);
        bundle.putString("shopId",itemData.getId());
        toNextActivity(UserCardInfoActivity.class,bundle);
    }
}
