package com.km.rmbank.module.login;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.module.HomeActivity;
import com.km.rmbank.module.register.RegisterPhoneActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;

    private String mobilePhone;
    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "登录";
    }

    @Override
    public LoginPresenter getmPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void onCreate() {
        mobilePhone = getIntent().getStringExtra("mobilePhone");
        etPhone.setText(TextUtils.isEmpty(mobilePhone) ? "" : mobilePhone);
    }

    /**
     * 登录
     * @param view
     */
    @OnClick(R.id.btn_login)
    public void login(View view){
        String phone = etPhone.getText().toString();
        String pwd = etPassword.getText().toString();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(pwd)){
            showToast("请填写手机号或密码");
            return;
        }
        mPresenter.login(phone,pwd);
    }

    @Override
    public void loginSuccess() {
        showToast("登录成功");
        toNextActivity(HomeActivity.class);
    }

    /**
     * 注册
     * @param view
     */
    @OnClick(R.id.tv_register)
    public void register(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("smsCodeType",1);
        toNextActivity(RegisterPhoneActivity.class,bundle);
    }

    /**
     * 忘记密码
     * @param view
     */
    @OnClick(R.id.tv_forget_loginpwd)
    public void forgetLoginPwd(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("smsCodeType",2);
        toNextActivity(RegisterPhoneActivity.class,bundle);
    }

}
