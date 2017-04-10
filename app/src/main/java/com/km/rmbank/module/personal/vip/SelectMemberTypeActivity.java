package com.km.rmbank.module.personal.vip;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.MemberTypeDto;
import com.km.rmbank.module.personal.shopcart.payment.PaymentActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectMemberTypeActivity extends BaseActivity<SelectMemberTypePresenter> implements SelectMemberTypeContract.View {

    @BindView(R.id.tv_member1)
    TextView tvMember1;
    @BindView(R.id.tv_member2)
    TextView tvMember2;


    @BindView(R.id.tv_member_name)
    TextView tvMemberName;
    @BindView(R.id.tv_member_intro)
    TextView tvMemberIntro;

    private String amount;//支付金额
    private String[] memberNames = {"体验式会员","合伙人会员"};
    private String[] memberTypeIntros = {"体验式会员 介绍，体验式会员 介绍，体验式会员 介绍，体验式会员 介绍，体验式会员 介绍，体验式会员 介绍，体验式会员 介绍，",
    "合伙人会员介绍,合伙人会员介绍,合伙人会员介绍,合伙人会员介绍,合伙人会员介绍,合伙人会员介绍,合伙人会员介绍,合伙人会员介绍,合伙人会员介绍,"};
    private List<MemberTypeDto> memberTypeDtos;
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
    public SelectMemberTypePresenter getmPresenter() {
        return new SelectMemberTypePresenter(this);
    }

    @Override
    protected void onCreate() {
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
            amount = memberTypeDtos.get(0).getMemberMoney();
        } else {
            tvMember1.setBackgroundResource(R.drawable.shape_member_type_unselected);
            tvMember1.setTextColor(getResources().getColor(R.color.color_red));

            tvMember2.setBackgroundResource(R.drawable.shape_member_type_selected);
            tvMember2.setTextColor(getResources().getColor(R.color.color_white));

            tvMemberName.setText(memberNames[1]);
            tvMemberIntro.setText(memberTypeIntros[1]);
            amount = memberTypeDtos.get(1).getMemberMoney();
        }
    }

    @OnClick(R.id.btn_become_member)
    public void becomeMember(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("paymentForObj",1);
        bundle.putString("amount",amount);
        toNextActivity(PaymentActivity.class,bundle);
    }

    @Override
    public void showMemberTypeInfo(List<MemberTypeDto> memberTypeDtos) {
        this.memberTypeDtos = memberTypeDtos;
        MemberTypeDto memberTypeDto1 = memberTypeDtos.get(0);
        MemberTypeDto memberTypeDto2 = memberTypeDtos.get(1);
        memberNames[0] += "(" + memberTypeDto1.getMemberMoney() + ")";
        memberNames[1] += "(" + memberTypeDto2.getMemberMoney() + ")";

        memberTypeIntros[0] = memberTypeDto1.getExperience();
        memberTypeIntros[1] = memberTypeDto2.getPartner();
        selectMember(2);
    }
}
