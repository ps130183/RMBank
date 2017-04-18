package com.km.rmbank.module;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.utils.FragmentChangeManager;
import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.event.PaySuccessEvent;
import com.km.rmbank.event.UserIsEmptyEvent;
import com.km.rmbank.event.UserNotLoginEvent;
import com.km.rmbank.module.actionarea.ConsultantsNewsFragment;
import com.km.rmbank.module.home.HomeFragment;
import com.km.rmbank.module.login.LoginActivity;
import com.km.rmbank.module.personal.PersonalFragment;
import com.km.rmbank.module.personal.order.MyOrderActivity;
import com.km.rmbank.module.rmshop.RmShopFragment;
import com.km.rmbank.utils.Constant;
import com.ps.androidlib.entity.TabEntity;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class HomeActivity extends BaseActivity {

    private static boolean isExsit = false;

    @BindView(R.id.fl_content_view)
    FrameLayout flContent;

    @BindView(R.id.common_tab_layout)
    CommonTabLayout mTabLayout;
    private List<CustomTabEntity> mTabEntities;

    private String[] mTitle = {"首页","商城","咨询","个人中心"};
    private int[] mSelectedIcon = {R.mipmap.icon_home_rbtn1_pressed,R.mipmap.icon_home_rbtn2_pressed,R.mipmap.icon_home_rbtn3_pressed,R.mipmap.icon_home_rbtn4_pressed};
    private int[] mUnSelectedIcon = {R.mipmap.icon_home_rbtn1_unpress,R.mipmap.icon_home_rbtn2_unpress,R.mipmap.icon_home_rbtn3_unpress,R.mipmap.icon_home_rbtn4_unpress};
    private List<Fragment> fragmentList;
//    @BindView(R.id.radiogroup)
//    RadioGroup radioGroup;

    private int currentPosition = -1;

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
        if (Constant.user.isEmpty()){
            Constant.user.getDataFromSp();
        }
//        AppUtils.initSystemBar(this,0x00000000);
        initTabLayout();
        //退出程序
        setClickKeyCodeBackLisenter(new OnClickKeyCodeBackLisenter() {
            @Override
            public boolean onClickKeyCodeBack() {
                exsit();
                return false;
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        currentPosition = intent.getIntExtra("position",-1);
        if (currentPosition >= 0){
            mTabLayout.setCurrentTab(currentPosition);
        }
    }

    @Override
    protected int getToolBarType() {
        return TOOLBAR_TYPE_HOME;
    }


    private void initTabLayout() {

        fragmentList = new ArrayList<>();
        fragmentList.add(HomeFragment.newInstance(null));
        fragmentList.add(RmShopFragment.newInstance(null));
        fragmentList.add(ConsultantsNewsFragment.newInstance(null));
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


    /**
     * 分享回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void paySuccess(PaySuccessEvent event){
        toNextActivity(HomeActivity.class);
        toNextActivity(MyOrderActivity.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userNoLogin(UserIsEmptyEvent event){
        Constant.user.clear();
        toNextActivity(LoginActivity.class);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userNotLogin(UserNotLoginEvent event){
        mTabLayout.setCurrentTab(0);
    }


    private void exsit(){
        if (isExsit){
            finish();
            System.exit(0);
        } else {
            isExsit = true;
            showToast("再按一次退出程序");
            Observable.timer(2, TimeUnit.SECONDS)
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                            isExsit = false;
                        }
                    });
        }
    }


}
