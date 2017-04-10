package com.km.rmbank.module.personal.shopcart.payment;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.alipay.AlipayUtils;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.AlipayParamsDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.dto.WeiCharParamsDto;
import com.km.rmbank.wxpay.WxUtil;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class PaymentActivity extends BaseActivity<PayPresenter> implements PayContract.View {

    private boolean[] paymentTypes = {false,false,false,false};

    @BindView(R.id.cb_balance)
    CheckBox cbBalance;
    @BindView(R.id.cb_weichat)
    CheckBox cbWeiChat;
    @BindView(R.id.cb_alipay)
    CheckBox cbAlipay;
    @BindView(R.id.cb_bank)
    CheckBox cbBank;

    @BindView(R.id.ll_pay_balance)
    RelativeLayout llPayBalance;
    @BindView(R.id.ll_intergal)
    LinearLayout llIntergal;

    @BindView(R.id.tv_amount)
    TextView tvAmount;

    private PayOrderDto mPayOrderDto;
    //为0：商品 支付  1：认证会员支付
    private int paymentForObj;
    private String mAmount;//支付金额

    @Override
    protected int getContentView() {
        return R.layout.activity_payment;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "收银台";
    }

    @Override
    public PayPresenter getmPresenter() {
        return new PayPresenter(this);
    }

    @Override
    protected void onCreate() {
        init();
        mPresenter.createPayOrder(mAmount);
    }

    private void init(){
        paymentForObj = getIntent().getIntExtra("paymentForObj",0);
        mAmount = getIntent().getStringExtra("amount");

        if (paymentForObj == 1){//认证会员支付时，隐藏余额支付 和 积分
            llPayBalance.setVisibility(View.GONE);
            llIntergal.setVisibility(View.GONE);
        }
    }

    @OnCheckedChanged({R.id.cb_balance,R.id.cb_weichat,R.id.cb_alipay,R.id.cb_bank})
    public void  choosePaymentType(CompoundButton buttonView, boolean isChecked){
        if (isChecked){
            switch (buttonView.getId()){
                case R.id.cb_balance:
                    refreshPaymentType(0);
                    break;
                case R.id.cb_weichat:
                    refreshPaymentType(1);
                    break;
                case R.id.cb_alipay:
                    refreshPaymentType(2);
                    break;
                case R.id.cb_bank:
                    refreshPaymentType(3);
                    break;
            }
        }

    }
    private void refreshPaymentType(int position){
        for (int i = 0; i < paymentTypes.length; i++){
            if (position == i){
                paymentTypes[i] = true;
            } else {
                paymentTypes[i] = false;
            }
        }
        cbBalance.setChecked(paymentTypes[0]);
        cbWeiChat.setChecked(paymentTypes[1]);
        cbAlipay.setChecked(paymentTypes[2]);
        cbBank.setChecked(paymentTypes[3]);
    }

    @OnClick(R.id.btn_to_pay)
    public void toPay(View view){
        int position = -1;
        for (int i = 0; i < paymentTypes.length; i++){
            if (paymentTypes[i]){
                position = i;
            }
        }
        switch (position){
            case 0://余额
                break;
            case 1://微信
                mPresenter.getWeiChatParams(mPayOrderDto.getPayNumber());
                break;
            case 2://支付宝
//                AlipayUtils
                mPresenter.getAliPayOrder(mPayOrderDto.getPayNumber());
                break;
            case 3://银行卡
                break;

            default:
                showToast("请选择支付方式");
                break;
        }
    }

    @Override
    public void createPayOrderSuccess(PayOrderDto payOrderDto) {
        mPayOrderDto = payOrderDto;
        tvAmount.setText(payOrderDto.getSumPrice() + "元");
    }

    @Override
    public void getAlipayParamsSuccess(String alipayParamsDto) {
        AlipayUtils.toPay(PaymentActivity.this,alipayParamsDto)
                .subscribe(new Consumer() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Object o) throws Exception {

                    }
                });
    }

    @Override
    public void getWeiCharParamsSuccess(WeiCharParamsDto weicharParams) {
//        Logger.d(weicharParams.toString());
        if (WxUtil.check(this,WxUtil.getWXAPI(this))){
            WxUtil.toPayByWXAPI(weicharParams);
        }
    }
}
