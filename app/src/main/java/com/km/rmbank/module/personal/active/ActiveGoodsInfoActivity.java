package com.km.rmbank.module.personal.active;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.ActiveGoodsOrderDetailDto;
import com.km.rmbank.dto.ActiveGoodsOrderListDto;
import com.ps.androidlib.utils.glide.GlideUtils;

import butterknife.BindView;

public class ActiveGoodsInfoActivity extends BaseActivity<ActiveGoodsInfoPresenter> implements ActiveGoodsInfoContract.View {

    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;

    @BindView(R.id.iv_goods_image)
    ImageView ivGoodsImage;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;

    @BindView(R.id.ll_receiver)
    LinearLayout llReceiver;
    @BindView(R.id.tv_receiver_name)
    TextView tvReceiverName;
    @BindView(R.id.tv_receiver_phone)
    TextView tvReceiverPhone;
    @BindView(R.id.tv_receiver_address)
    TextView tvReceiverAddress;

    @BindView(R.id.ll_express)
    LinearLayout llExpress;
    @BindView(R.id.tv_express_company)
    TextView tvExpressCompany;
    @BindView(R.id.tv_express_number)
    TextView tvExpressNumber;


    private ActiveGoodsOrderListDto mOrderList;
    @Override
    protected int getContentView() {
        return R.layout.activity_active_goods_info;
    }

    @Override
    protected String getTitleName() {
        return "商品信息";
    }

    @Override
    public ActiveGoodsInfoPresenter getmPresenter() {
        return new ActiveGoodsInfoPresenter(this);
    }

    @Override
    protected void onCreate() {
        mOrderList = getIntent().getParcelableExtra("orderDetail");
        mPresenter.getActiveGoodsOrderDetail(mOrderList.getOrderNo());
    }

    @Override
    public void showActiveGoodsOrderDetail(ActiveGoodsOrderDetailDto orderDetailDto) {
        tvOrderNumber.setText("订单号：" + orderDetailDto.getOrderNo());
        GlideUtils.loadImage(ivGoodsImage,mOrderList.getThumbnailUrl());
        tvGoodsName.setText(mOrderList.getName());
        tvGoodsPrice.setText(mOrderList.getActiveValue() + "  *  " + orderDetailDto.getProductCount());

        llReceiver.setVisibility(View.VISIBLE);
        tvReceiverName.setText(orderDetailDto.getReceivePerson());
        tvReceiverPhone.setText(orderDetailDto.getReceivePersonPhone());
        tvReceiverAddress.setText(orderDetailDto.getReceiveAddress());

//        if (TextUtils.isEmpty(orderDetailDto.get))
        if (orderDetailDto.getStatus() == 2){
            llExpress.setVisibility(View.VISIBLE);
            tvExpressCompany.setText(orderDetailDto.getExpressCompany());
            tvExpressNumber.setText(orderDetailDto.getCourierNumber());
        }
    }
}
