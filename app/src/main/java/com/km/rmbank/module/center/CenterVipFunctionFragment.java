package com.km.rmbank.module.center;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.module.club.ClubInfoActivity;
import com.km.rmbank.module.club.EditMyClubActivity;
import com.km.rmbank.module.personal.account.UserAccountActivity;
import com.km.rmbank.module.personal.goodsmanager.GoodsManagerActivity;
import com.km.rmbank.module.personal.integral.MyIntegralActivity;
import com.km.rmbank.module.personal.team.MyTeamActivity;
import com.km.rmbank.utils.Constant;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CenterVipFunctionFragment extends BaseFragment {


    public static CenterVipFunctionFragment newInstance(Bundle bundle) {
        CenterVipFunctionFragment fragment = new CenterVipFunctionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_center_vip_function;
    }

    @Override
    protected void createView() {

    }

    /**
     * 我的团队
     * @param view
     */
    @OnClick(R.id.tv_my_team)
    public void clickMyTeam(View view){
        toNextActivity(MyTeamActivity.class);
    }

    /**
     * 商品管理
     * @param view
     */
    @OnClick(R.id.tv_goods_manager)
    public void clickGoodsManager(View view){
        toNextActivity(GoodsManagerActivity.class);
    }

    /**
     * 我的俱乐部
     * @param view
     */
    @OnClick(R.id.tv_my_club)
    public void clickMyClub(View view){
        if (Constant.userInfo.getClubStatus() == 0){
            toNextActivity(EditMyClubActivity.class);
        } else {
            Bundle bundle = new Bundle();
            bundle.putBoolean("isMyClub",true);
            toNextActivity(ClubInfoActivity.class,bundle);
        }
    }

    /**
     * 我的账户
     * @param view
     */
    @OnClick(R.id.tv_my_account)
    public void clickMyAccount(View view){
        toNextActivity(UserAccountActivity.class);
    }

    /**
     * 我的积分
     * @param view
     */
    @OnClick(R.id.tv_my_integral)
    public void clickMyIntegral(View view){
        toNextActivity(MyIntegralActivity.class);
    }


}
