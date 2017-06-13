package com.km.rmbank.module.personal.account;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.UserAccountDetailAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.cell.PersonalAccountDetailsCell;
import com.km.rmbank.dto.UserAccountDetailDto;
import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.module.personal.account.withdraw.WithDrawListActivity;
import com.km.rv_libs.TemplateAdapter;
import com.km.rv_libs.base.ICell;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class UserAccountActivity extends BaseActivity<UserAccountPresenter> implements UserAccountContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_balance)
    TextView tvBalance;

    @Override
    protected int getContentView() {
        return R.layout.activity_user_account;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "我的账户";
    }


    @Override
    public UserAccountPresenter getmPresenter() {
        return new UserAccountPresenter(this);
    }

    @Override
    protected void onCreate() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadBalance();
        mPresenter.loadAccountDetail(1);
    }

    /**
     * 提现
     *
     * @param view
     */
    @OnClick(R.id.tv_integral)
    public void withDraw(View view) {
        toNextActivity(WithDrawListActivity.class);
    }

    @Override
    public void initAccountDetail() {
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView, RVUtils.DIVIDER_COLOR_ACCOUNT_DETAILS, 2);
        final UserAccountDetailAdapter adapter = new UserAccountDetailAdapter(this);
        mRecyclerView.setAdapter(adapter);
        adapter.addLoadMore(mRecyclerView, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.loadAccountDetail(adapter.getNextPage());
            }
        });

    }

    @Override
    public void showAccountDetail(List<UserAccountDetailDto> userAccountDetailDtos, int curPage) {
        UserAccountDetailAdapter adapter = (UserAccountDetailAdapter) mRecyclerView.getAdapter();
        adapter.addData(userAccountDetailDtos,curPage);
    }

    @Override
    public void showBalance(UserBalanceDto userBalanceDto) {
        tvBalance.setText(userBalanceDto.getBalance() + "");
    }
}
