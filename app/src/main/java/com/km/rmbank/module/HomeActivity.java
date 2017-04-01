package com.km.rmbank.module;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.FrameLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.dto.UserDto;
import com.km.rmbank.module.actionarea.ActionAreaFragment;
import com.km.rmbank.module.home.HomeFragment;
import com.km.rmbank.module.login.LoginActivity;
import com.km.rmbank.module.personal.PersonalFragment;
import com.km.rmbank.module.rmshop.RmShopFragment;
import com.km.rmbank.utils.Constant;
import com.ps.androidlib.entity.TabEntity;
import com.ps.androidlib.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

public class HomeActivity extends BaseActivity {

//    @BindView(R.id.viewpage)
//    ViewPager viewPager;
//    private ViewPagerTabLayoutAdapter viewPagerAdapter;
    @BindView(R.id.fl_content_view)
    FrameLayout flContent;

    @BindView(R.id.common_tab_layout)
    CommonTabLayout mTabLayout;
    private List<CustomTabEntity> mTabEntities;

    private String[] mTitle = {"首页","人脉商城","活动专区","个人中心"};
    private int[] mSelectedIcon = {R.mipmap.icon_home_rbtn1,R.mipmap.icon_home_rbtn2,R.mipmap.icon_home_rbtn3,R.mipmap.icon_home_rbtn4};
    private int[] mUnSelectedIcon = {R.mipmap.icon_home_rbtn1,R.mipmap.icon_home_rbtn2,R.mipmap.icon_home_rbtn3,R.mipmap.icon_home_rbtn4};

//    @BindView(R.id.radiogroup)
//    RadioGroup radioGroup;

    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "首页";
    }

    @Override
    protected void onCreate() {
        mTabEntities = new ArrayList<>();
        Constant.user.getDataFromSp();
//        AppUtils.initSystemBar(this,0x00000000);
        initTabLayout();
    }

    @Override
    protected int getToolBarType() {
        return TOOLBAR_TYPE_HOME;
    }


    Random mRandom = new Random();

    private void initTabLayout() {

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(HomeFragment.newInstance(null));
        fragmentList.add(RmShopFragment.newInstance(null));
        fragmentList.add(ActionAreaFragment.newInstance(null));
        fragmentList.add(PersonalFragment.newInstance(null));

        for (int i = 0;i < mTitle.length; i++){
            mTabEntities.add(new TabEntity(mTitle[i],mSelectedIcon[i],mUnSelectedIcon[i]));
        }

        mTabLayout.setTabData((ArrayList<CustomTabEntity>) mTabEntities,this,R.id.fl_content_view, (ArrayList<Fragment>) fragmentList);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
//                viewPager.setCurrentItem(position);
                if (position == 3){
                    if (Constant.user.isEmpty()){
                        toNextActivity(LoginActivity.class);
                    }
                }
            }

            @Override
            public void onTabReselect(int position) {
//                if (position == 0) {
//                    mTabLayout.showMsg(0, mRandom.nextInt(100) + 1);
////                    UnreadMsgUtils.show(mTabLayout.getMsgView(0), mRandom.nextInt(100) + 1);
//                }
            }
        });
    }
}
