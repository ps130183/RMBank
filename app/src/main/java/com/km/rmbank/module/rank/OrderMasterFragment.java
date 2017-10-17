package com.km.rmbank.module.rank;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseFragment;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderMasterFragment extends BaseFragment {

    @BindView(R.id.title)
    TextView tvTitle;
    List<String> mdatas;

    public static OrderMasterFragment newInstance(Bundle bundle) {
        OrderMasterFragment fragment = new OrderMasterFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_order_master;
    }

    @Override
    protected void createView() {
        tvTitle.setText("遇见大咖");
//        mdatas.get(1);
    }

}
