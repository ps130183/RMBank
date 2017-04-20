package com.km.rmbank.module.personal.integral;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.UserIntegralAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.IntegralDetailsDto;
import com.km.rmbank.dto.IntegralDto;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyIntegralActivity extends BaseActivity<MyIntegralPresenter> implements MyIntegralContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_balance)
    TextView tvBalance;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_integral;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "我的积分";
    }

    @Override
    public MyIntegralPresenter getmPresenter() {
        return new MyIntegralPresenter(this);
    }

    @Override
    protected void onCreate() {
        initRecyclerview();
        mPresenter.getUserIntegralInfo();
    }

    private void initRecyclerview(){
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView, RVUtils.DIVIDER_COLOR_ACCOUNT_DETAILS,2);
//        List<ICell> iCells = new ArrayList<>();
//        for (int i = 0; i < 10; i++){
//            iCells.add(new MyIntegralCell(null));
//        }
//        TemplateAdapter adapter = new TemplateAdapter();
        final UserIntegralAdapter adapter = new UserIntegralAdapter(this);
        mRecyclerView.setAdapter(adapter);
        adapter.addLoadMore(mRecyclerView, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getIntegralDetails(adapter.getNextPage());
            }
        });
        mPresenter.getIntegralDetails(adapter.getNextPage());
    }

    /**
     * 积分规则
     * @param view
     */
    @OnClick(R.id.tv_integral)
    public void withDraw(View view){
//        toNextActivity(WithDrawListActivity.class);
    }

    @Override
    public void showUserIntegralInfo(IntegralDto integralDto) {
        tvBalance.setText(integralDto.getTotal()+"");
    }

    @Override
    public void showIntegralDetails(List<IntegralDetailsDto> integralDetailsDtos, int pageNo) {
        UserIntegralAdapter adapter = (UserIntegralAdapter) mRecyclerView.getAdapter();
        adapter.addData(integralDetailsDtos,pageNo);
    }
}
