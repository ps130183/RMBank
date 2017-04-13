package com.km.rmbank.module.rmshop.goods;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseFragment;

public class GoodsEvaluateFragment extends BaseFragment {

    public static GoodsEvaluateFragment newInstance(Bundle bundle) {
        GoodsEvaluateFragment fragment = new GoodsEvaluateFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_goods_evaluate;
    }

    @Override
    protected void createView() {

    }
}
