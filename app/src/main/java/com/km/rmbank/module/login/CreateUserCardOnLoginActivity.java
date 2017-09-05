package com.km.rmbank.module.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.event.LoginSuccessEvent;
import com.km.rmbank.module.Home2Activity;
import com.km.rmbank.utils.Constant;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.EventBusUtils;

import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class CreateUserCardOnLoginActivity extends BaseActivity<CreateUserCardOnLoginPresenter> implements CreateUserCardOnLoginContract.View {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_position)
    EditText etPosition;

    private String phone;
    @Override
    protected int getContentView() {
        return R.layout.activity_create_user_card_on_login;
    }

    @Override
    protected String getTitleName() {
        return "让别人认识你";
    }

    @Override
    public CreateUserCardOnLoginPresenter getmPresenter() {
        return new CreateUserCardOnLoginPresenter(this);
    }

    @Override
    protected void onCreate() {
        phone = getIntent().getStringExtra("phone");
    }

    @OnClick(R.id.btn_submit)
    public void submit(View view){
        String name = etName.getText().toString();
        String position = etPosition.getText().toString();

        if (TextUtils.isEmpty(name)){
            showToast("请填写您的真实姓名");
            return;
        } else if (TextUtils.isEmpty(position)){
            showToast("请填写您的身份");
            return;
        }
        mPresenter.createUserCard(name,position,phone);

    }

    @Override
    public void createUserCardSuccess(String token) {
        Constant.user.getDataFromSp();
        Constant.user.setToken(token);
        Constant.user.saveToSp();
        JPushInterface.setAlias(this, Constant.user.getMobilePhone(), new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Logger.d("极光别名设置成功 = " + s + "    i =" + i);
            }
        });
        EventBusUtils.post(new LoginSuccessEvent());
        toNextActivity(Home2Activity.class);
    }
}
