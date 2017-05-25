package com.km.rmbank.module.rmshop.goods;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.module.rmshop.RmShopNewFragment;

import butterknife.BindView;

public class RmShopActivity extends BaseActivity {


    @BindView(R.id.fl_content)
    FrameLayout flContent;

    @Override
    protected int getContentView() {
        return R.layout.activity_rm_shop;
    }

    @Override
    protected String getTitleName() {
        return null;
    }

    @Override
    protected int getToolBarType() {
        return TOOLBAR_TYPE_HOME;
    }

    @Override
    protected void onCreate() {
        Bundle bundle = getIntent().getExtras();
        bundle.putBoolean("isFromHome",true);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl_content, RmShopNewFragment.newInstance(bundle));
        ft.commit();

    }
}
