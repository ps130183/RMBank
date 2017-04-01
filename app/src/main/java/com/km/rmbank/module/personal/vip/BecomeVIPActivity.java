package com.km.rmbank.module.personal.vip;

import android.support.annotation.NonNull;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;

import butterknife.OnClick;

public class BecomeVIPActivity extends BaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_become_vip;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "认证声明";
    }

    @Override
    protected void onCreate() {

    }

    @OnClick(R.id.btn_agree)
    public void agree(View view){
        toNextActivity(SelectMemberTypeActivity.class);
    }
}
