package com.km.rmbank.module.home.image;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.km.rmbank.R;
import com.km.rmbank.adapter.ViewPagerAdapter;
import com.km.rmbank.basic.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PictureShowActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    private List<String> pictureUrls;

    @Override
    protected int getContentView() {
        return R.layout.activity_picture_show;
    }

    @Override
    protected int getToolBarType() {
        return BaseActivity.TOOLBAR_TYPE_HOME;
    }

    @Override
    protected String getTitleName() {
        return null;
    }

    @Override
    protected void onCreate() {
        pictureUrls = getIntent().getStringArrayListExtra("pictureUrls");
        initViewPager();
    }

    private void initViewPager(){

        List<Fragment> fragmentList = new ArrayList<>();
        for (String pictureUrl : pictureUrls){
            Bundle bundle = new Bundle();
            bundle.putString("pictureUrl",pictureUrl);
            fragmentList.add(PictureShowFragment.newInstance(bundle));
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentList);
        mViewPager.setAdapter(adapter);
    }
}
