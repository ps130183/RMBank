package com.km.rmbank.module.personal.vip;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.module.personal.shopcart.payment.PaymentActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectMemberTypeActivity extends BaseActivity {

    @BindView(R.id.tv_member1)
    TextView tvMember1;
    @BindView(R.id.tv_member2)
    TextView tvMember2;


    @BindView(R.id.tv_member_name)
    TextView tvMemberName;
    @BindView(R.id.tv_member_intro)
    TextView tvMemberIntro;

    private String[] memberNames = {"体验式会员(1980元)","合伙人会员(20000元)"};
    private String[] memberTypeIntros = {"体验式会员 介绍，体验式会员 介绍，体验式会员 介绍，体验式会员 介绍，体验式会员 介绍，体验式会员 介绍，体验式会员 介绍，",
    "合伙人会员介绍,合伙人会员介绍,合伙人会员介绍,合伙人会员介绍,合伙人会员介绍,合伙人会员介绍,合伙人会员介绍,合伙人会员介绍,合伙人会员介绍,"};
    @Override
    protected int getContentView() {
        return R.layout.activity_select_member_type;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "选择身份";
    }

    @Override
    protected void onCreate() {
        selectMember(2);
    }

    @OnClick({R.id.tv_member1,R.id.iv_member1})
    public void selectMember1(View view){
        selectMember(1);
    }

    @OnClick({R.id.tv_member2,R.id.iv_member2})
    public void selectMember2(View view){
        selectMember(2);
    }

    private void selectMember(int memberType){
        if (memberType == 1){
            tvMember1.setBackgroundResource(R.drawable.shape_member_type_selected);
            tvMember1.setTextColor(getResources().getColor(R.color.color_white));

            tvMember2.setBackgroundResource(R.drawable.shape_member_type_unselected);
            tvMember2.setTextColor(getResources().getColor(R.color.color_red));

            tvMemberName.setText(memberNames[0]);
            tvMemberIntro.setText(memberTypeIntros[0]);
        } else {
            tvMember1.setBackgroundResource(R.drawable.shape_member_type_unselected);
            tvMember1.setTextColor(getResources().getColor(R.color.color_red));

            tvMember2.setBackgroundResource(R.drawable.shape_member_type_selected);
            tvMember2.setTextColor(getResources().getColor(R.color.color_white));

            tvMemberName.setText(memberNames[1]);
            tvMemberIntro.setText(memberTypeIntros[1]);
        }
    }

    @OnClick(R.id.btn_become_member)
    public void becomeMember(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("paymentForObj",1);
        toNextActivity(PaymentActivity.class,bundle);
    }
}
