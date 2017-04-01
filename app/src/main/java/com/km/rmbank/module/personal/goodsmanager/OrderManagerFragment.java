package com.km.rmbank.module.personal.goodsmanager;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseFragment;

public class OrderManagerFragment extends BaseFragment {

    public static OrderManagerFragment newInstance(Bundle args) {
        OrderManagerFragment fragment = new OrderManagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_order_manager;
    }

    @Override
    protected void createView() {

    }
}
