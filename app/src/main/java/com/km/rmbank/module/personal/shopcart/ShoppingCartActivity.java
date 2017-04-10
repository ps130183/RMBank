package com.km.rmbank.module.personal.shopcart;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.km.rmbank.R;
import com.km.rmbank.adapter.ShoppingCartParentListAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.entity.ShoppingCartEntity;
import com.km.rmbank.module.personal.shopcart.createorder.CreateOrderActivity;
import com.km.rmbank.module.rmshop.goods.GoodsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTouch;

public class ShoppingCartActivity extends BaseActivity<ShoppingCartPresenter> implements ShoppingCartContact.View, ShoppingCartParentListAdapter.onCheckedAllListener, ShoppingCartParentListAdapter.OnSubItemClcikListener {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private ShoppingCartParentListAdapter adapter;

    @BindView(R.id.cb_check_all)
    CheckBox cbCheckAll;
    private boolean isCheckAllTouch = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_shopping_cart;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "购物车";
    }

    @Override
    public ShoppingCartPresenter getmPresenter() {
        return new ShoppingCartPresenter(this);
    }

    @Override
    protected void onCreate() {
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView);
        adapter = new ShoppingCartParentListAdapter(this);
        adapter.setShoppingCart(true);
        mRecyclerView.setAdapter(adapter);
        adapter.setCheckedAllListener(this);
        adapter.setOnSubItemClcikListener(this);
    }


    @Override
    public void showShoppingCartDatas(List<ShoppingCartEntity> shoppingCartEntities) {
        adapter.addData(shoppingCartEntities);
    }

    @Override
    public void onCheckedAll(boolean isCheckedAll) {
        isCheckAllTouch = false;
        cbCheckAll.setChecked(isCheckedAll);
    }

    @OnCheckedChanged(R.id.cb_check_all)
    public void checkAll(CompoundButton buttonView, boolean isChecked){
        if (isCheckAllTouch){
            adapter.checkAll(isChecked);
        }
    }
    @OnTouch(R.id.cb_check_all)
    public boolean onCbCheckAllTouch(View v, MotionEvent event){
        isCheckAllTouch = true;
        return false;
    }

    @Override
    public void onSubItemClick(GoodsDto itemData, int position) {
        toNextActivity(GoodsActivity.class);
    }

    @OnClick(R.id.tv_payment)
    public void payMent(View view){
//        showToast("去付款");
        toNextActivity(CreateOrderActivity.class);
    }
}
