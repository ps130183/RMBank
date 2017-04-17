package com.km.rmbank.module.guide;

import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.module.HomeActivity;
import com.ps.androidlib.utils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by kamangkeji on 17/4/15.
 */

public class GuideFragment extends BaseFragment {


    @Override
    protected int getContentView() {
        return R.layout.fragment_guide;
    }

    @Override
    protected void createView() {

    }

    @OnClick(R.id.to_home)
    public void toHome(View view){
        toNextActivity(HomeActivity.class);
        SPUtils.getInstance().put("isFirst",false);
        getActivity().finish();
    }
}
