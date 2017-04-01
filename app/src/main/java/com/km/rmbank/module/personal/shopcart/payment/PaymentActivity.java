package com.km.rmbank.module.personal.shopcart.payment;

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

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;

import butterknife.BindView;
import butterknife.OnCheckedChanged;

public class PaymentActivity extends BaseActivity {

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

    //为0：商品 支付  1：认证会员支付
    private int paymentForObj;

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
    protected void onCreate() {
        init();
    }

    private void init(){
        paymentForObj = getIntent().getIntExtra("paymentForObj",0);
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
}
