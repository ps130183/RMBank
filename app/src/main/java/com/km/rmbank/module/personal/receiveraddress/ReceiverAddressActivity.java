package com.km.rmbank.module.personal.receiveraddress;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.daimajia.swipe.util.Attributes;
import com.km.rmbank.R;
import com.km.rmbank.adapter.ReceiverAddressAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.event.OtherAddressEvent;
import com.ps.androidlib.utils.EventBusUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ReceiverAddressActivity extends BaseActivity<ReceiverAddressPresenter> implements ReceiverAddressContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private boolean selecteOtherAddress;
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
        return new ReceiverAddressPresenter(this);
    }

    @Override
    protected void onCreate() {
        selecteOtherAddress = getIntent().getBooleanExtra("select_other_address",false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadReceiverAddressData();
    }

    @Override
    public void initRecyclerView() {
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView);
        ReceiverAddressAdapter addressAdapter = new ReceiverAddressAdapter(this);
        addressAdapter.setMode(Attributes.Mode.Single);
        addressAdapter.setSelectOtherAddress(selecteOtherAddress);
        mRecyclerView.setAdapter(addressAdapter);
        addressAdapter.setItemClickListener(new BaseAdapter.ItemClickListener<ReceiverAddressDto>() {
            @Override
            public void onItemClick(ReceiverAddressDto itemData, int position) {
                if (selecteOtherAddress){
                    EventBusUtils.post(new OtherAddressEvent(itemData));
                    finish();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("receiverAddressDto",itemData);
                    toNextActivity(CreateReceiverAddressActivity.class,bundle);
                }
            }
        });

        //设置默认
        addressAdapter.setOnSetDefaultListener(new ReceiverAddressAdapter.onSetDefaultListener() {
            @Override
            public void setDefault(ReceiverAddressDto receiverAddressDto) {
                mPresenter.setDefaultReceiverAddress(receiverAddressDto);
            }
        });

        //删除
        addressAdapter.setOnDeleteListener(new ReceiverAddressAdapter.onDeleteListener() {
            @Override
            public void deleteReceiverAddress(ReceiverAddressDto receiverAddressDto) {
                mPresenter.deleteReceiverAddress(receiverAddressDto);
            }
        });
    }

    @Override
    public void showReceiverAddressList(List<ReceiverAddressDto> receiverAddressDtos) {
        ReceiverAddressAdapter addressAdapter = (ReceiverAddressAdapter) mRecyclerView.getAdapter();
        addressAdapter.addData(receiverAddressDtos);
    }

    @Override
    public void setDefaultReceiverAddressSuccess() {
        mPresenter.loadReceiverAddressData();
    }

    @Override
    public void deleteReceiverSuccess(ReceiverAddressDto receiverAddressDto) {
        ReceiverAddressAdapter adapter = (ReceiverAddressAdapter) mRecyclerView.getAdapter();
        adapter.removeData(receiverAddressDto);
    }

    @OnClick(R.id.btn_create_receiver_address)
    public void createReceiverAddress(View view){
        toNextActivity(CreateReceiverAddressActivity.class);
    }
}
