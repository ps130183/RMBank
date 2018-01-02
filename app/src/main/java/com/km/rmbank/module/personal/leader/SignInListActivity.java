package com.km.rmbank.module.personal.leader;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.SignInDto;
import com.km.rmbank.module.personal.account.UserAccountActivity;
import com.km.rmbank.utils.SwipeRefreshUtils;
import com.ps.commonadapter.adapter.CommonAdapter;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.commonadapter.adapter.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class SignInListActivity extends BaseActivity<SignInListPresenter> implements SignInListContract.View {

    @BindView(R.id.rv_sign_in)
    RecyclerView rvSignIn;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;

    private CommonAdapter<SignInDto> adapter;
    private RecyclerView.Adapter mAdapter;

    private String actionId;

    @Override
    protected int getContentView() {
        return R.layout.activity_sign_in_list;
    }

    @Override
    public SignInListPresenter getmPresenter() {
        return new SignInListPresenter(this);
    }

    @Override
    protected String getTitleName() {
        return "签到统计";
    }

    @Override
    protected void onCreate() {
        actionId = getIntent().getStringExtra("actionId");
        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swiper_refresh);
        initRv();
    }

    private List<SignInDto> mDatas;

    private RecyclerAdapterHelper<SignInDto> mHelper;

    private void initRv() {
        mDatas = new ArrayList<>();

        adapter = new CommonAdapter<SignInDto>(this, mDatas, R.layout.item_rv_sign_in_list) {
            @Override
            public void convert(CommonViewHolder holder, SignInDto mData) {
                holder.setText(R.id.tv_user_name, mData.getRegistrationName());
                holder.setText(R.id.tv_status, "1".equals(mData.getStatus()) ? "未缴费" : mData.getSumTotal());
                holder.setText(R.id.tv_back_money, mData.getBackMoney() + "");
            }
        };

        mHelper = new RecyclerAdapterHelper<>(rvSignIn);
        mHelper.addLinearLayoutManager()
                .addAdapter(adapter)
                .addEmptyWrapper(null)
                .create();

        mAdapter = mHelper.getmAdapter();
        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void itemClick(CommonViewHolder holder, int position) {
//                showToast(mDatas.get(position));
            }
        });

        mPresenter.loadSignInLists(actionId);

        SwipeRefreshUtils.initSwipeRefresh(mSwipeRefresh, new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadSignInLists(actionId);
            }
        });
    }

    private int i = 3;

    @OnClick(R.id.tv_submit)
    public void submit(View view) {
        toNextActivity(UserAccountActivity.class);
    }

    @Override
    public void showSignInLists(List<SignInDto> signInDtos) {
        mDatas.clear();
        mDatas.addAll(signInDtos);
        mAdapter.notifyDataSetChanged();

        countTotalMoney();
    }

    private void countTotalMoney() {
        int money = 0;
        for (int i = 0; i < mDatas.size(); i++) {
            money += mDatas.get(i).getBackMoney();
        }
        tvTotalMoney.setText("收入总计：¥ " + money);

    }
}
