package com.km.rmbank.module.personal.active;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.UserAccountDetailAdapter;
import com.km.rmbank.adapter.UserIntegralAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.ActiveValueDetailDto;
import com.km.rmbank.dto.ActiveValueDto;
import com.km.rmbank.dto.UserAccountDetailDto;
import com.ps.androidlib.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ActiveInfoActivity extends BaseActivity<ActiveInfoPresenter> implements ActiveInfoContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_active_value_usable)
    TextView tvActiveValueUsable;
    @BindView(R.id.tv_active_value_already)
    TextView tvActiveValueAlready;

    @Override
    protected int getContentView() {
        return R.layout.activity_active_info;
    }

    @Override
    protected String getTitleName() {
        return "活跃值";
    }

    @Override
    public ActiveInfoPresenter getmPresenter() {
        return new ActiveInfoPresenter(this);
    }

    @Override
    protected void onCreate() {

        setRightBtnClick("兑换商品", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toNextActivity(ConvertActiveGoodsListActivity.class);
            }
        });

        initRecyclerview();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getActiveValue();
        mPresenter.getActiveValueDetail(1);
    }

    private void initRecyclerview(){
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView, RVUtils.DIVIDER_COLOR_ACCOUNT_DETAILS,2);

        final UserAccountDetailAdapter adapter = new UserAccountDetailAdapter(this);
        mRecyclerView.setAdapter(adapter);
        adapter.addLoadMore(mRecyclerView, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getActiveValueDetail(adapter.getNextPage());
            }
        });

    }

    @OnClick(R.id.tv_active_value_introduce)
    public void clickActiveValueIntroduce(View view){
        toNextActivity(ActiveValueIntroduceActivity.class);
    }

    @Override
    public void showActiveValue(ActiveValueDto activeValueDto) {
        tvActiveValueUsable.setText("可用  " + activeValueDto.getActiveValue());
        tvActiveValueAlready.setText("已用  " + activeValueDto.getFreezingActiveValue());
    }

    @Override
    public void showActiveValueDetail(List<ActiveValueDetailDto> activeValueDetailDtos, int pageNo) {
        UserAccountDetailAdapter adapter = (UserAccountDetailAdapter) mRecyclerView.getAdapter();
        List<UserAccountDetailDto> userAccountDetailDtos = new ArrayList<>();
        for (ActiveValueDetailDto activeValueDetailDto : activeValueDetailDtos){
            UserAccountDetailDto userAccountDetailDto = new UserAccountDetailDto();
            userAccountDetailDto.setExplain(activeValueDetailDto.getActiveContent());
            userAccountDetailDto.setAmount(activeValueDetailDto.getActiveValue());
            userAccountDetailDto.setCreateDate(DateUtils.getInstance().getDate(activeValueDetailDto.getCreateDate()));
            userAccountDetailDtos.add(userAccountDetailDto);
        }
        adapter.addData(userAccountDetailDtos,pageNo);
    }
}
