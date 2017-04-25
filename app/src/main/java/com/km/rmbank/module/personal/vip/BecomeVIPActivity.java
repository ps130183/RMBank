package com.km.rmbank.module.personal.vip;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class BecomeVIPActivity extends BaseActivity {

    @BindView(R.id.tv_content1)
    TextView tvContent1;
    @BindView(R.id.tv_content2)
    TextView tvContent2;

    private String content1 = "1、用户在享受会员服务后发布的任何内容并不反映或代表玩转地球的观点。\n" +
            "2、会员类型选择后将不可以改变，请选择您最中意的会员类型。\n" +
            "3、用户须对利用“玩转地球”账号或本服务传送信息的真实性、合法性、准确性、真实性等全权负责";

    private String content2 = "4、本协议的任何条款无论因何种原因无效或不具可执行性，其余条款仍有效，对双方具有约束力。\n" +
            "5、请认真阅读会员类型不同而享受的不同服务，该服务由玩转地球官方提供服务内容。\n" +
            "6、审核相关用户使用中遇到的问题会在1-3个工作日解决，希望玩转地球给您带来不凡的体验。";

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
        tvContent1.setText(content1);
        tvContent2.setText(content2);
    }

    @OnClick(R.id.btn_agree)
    public void agree(View view){
        toNextActivity(SelectMemberTypeActivity.class);
    }
}
