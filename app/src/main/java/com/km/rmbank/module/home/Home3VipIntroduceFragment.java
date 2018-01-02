package com.km.rmbank.module.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.webkit.WebView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.module.personal.vip.AlreadyBecomeVip2Activity;
import com.km.rmbank.module.personal.vip.SelectMemberTypeActivity;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.retrofit.SecretConstant;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home3VipIntroduceFragment extends BaseFragment {

//    @BindView(R.id.iv_app_introduce1)
//    ImageView ivAppIntroduce1;
//    @BindView(R.id.iv_app_introduce2)
//    ImageView ivAppIntroduce2;
//    @BindView(R.id.iv_app_introduce3)
//    ImageView ivAppIntroduce3;
//    @BindView(R.id.iv_app_introduce4)
//    ImageView ivAppIntroduce4;
//    @BindView(R.id.iv_app_introduce5)
//    ImageView ivAppIntroduce5;
//
//    private int[] appIntroducesRes = {R.mipmap.ic_app_introduce_1,
//            R.mipmap.ic_app_introduce_2,
//            R.mipmap.ic_app_introduce_3,
//            R.mipmap.ic_app_introduce_4,
//            R.mipmap.ic_app_introduce_5};

    @BindView(R.id.webView)
    WebView webView;

    public static Home3VipIntroduceFragment newInstance(Bundle bundle) {

        Home3VipIntroduceFragment fragment = new Home3VipIntroduceFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home3_vip_introduce;
    }

    @Override
    protected void createView() {
        webView.loadUrl(SecretConstant.API_HOST + SecretConstant.API_HOST_PATH + "/partner/introduce");
    }


    @OnClick(R.id.btn_apply_vip)
    public void applyVip(View view){
        if (Constant.userInfo != null && "2".equals(Constant.userInfo.getRoleId())){
            toNextActivity(AlreadyBecomeVip2Activity.class);
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt("vipType",3);
            toNextActivity(SelectMemberTypeActivity.class,bundle);
        }
    }

//    /**
//     * 体验式
//     * @param view
//     */
//    @OnClick(R.id.btn_apply_vip1)
//    public void onApplyVip1(View view){
//        if ("2".equals(Constant.userInfo.getRoleId())){
//            toNextActivity(AlreadyBecomeVip2Activity.class);
//        } else {
//            Bundle bundle = new Bundle();
//            bundle.putInt("vipType",3);
//            toNextActivity(SelectMemberTypeActivity.class,bundle);
//        }
//
//    }
//
//    /**
//     * 合伙人
//     * @param view
//     */
//    @OnClick(R.id.btn_apply_vip2)
//    public void onApplyVip2(View view){
//        if ("2".equals(Constant.userInfo.getRoleId())){
//            toNextActivity(AlreadyBecomeVip2Activity.class);
//        } else {
//            toNextActivity(SelectMemberTypeActivity.class);
//        }
//    }

}
