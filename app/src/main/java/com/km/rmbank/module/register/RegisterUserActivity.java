package com.km.rmbank.module.register;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.module.login.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterUserActivity extends BaseActivity<RegisterUserPresenter> implements RegisterUserContract.View {

    @BindView(R.id.title)
    TextView tvTitle;

    @BindView(R.id.tv_user_phone)
    TextView tvUserPhone;

    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_double_password)
    EditText etDoublePassword;
    @BindView(R.id.et_smscode)
    EditText etSmsCode;

    @BindView(R.id.btn_register)
    Button btnRegister;

    private String userPhone;

    private int smsCodeType;
    @Override
    protected int getContentView() {
        return R.layout.activity_register_user;
    }

    @Override
    public RegisterUserPresenter getmPresenter() {
        return new RegisterUserPresenter(this,this);
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "注册";
    }

    @Override
    protected void onCreate() {
        smsCodeType = getIntent().getIntExtra("smsCodeType",0);
        userPhone = getIntent().getStringExtra("mobilePhone");
        if (!TextUtils.isEmpty(userPhone)){
            tvUserPhone.setText("您的手机号为：" + userPhone);
        } else {
            tvUserPhone.setText("");
        }

        if (smsCodeType == 1){
            btnRegister.setText("注册");
            tvTitle.setText("注册");
        } else if (smsCodeType == 2){
            btnRegister.setText("修改密码");
            tvTitle.setText("忘记密码");
        }
    }

    @OnClick(R.id.btn_register)
    public void register(View view){
        String password = etPassword.getText().toString();
        String dPassword = etDoublePassword.getText().toString();
        String smsCode = etSmsCode.getText().toString();
        if (TextUtils.isEmpty(password)){
            showToast("请填写密码");
            return;
        } else if (TextUtils.isEmpty(dPassword)){
            showToast("请填写重复密码");
            return;
        } else if (!password.equals(dPassword)){
            showToast("密码不一致，请重新填写");
            return;
        } else if (TextUtils.isEmpty(smsCode)){
            showToast("请输入手机验证码");
            return;
        }
        if (smsCodeType == 1){
            mPresenter.registerUser(userPhone,password,smsCode);
        } else if (smsCodeType == 2){
            mPresenter.forgetLoginPwd(userPhone,password,smsCode);
        }
    }

    @Override
    public void registerSuccess() {
        showToast("注册成功，请登录");
        Bundle bundle = new Bundle();
        bundle.putString("mobilePhone",userPhone);
        toNextActivity(LoginActivity.class,bundle);
    }

    @Override
    public void registerFail(String message) {

    }
}
