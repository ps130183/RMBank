package com.km.rmbank.module;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseUserChatUtils;
import com.hyphenate.exceptions.HyphenateException;
import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.dto.MyFriendsDto;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.entity.TeamEntity;
import com.km.rmbank.event.DownloadAppEvent;
import com.km.rmbank.event.LoginSuccessEvent;
import com.km.rmbank.event.PaySuccessEvent;
import com.km.rmbank.event.UserIsEmptyEvent;
import com.km.rmbank.module.actionarea.InformationFragment;
import com.km.rmbank.module.home.HomeFragment;
import com.km.rmbank.module.home.HomeNewFragment;
import com.km.rmbank.module.login.LoginActivity;
import com.km.rmbank.module.nearbyvip.NearbyVipActivity;
import com.km.rmbank.module.personal.PersonalFragment;
import com.km.rmbank.module.personal.PersonalNewFragment;
import com.km.rmbank.module.personal.order.MyOrderActivity;
import com.km.rmbank.module.personal.userinfo.EditUserCardActivity;
import com.km.rmbank.module.rmshop.RmShopFragment;
import com.km.rmbank.module.rmshop.RmShopNewFragment;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.UmengShareUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.EventBusUtils;
import com.startsmake.mainnavigatetabbar.widget.MainNavigateTabBar;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class HomeNewActivity extends BaseActivity<HomePresenter> implements HomeContract.View {

    public final static int REQUEST_PERMISSION_CAMERA = 1;
    public final static int REQUEST_PERMISSION_LOCATION = 2;
    private static boolean isExsit = false;

    private static final String TAG_PAGE_HOME = "首页";
    private static final String TAG_PAGE_CITY = "商城";
    private static final String TAG_PAGE_PUBLISH = "  ";
    private static final String TAG_PAGE_MESSAGE = "咨讯";
    private static final String TAG_PAGE_PERSON = "我的";

    private ShareDto shareDto;

    private int[] mSelectedIcon = {R.mipmap.icon_home_rbtn1_pressed, R.mipmap.icon_home_rbtn2_pressed, R.mipmap.icon_home_rbtn3_pressed, R.mipmap.icon_home_rbtn4_pressed};
    private int[] mUnSelectedIcon = {R.mipmap.icon_home_rbtn1_unpress, R.mipmap.icon_home_rbtn2_unpress, R.mipmap.icon_home_rbtn3_unpress, R.mipmap.icon_home_rbtn4_unpress};

    @BindView(R.id.mainTabBar)
    MainNavigateTabBar mNavigateTabBar;

    @BindView(R.id.tab_post_icon)
    ImageView mTabPostIcon;

    @BindView(R.id.rl_main_dialog)
    RelativeLayout clMainDialog;

    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.iv_share)
    ImageView ivShare;

    @BindView(R.id.tv_rich_scan)
    TextView tvRichScan;
    @BindView(R.id.tv_near_partner)
    TextView tvNearPartner;

    @BindView(R.id.rl_play_card)
    RelativeLayout rlPlayCard;

    @BindView(R.id.iv_cancel)
    ImageView ivCancel;

    private int currentPosition = -1;

    //百度地图
    private LocationClient mLocationClient;

    @Override
    protected int getContentView() {
        return R.layout.activity_home_new;
    }

    @Override
    protected String getTitleName() {
        return "首页";
    }

    @Override
    public HomePresenter getmPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected int getToolBarType() {
        return TOOLBAR_TYPE_HOME;
    }

    @Override
    protected void onCreate() {
        if (Constant.user.isEmpty()) {
            Constant.user.getDataFromSp();
        }

        //退出程序
        setClickKeyCodeBackLisenter(new OnClickKeyCodeBackLisenter() {
            @Override
            public boolean onClickKeyCodeBack() {
                exsit();
                return false;
            }
        });
        //检测app版本
        EventBusUtils.post(new DownloadAppEvent(this));

        requestLocationPremission();
    }

    private void exsit() {
        if (isExsit) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNavigateTabBar.onRestoreInstanceState(savedInstanceState);

        mNavigateTabBar.addTab(HomeNewFragment.class, new MainNavigateTabBar.TabParam(mUnSelectedIcon[0], mSelectedIcon[0], TAG_PAGE_HOME));
        mNavigateTabBar.addTab(RmShopNewFragment.class, new MainNavigateTabBar.TabParam(mUnSelectedIcon[1], mSelectedIcon[1], TAG_PAGE_CITY));
        mNavigateTabBar.addTab(null, new MainNavigateTabBar.TabParam(0, 0, TAG_PAGE_PUBLISH));
        mNavigateTabBar.addTab(InformationFragment.class, new MainNavigateTabBar.TabParam(mUnSelectedIcon[2], mSelectedIcon[2], TAG_PAGE_MESSAGE));
        mNavigateTabBar.addTab(PersonalNewFragment.class, new MainNavigateTabBar.TabParam(mUnSelectedIcon[3], mSelectedIcon[3], TAG_PAGE_PERSON));

        mNavigateTabBar.setTabSelectListener(new MainNavigateTabBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(MainNavigateTabBar.ViewHolder holder) {
                if (holder.fragmentClass == PersonalNewFragment.class && Constant.user.isEmpty()) {//个人中心
                    toNextActivity(LoginActivity.class);
                }
            }
        });

        if (!Constant.user.isEmpty()){
//            loginEmclient();
            EventBusUtils.post(new LoginSuccessEvent());
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        currentPosition = intent.getIntExtra("position", -1);
        if (currentPosition >= 0) {
            mNavigateTabBar.setCurrentSelectedTab(currentPosition);
        }
    }

    /**
     * 登录成功
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginSuccess(LoginSuccessEvent event){
        mPresenter.getShareContent();
//        loginSuccessToHuanxin();
        EaseUser easeUser = new EaseUser(Constant.user.getMobilePhone());
        EaseUserChatUtils.addCurLoginUser(easeUser);
        loginEmclient();
    }

    /**
     * 支付成功
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void paySuccess(PaySuccessEvent event) {
        toNextActivity(MyOrderActivity.class);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mNavigateTabBar.onSaveInstanceState(outState);
    }

    private void requestLocationPremission() {
        String[] locationPermission = {Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};
        PermissionGen.needPermission(HomeNewActivity.this, REQUEST_PERMISSION_LOCATION, locationPermission);
    }

    @PermissionSuccess(requestCode = REQUEST_PERMISSION_LOCATION)
    public void getLocationPermissionSuccess() {
        initBDLocation();
    }

    private void initBDLocation() {
        mLocationClient = new LocationClient(getApplicationContext());

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span = 1000 * 60 * 10; //10秒
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);

        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                //获取定位结果
                String longitude = String.valueOf(location.getLongitude());
                String latitude = String.valueOf(location.getLatitude());

                mPresenter.getUserLocation(longitude, latitude);
            }

            public void onConnectHotSpotMessage(String s, int i) {

            }


        });

        mLocationClient.start();
    }

    public void onClickPublish(View v) {
        float curTvShareTranslationY = tvShare.getTranslationY();
//        float curIvShareTranslationY = ivShare.getTranslationY();
        float windowHeight = AppUtils.getCurWindowHeight(HomeNewActivity.this);
        ObjectAnimator tabPostIconAnimator = ObjectAnimator.ofFloat(mTabPostIcon, "rotation", 0f, 45f);
        ObjectAnimator mainDialogAnimator = ObjectAnimator.ofFloat(clMainDialog, "alpha", 0f, 0.95f);
        mainDialogAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                clMainDialog.setVisibility(View.VISIBLE);
            }
        });
        final ObjectAnimator nearPartnerAnimator = ObjectAnimator.ofFloat(tvNearPartner, "translationY", windowHeight, curTvShareTranslationY);
        nearPartnerAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                tvNearPartner.setVisibility(View.VISIBLE);
            }
        });
        final ObjectAnimator richScanAnimator = ObjectAnimator.ofFloat(tvRichScan, "translationY", windowHeight, curTvShareTranslationY);
        richScanAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                tvRichScan.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                nearPartnerAnimator.setDuration(300);
                nearPartnerAnimator.start();
            }
        });
        ObjectAnimator shareAnimator = ObjectAnimator.ofFloat(tvShare, "translationY", windowHeight, curTvShareTranslationY);
//        ObjectAnimator ivShareAnimator = ObjectAnimator.ofFloat(ivShare, "translationY", windowHeight, curIvShareTranslationY);
        shareAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                tvShare.setVisibility(View.VISIBLE);
//                ivShare.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                richScanAnimator.setDuration(300);
                richScanAnimator.start();
            }
        });

        float rlCurTranslationY = rlPlayCard.getTranslationY();
        ObjectAnimator rlPlayCardAnimator = ObjectAnimator.ofFloat(rlPlayCard, "translationY", -200f, rlCurTranslationY);

        ObjectAnimator cancelAnimator = ObjectAnimator.ofFloat(ivCancel, "rotation", 0f, 45f);

        AnimatorSet animatorSet = new AnimatorSet();
        //.with(ivShareAnimator)
        animatorSet.play(tabPostIconAnimator).with(mainDialogAnimator).with(shareAnimator).with(rlPlayCardAnimator).with(cancelAnimator);
        animatorSet.setDuration(500);
        animatorSet.start();
    }

    @OnClick(R.id.rl_main_dialog)
    public void mainDialog(View view) {
    }

    @OnClick(R.id.iv_cancel)
    public void cancelMainDialog(View view) {
        cancelMainDialog();
    }

    @PermissionSuccess(requestCode = REQUEST_PERMISSION_CAMERA)
    public void success() {
        sweep();
    }

    @PermissionFail(requestCode = REQUEST_PERMISSION_CAMERA)
    public void failed() {
        showToast("照相机权限申请失败");
    }

    private void sweep() {
        startActivityForResult(new Intent(this, CaptureActivity.class), 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {//扫描二维码 成功  接收结果
            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");
            mPresenter.getUserInfoByQRCode(result);
        }
    }

    /**
     * 关闭提示弹出框
     */
    private void cancelMainDialog() {
        ObjectAnimator tabPostIconAnimator = ObjectAnimator.ofFloat(mTabPostIcon, "rotation", 45f, 0f);
        ObjectAnimator mainDialogAnimator = ObjectAnimator.ofFloat(clMainDialog, "alpha", 0.9f, 0f);
        mainDialogAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                clMainDialog.setVisibility(View.GONE);
                tvShare.setVisibility(View.INVISIBLE);
//                ivShare.setVisibility(View.INVISIBLE);
                tvRichScan.setVisibility(View.INVISIBLE);
                tvNearPartner.setVisibility(View.INVISIBLE);
            }
        });
        ObjectAnimator cancelAnimator = ObjectAnimator.ofFloat(ivCancel, "rotation", 45f, 0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(tabPostIconAnimator).with(mainDialogAnimator).with(cancelAnimator);
        animatorSet.setDuration(500);
        animatorSet.start();
    }

    /**
     * 扫一扫
     * @param view
     */
    @OnClick(R.id.tv_rich_scan)
    public void richScan(View view) {
        cancelMainDialog();
        PermissionGen.needPermission(HomeNewActivity.this,
                REQUEST_PERMISSION_CAMERA, Manifest.permission.CAMERA);
    }

    /**
     * 分享
     * @param view
     */
    @OnClick(R.id.tv_share)
    public void shareApp(View view) {
        cancelMainDialog();
        openShare();
    }

    /**
     * 附近合伙人
     * @param view
     */
    @OnClick(R.id.tv_near_partner)
    public void nearPartner(View view){
        cancelMainDialog();
        toNextActivity(NearbyVipActivity.class);
    }

    /**
     * 打开分享面板
     */
    private void openShare() {
        UmengShareUtils.openShare(this, shareDto, new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                showToast("分享成功");
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                Logger.d(throwable.getMessage());

                showToast("分享失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                showToast("取消分享");
            }
        });
    }

    @Override
    public void showShareContent(ShareDto shareDto) {
        this.shareDto = shareDto;
    }

    @Override
    public void getUserInfoByQRCodeSuccess(UserCardDto userCardDto, String friendPhone) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("userCardDto", userCardDto);
        bundle.putString("friendPhone", friendPhone);
        toNextActivity(EditUserCardActivity.class, bundle);
    }

    @Override
    public void locationSuccess() {
        Logger.d("定位成功");
    }

    @Override
    public void showMyFriends(List<MyFriendsDto> myFriendsDtos) {
        for (MyFriendsDto friendsDto : myFriendsDtos){
            EaseUser user = new EaseUser(friendsDto.getMobilePhone());
            user.setAvatar(friendsDto.getPortraitUrl());
            user.setNickname(friendsDto.getNickName());
            EaseUserChatUtils.addUser(user);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userNotLogin(UserIsEmptyEvent emptyEvent){
        Constant.user.clear();
        toNextActivity(LoginActivity.class);
    }

    private void loginEmclient(){
        EMClient.getInstance().login(Constant.user.getMobilePhone(),Constant.user.getHXpwd(),new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Logger.d("登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Logger.d("登录聊天服务器失败！");
            }
        });
    }

    @OnClick({R.id.ll_guide1,R.id.tv_guide1,R.id.tv_guide_intro1})
    public void guide1(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("guide",0);
        toNextActivity(UserCardGuideActivity.class,bundle);
    }

    @OnClick({R.id.ll_guide2,R.id.tv_guide2,R.id.tv_guide_intro2})
    public void guide2(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("guide",1);
        toNextActivity(UserCardGuideActivity.class,bundle);
    }

    @OnClick({R.id.ll_guide3,R.id.tv_guide3,R.id.tv_guide_intro3})
    public void guide3(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("guide",2);
        toNextActivity(UserCardGuideActivity.class,bundle);
    }

}
