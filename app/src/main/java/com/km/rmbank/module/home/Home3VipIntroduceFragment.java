package com.km.rmbank.module.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.module.personal.vip.SelectMemberTypeActivity;

import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home3VipIntroduceFragment extends BaseFragment {

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

    }

    /**
     * 体验式
     * @param view
     */
    @OnClick(R.id.btn_apply_vip1)
    public void onApplyVip1(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("vipType",3);
        toNextActivity(SelectMemberTypeActivity.class,bundle);
    }

    /**
     * 合伙人
     * @param view
     */
    @OnClick(R.id.btn_apply_vip2)
    public void onApplyVip2(View view){
        toNextActivity(SelectMemberTypeActivity.class);
    }

}
