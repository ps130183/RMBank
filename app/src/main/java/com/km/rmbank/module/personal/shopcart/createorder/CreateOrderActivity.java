package com.km.rmbank.module.personal.shopcart.createorder;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioGroup;

import com.km.rmbank.R;
import com.km.rmbank.adapter.ShoppingCartParentListAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.entity.ShoppingCartEntity;
import com.km.rmbank.module.personal.shopcart.payment.PaymentActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CreateOrderActivity extends BaseActivity<CreateOrderPresenter> implements CreateOrderContact.View {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private ShoppingCartParentListAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_create_order;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "完善订单";
    }

    @Override
    public CreateOrderPresenter getmPresenter() {
        return new CreateOrderPresenter(this,this);
    }

    @Override
    protected void onCreate() {
        initRv();
    }

    private void initRv(){
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView);
        adapter = new ShoppingCartParentListAdapter(this);
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    public void showOrderDatas(List<ShoppingCartEntity> cartEntities) {
        adapter.addData(cartEntities);
    }

    @OnClick(R.id.tv_submit_order)
    public void submitOrder(View view){
        toNextActivity(PaymentActivity.class);
    }
}
