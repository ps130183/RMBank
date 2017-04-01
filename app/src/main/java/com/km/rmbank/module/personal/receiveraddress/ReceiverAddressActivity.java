package com.km.rmbank.module.personal.receiveraddress;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.adapter.ReceiverAddressAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.ReceiverAddressDto;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ReceiverAddressActivity extends BaseActivity<ReceiverAddressPresenter> implements ReceiverAddressContract.View, BaseAdapter.ItemClickListener {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Override
    protected int getContentView() {
        return R.layout.activity_receiver_address;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "收货地址";
    }

    @Override
    public ReceiverAddressPresenter getmPresenter() {
        return new ReceiverAddressPresenter(this,this);
    }

    @Override
    protected void onCreate() {

    }

    @Override
    public void initRecyclerView() {
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView);
        ReceiverAddressAdapter addressAdapter = new ReceiverAddressAdapter(this);
        mRecyclerView.setAdapter(addressAdapter);
        addressAdapter.setItemClickListener(this);
    }

    @Override
    public void showReceiverAddressList(List<ReceiverAddressDto> receiverAddressDtos) {
        ReceiverAddressAdapter addressAdapter = (ReceiverAddressAdapter) mRecyclerView.getAdapter();
        addressAdapter.addData(receiverAddressDtos);
    }

    @Override
    public void onItemClick(Object itemData, int position) {

    }

    @OnClick(R.id.btn_create_receiver_address)
    public void createReceiverAddress(View view){
        toNextActivity(CreateReceiverAddressActivity.class);
    }
}
