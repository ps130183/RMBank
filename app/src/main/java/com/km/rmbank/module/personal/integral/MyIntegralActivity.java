package com.km.rmbank.module.personal.integral;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.cell.MyIntegralCell;
import com.km.rmbank.module.personal.account.withdraw.WithDrawListActivity;
import com.km.rv_libs.TemplateAdapter;
import com.km.rv_libs.base.ICell;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyIntegralActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

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
    protected void onCreate() {
        initRecyclerview();
    }

    private void initRecyclerview(){
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView, RVUtils.DIVIDER_COLOR_ACCOUNT_DETAILS,2);
        List<ICell> iCells = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            iCells.add(new MyIntegralCell(null));
        }
        TemplateAdapter adapter = new TemplateAdapter();
        mRecyclerView.setAdapter(adapter);
        adapter.addData(iCells);
    }

    /**
     * 积分规则
     * @param view
     */
    @OnClick(R.id.tv_integral)
    public void withDraw(View view){
//        toNextActivity(WithDrawListActivity.class);
    }
}
