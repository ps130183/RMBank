package com.km.rmbank.module.personal.shopcart;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.ShoppingCartParentListAdapter;
import com.km.rmbank.adapter.ShoppingCartSubListAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.dto.ShoppingCartDto;
import com.km.rmbank.module.personal.shopcart.createorder.CreateOrderActivity;
import com.km.rmbank.module.rmshop.goods.GoodsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTouch;

public class ShoppingCartActivity extends BaseActivity<ShoppingCartPresenter> implements ShoppingCartContact.View, ShoppingCartParentListAdapter.onCheckedAllListener, ShoppingCartParentListAdapter.OnSubItemClcikListener, ShoppingCartParentListAdapter.onUpdateGoodsCount, ShoppingCartParentListAdapter.OnSubDeleteGoodsListener {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private ShoppingCartParentListAdapter adapter;

    @BindView(R.id.cb_check_all)
    CheckBox cbCheckAll;
    private boolean isCheckAllTouch = false;

    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;

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
        adapter.setTotalMoney(tvTotalMoney);
        mRecyclerView.setAdapter(adapter);
        adapter.setCheckedAllListener(this);
        adapter.setOnSubItemClcikListener(this);
        adapter.setOnUpdateGoodsCount(this);
        adapter.setOnSubDeleteGoodsListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadShoppingCartDatas();
    }

    @Override
    public void showShoppingCartDatas(List<ShoppingCartDto> shoppingCartEntities) {
        adapter.addData(shoppingCartEntities);
    }

    @Override
    public void updateGoodsCountSuccess(GoodsDetailsDto goodsDto, String optiontType) {
        ShoppingCartParentListAdapter adapter = (ShoppingCartParentListAdapter) mRecyclerView.getAdapter();
        if ("1".equals(optiontType)){
            goodsDto.setProductInShopCarCount(goodsDto.getProductInShopCarCount()+1);
        } else {
            goodsDto.setProductInShopCarCount(goodsDto.getProductInShopCarCount()-1);
        }
        adapter.setTotalMoney();
        adapter.notifyDataSetChanged();

    }

    @Override
    public void createOrderSuccess(List<ShoppingCartDto> shoppingCartDtos) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("checkedGoods", (ArrayList<? extends Parcelable>) shoppingCartDtos);
        toNextActivity(CreateOrderActivity.class,bundle);
    }

    @Override
    public void deleteSuccess(int positionOnParent, int positionOnSub) {
//        mPresenter.loadShoppingCartDatas();
        adapter.deleteGoodsSuccess(positionOnParent,positionOnSub);
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
    public void onSubItemClick(GoodsDetailsDto itemData, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("productNo",itemData.getProductNo());
        toNextActivity(GoodsActivity.class,bundle);
    }

    @OnClick(R.id.tv_payment)
    public void payMent(View view){
//        showToast("去付款");
        mPresenter.createOrder(adapter.getAllCheckedGoodsProductNo());
    }

    @Override
    public void updateGoodsCount(GoodsDetailsDto productNo, String optionType) {
        mPresenter.updateGoodsCount(productNo,optionType);
    }

    @Override
    public void deleteGoods(GoodsDetailsDto goodsDetailsDto,int positionOnParent, int positionOnSub) {
        mPresenter.deleteGoods(goodsDetailsDto,positionOnParent,positionOnSub);
    }
}
