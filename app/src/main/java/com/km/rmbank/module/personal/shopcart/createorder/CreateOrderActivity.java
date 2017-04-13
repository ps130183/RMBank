package com.km.rmbank.module.personal.shopcart.createorder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.ShoppingCartParentListAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.dto.ShoppingCartDto;
import com.km.rmbank.event.OtherAddressEvent;
import com.km.rmbank.module.personal.receiveraddress.ReceiverAddressActivity;
import com.km.rmbank.module.personal.shopcart.payment.PaymentActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CreateOrderActivity extends BaseActivity<CreateOrderPresenter> implements CreateOrderContact.View {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private ShoppingCartParentListAdapter adapter;

    @BindView(R.id.tv_receiver_name)
    TextView tvReceiverName;
    @BindView(R.id.tv_receiver_phone)
    TextView tvReceiverPhone;
    @BindView(R.id.tv_receiver_address)
    TextView tvReceiverAddress;

    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;

    private List<ShoppingCartDto> shoppingCartDtos;
    private ReceiverAddressDto receiverAddressDto;

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
        return new CreateOrderPresenter(this);
    }

    @Override
    protected void onCreate() {
        shoppingCartDtos = getIntent().getParcelableArrayListExtra("checkedGoods");
        initRv();
        showOrderDatas(shoppingCartDtos);
    }

    private void initRv(){
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView);
        adapter = new ShoppingCartParentListAdapter(this);
        adapter.setShowFreight(true);
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    public void showDefaultReceiverAddress(ReceiverAddressDto receiverAddressDto) {
        this.receiverAddressDto = receiverAddressDto;
        tvReceiverName.setText(receiverAddressDto.getReceivePerson());
        tvReceiverPhone.setText(receiverAddressDto.getReceivePersonPhone());
        tvReceiverAddress.setText(receiverAddressDto.getReceiveAddress());
    }

    @Override
    public void showOrderDatas(List<ShoppingCartDto> cartEntities) {
        adapter.addData(cartEntities);
        tvTotalMoney.setText(adapter.getTotalMoneyCheckedGoods());
    }

    @Override
    public void submitOrderSuccess(PayOrderDto payOrderDto) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("payOrderDto",payOrderDto);
        toNextActivity(PaymentActivity.class,bundle);
    }

    @OnClick(R.id.tv_submit_order)
    public void submitOrder(View view){
        String[] params = getOrderParams();
        mPresenter.submitOrder(params[0],params[1],receiverAddressDto.getId(),params[2],params[3]);
//        toNextActivity(PaymentActivity.class);
    }

    @OnClick({R.id.tv_receiver_name,R.id.tv_receiver_phone,R.id.tv_receiver_address,R.id.iv_other_address})
    public void getOtherAddress(View view){
        Bundle bundle = new Bundle();
        bundle.putBoolean("select_other_address",true);
        toNextActivity(ReceiverAddressActivity.class,bundle);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void otherAddress(OtherAddressEvent event){
        showDefaultReceiverAddress(event.getReceiverAddressDto());
    }

    /**
     * 获取提交订单 的相应参数
     * @return
     */
    private String[] getOrderParams(){
        List<ShoppingCartDto> shoppingCartDtos = adapter.getAllData();
        String[] params = new String[4];
        StringBuffer productNos = new StringBuffer();
        StringBuffer productCounts = new StringBuffer();
        BigDecimal freight = new BigDecimal("0");
        for (ShoppingCartDto cartDto : shoppingCartDtos){
            freight = freight.add(new BigDecimal(cartDto.getFreight()));
            for (GoodsDetailsDto detailsDto : cartDto.getProductList()){
                productNos.append(detailsDto.getProductNo()).append("#");
                productCounts.append(detailsDto.getProductInShopCarCount()).append("#");
            }
        }

        productNos.replace(productNos.length() - 1, productNos.length(), "");
        productCounts.replace(productCounts.length() - 1, productCounts.length(), "");
        params[0] = productNos.toString();
        params[1] = productCounts.toString();
        params[2] = String.valueOf(freight.doubleValue());
        params[3] = "0";
        return params;
    }
}
