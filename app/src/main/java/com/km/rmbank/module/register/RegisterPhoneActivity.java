package com.km.rmbank.module.register;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.km.rmbank.R;
import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.DefaultDto;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

public class RegisterPhoneActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {

    @BindView(R.id.et_phone)
    EditText etPhone;

    private int smsCodeType = 0;
    @Override
    protected int getContentView() {
        return R.layout.activity_register_phone;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "填写手机号";
    }
//
    @Override
    public RegisterPresenter getmPresenter() {
        return new RegisterPresenter(this,this);
    }

    @Override
    protected void onCreate() {
        smsCodeType = getIntent().getIntExtra("smsCodeType",0);
    }

    @OnClick(R.id.btn_get_captcha)
    public void getCaptcha(View view){
        String phone = etPhone.getText().toString();
        if (TextUtils.isEmpty(phone) || phone.length() < 11){
            showToast("请输入正确的手机号");
            return;
        }
        mPresenter.getCode(phone);
    }

    @Override
    public void getCodeSuccess() {
        Bundle bundle = new Bundle();
        bundle.putString("mobilePhone",etPhone.getText().toString());
        bundle.putInt("smsCodeType",smsCodeType);
        toNextActivity(RegisterUserActivity.class,bundle);
    }

}
