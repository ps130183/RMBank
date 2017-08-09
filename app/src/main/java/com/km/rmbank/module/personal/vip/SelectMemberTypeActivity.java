package com.km.rmbank.module.personal.vip;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.MemberTypeDto;
import com.km.rmbank.module.payment.PaymentActivity;
import com.km.rmbank.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectMemberTypeActivity extends BaseActivity<SelectMemberTypePresenter> implements SelectMemberTypeContract.View {

    @BindView(R.id.rl_vip1)
    RelativeLayout rlVip1;

    @BindView(R.id.tv_member1)
    TextView tvMember1;
    @BindView(R.id.tv_member2)
    TextView tvMember2;


    @BindView(R.id.tv_member_name)
    TextView tvMemberName;
    @BindView(R.id.tv_member_intro)
    TextView tvMemberIntro;

    @BindView(R.id.btn_become_member)
    Button btnBecomeMember;

    @BindView(R.id.rg_vip2_money)
    RadioGroup rgVip2Money;

    @BindView(R.id.tv_vip_number)
    TextView tvVipNumber;
    private int vipType = 0;
    private int memberType = 0;
    private String amount;//支付金额
    private String[] memberNames = {"* 体验式会员","* 合伙人会员"};
    private String[] memberTypeIntros = {"1.成为玩转地球尊贵体验官，可以获得积分180购买商城中所有商品。\n" +
            "2.免费参加玩转地球举办的全部活动，红酒俱乐部、旗袍俱乐部等等。\n" ,
            "1.拥有玩转地球尊贵合伙人身份。\n" +
            "2.可以建立属于您自己的俱乐部。\n" +
            "3.平台全部资源可免费试用。\n" +
            "4.发布商品供用户购买。\n" +
            "5.打造自己的人脉银行。\n" +
                    "6.商品销售提成机制。"};
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
        vipType = getIntent().getIntExtra("vipType",0);
        initRgVip2Money();
    }

    @OnClick({R.id.tv_member1,R.id.iv_member1})
    public void selectMember1(View view){
        selectMember(3);
    }

    @OnClick({R.id.tv_member2,R.id.iv_member2})
    public void selectMember2(View view){
        selectMember(2);
    }

    private void selectMember(int memberType){
        this.memberType = memberType;
        if (memberType == 3){//体验式
            if ("3".equals(Constant.userInfo.getRoleId()) || "2".equals(Constant.user.getRoleId())){
                btnBecomeMember.setVisibility(View.GONE);
            } else {
                btnBecomeMember.setVisibility(View.VISIBLE);
            }
            tvMember1.setBackgroundResource(R.drawable.shape_member_type_selected);
            tvMember1.setTextColor(getResources().getColor(R.color.color_white));
            tvVipNumber.setTextColor(getResources().getColor(R.color.colorPrimary));

            tvMember2.setBackgroundResource(R.drawable.shape_member_type_unselected);
            tvMember2.setTextColor(getResources().getColor(R.color.colorPrimary));

            tvMemberName.setText(memberNames[0]);
            tvMemberIntro.setText(memberTypeIntros[0]);
            amount = memberTypeDtos.get(0).getMemberMoney();

            rgVip2Money.setVisibility(View.GONE);
        } else {//合伙人
            if ("2".equals(Constant.userInfo.getRoleId())){
                btnBecomeMember.setVisibility(View.GONE);
                rgVip2Money.setVisibility(View.GONE);
            } else {
                btnBecomeMember.setVisibility(View.VISIBLE);
                rgVip2Money.setVisibility(View.VISIBLE);
            }
            tvMember1.setBackgroundResource(R.drawable.shape_member_type_unselected);
            tvMember1.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvVipNumber.setTextColor(getResources().getColor(R.color.color_white));

            tvMember2.setBackgroundResource(R.drawable.shape_member_type_selected);
            tvMember2.setTextColor(getResources().getColor(R.color.color_white));

            tvMemberName.setText(memberNames[1]);
            tvMemberIntro.setText(memberTypeIntros[1]);
            amount = memberTypeDtos.get(1).getMemberMoney();

            int moneyDifference = memberTypeDtos.get(1).getMoneyDifference();
            if (moneyDifference == 20000){
                selectVip2Money(0);
            } else if (moneyDifference < 20000 && moneyDifference >= 10000){
                selectVip2Money(1);
            } else if (moneyDifference < 10000 && moneyDifference >= 5000){
                selectVip2Money(2);
            } else {
                selectVip2Money(3);
            }
        }
    }

    @OnClick(R.id.btn_become_member)
    public void becomeMember(View view){
        if (Constant.isPay){
            showToast("支付暂未开通");
            return;
        }
        float money = Float.parseFloat(amount);
        if (memberType == 2 && money > memberTypeDtos.get(1).getMoneyDifference()){
            showToast("您选择的金额大于所需支付的金额");
            return;
        }

        Bundle bundle = new Bundle();
        if (memberType == 2 && money == memberTypeDtos.get(1).getMoneyDifference()){
            bundle.putBoolean("becomeVip2",true);
        }
        bundle.putInt("paymentForObj",memberType);
        bundle.putString("amount",amount);
        toNextActivity(PaymentActivity.class,bundle);
    }

    @Override
    public void showMemberTypeInfo(List<MemberTypeDto> memberTypeDtos) {
        this.memberTypeDtos = memberTypeDtos;
        MemberTypeDto memberTypeDto1 = memberTypeDtos.get(0);
        MemberTypeDto memberTypeDto2 = memberTypeDtos.get(1);
        memberNames[0] += "(" + memberTypeDto1.getMemberMoney() + "元)";
        memberNames[1] += "(" + memberTypeDto2.getMemberMoney() + "元,还需支付" + memberTypeDto2.getMoneyDifference() + "元)";

        tvVipNumber.setText("剩余名额：" + memberTypeDto2.getResidueMember() + "位");

//        memberTypeIntros[0] = memberTypeDto1.getExperience();
//        memberTypeIntros[1] = memberTypeDto2.getPartner();
//        if ("3".equals(Constant.user.getRoleId())){
//            rlVip1.setVisibility(View.GONE);
//        }
        if (vipType > 0){
            selectMember(vipType);
        } else {
            selectMember(2);
        }
    }

    /**
     * 初始化 合伙人 选择付款金额  监听
     */
    private void initRgVip2Money(){
        rgVip2Money.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rbtn_money1:
                        amount = "20000";
                        break;
                    case R.id.rbtn_money2:
                        amount = "10000";
                        break;
                    case R.id.rbtn_money3:
                        amount = "5000";
                        break;
                    case R.id.rbtn_money4:
                        amount = "2000";
                        break;
                }
            }
        });
    }

    private void selectVip2Money(int position){
        ((RadioButton)rgVip2Money.getChildAt(position)).setChecked(true);
    }
}
