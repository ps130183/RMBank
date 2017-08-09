package com.km.rmbank.module.club.recent;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.ActionRecentJoinMemberAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.ActionMemberDto;
import com.km.rmbank.utils.SwipeRefreshUtils;

import java.util.List;

import butterknife.BindView;

public class ActionJoinMemberActivity extends BaseActivity<ActionJoinMemberPresenter> implements ActionJoinMemberContract.View {

    @BindView(R.id.ll_toolbar_top)
    LinearLayout llToolbarTop;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.title)
    TextView mTitle;

    @BindView(R.id.rv_action_join_member)
    RecyclerView rvActionJoinMember;

    private ActionDto mActionDto;
    private boolean isMyClub;
    @Override
    protected int getContentView() {
        return R.layout.activity_action_join_member;
    }

    @Override
    protected String getTitleName() {
        return "已报名";
    }

    @Override
    public ActionJoinMemberPresenter getmPresenter() {
        return new ActionJoinMemberPresenter(this);
    }

    @Override
    protected void onCreate() {
        llToolbarTop.setBackgroundColor(ContextCompat.getColor(this,R.color.color_white));
        mToolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.color_white));
        mTitle.setTextColor(ContextCompat.getColor(this,R.color.color_block));

        setLeftIconClick(R.mipmap.ic_left_back1, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swiper_refresh);

        mActionDto = getIntent().getParcelableExtra("actionDto");
        isMyClub = getIntent().getBooleanExtra("isMyClub",isMyClub);
        initRecyclerview();
    }

    private void initRecyclerview(){
        SwipeRefreshUtils.initSwipeRefresh(mSwipeRefresh, new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getActionMemberList(mActionDto.getId(),1);
            }
        });

        RVUtils.setLinearLayoutManage(rvActionJoinMember, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvActionJoinMember);
        final ActionRecentJoinMemberAdapter adapter = new ActionRecentJoinMemberAdapter(this,isMyClub);
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
