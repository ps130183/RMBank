package com.km.rmbank.module.home;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.ps.commonadapter.adapter.CommonAdapter;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home3BenefitShareFragment extends BaseFragment {


    @BindView(R.id.rv_benefit_share)
    RecyclerView rvBenefitShare;

    private String[] actionNames = {"99元基地活动体验官","会议活动支付页面"};
    private int[] actionNameBgs = {Color.RED,Color.GRAY,Color.rgb(255,200,0),Color.BLUE};
    public Home3BenefitShareFragment() {
        // Required empty public constructor
    }

    public static Home3BenefitShareFragment newInstance(Bundle bundle) {

        Home3BenefitShareFragment fragment = new Home3BenefitShareFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home3_benefit_share;
    }

    @Override
    protected void createView() {
        init();
    }

    private void init(){

        List<String> mDatas = new ArrayList<>();
        for (int i = 0; i < 2; i++){
            mDatas.add(actionNames[i]);
        }

        CommonAdapter<String> commonAdapter = new CommonAdapter<String>(getActivity(),mDatas,R.layout.item_rv_benefit_share) {
            @Override
            public void convert(CommonViewHolder holder, String mData) {
                holder.setText(R.id.tv_action_name,mData);
                Random random = new Random();
                int backColor = random.nextInt(4) % 4;
                holder.findView(R.id.tv_action_name).setBackgroundColor(actionNameBgs[backColor]);
            }
        };

        RecyclerAdapterHelper<String> mHelper = new RecyclerAdapterHelper<>(rvBenefitShare);
        mHelper.addLinearLayoutManager()
                .addAdapter(commonAdapter)
                .addEmptyWrapper(null)
                .create();
    }

}
