package com.km.rmbank.module.rank;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseFragment;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankingFragment extends BaseFragment {

    @BindView(R.id.title)
    TextView tvTitle;
    List<String> mdatas;

    public static RankingFragment newInstance(Bundle bundle) {
        RankingFragment fragment = new RankingFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_ranking;
    }

    @Override
    protected void createView() {
        tvTitle.setText("琅琊榜");
//        mdatas.get(1);
    }

}
