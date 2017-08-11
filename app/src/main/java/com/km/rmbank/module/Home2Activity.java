package com.km.rmbank.module;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseUserChatUtils;
import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.MyFriendsDto;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.event.DownloadAppEvent;
import com.km.rmbank.event.HomeTabLayoutEvent;
import com.km.rmbank.event.LoginFailEvent;
import com.km.rmbank.event.LoginSuccessEvent;
import com.km.rmbank.event.PaySuccessEvent;
import com.km.rmbank.event.RichScanEvent;
import com.km.rmbank.event.ShareEvent;
import com.km.rmbank.event.UserIsEmptyEvent;
import com.km.rmbank.module.actionarea.InformationFragment;
import com.km.rmbank.module.center.CenterVipFunctionFragment;
import com.km.rmbank.module.home.Home2Fragment;
import com.km.rmbank.module.home.Home3Fragment;
import com.km.rmbank.module.login.LoginActivity;
import com.km.rmbank.module.nearbyvip.NearbyVipActivity;
import com.km.rmbank.module.personal.PersonalNewFragment;
import com.km.rmbank.module.personal.order.MyOrderActivity;
import com.km.rmbank.module.personal.userinfo.EditUserCardActivity;
import com.km.rmbank.module.personal.userinfo.UserCardInfoActivity;
import com.km.rmbank.module.rank.RankingFragment;
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
import java.util.Set;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class Home2Activity extends BaseActivity<Home2Presenter> implements Home2Contract.View {

    public final static int REQUEST_PERMISSION_CAMERA = 1;
    public final static int REQUEST_PERMISSION_LOCATION = 2;
    private static boolean isExsit = false;

    private static final String TAG_PAGE_HOME = "首页";
    private static final String TAG_PAGE_CITY = "商城";
    private static final String TAG_PAGE_PUBLISH = "合伙人";
    private static final String TAG_PAGE_MESSAGE = "琅琊榜";
    private static final String TAG_PAGE_PERSON = "我的";

    private ShareDto shareDto;

    private int[] mSelectedIcon = {R.mipmap.icon_home_rbtn1_pressed, R.mipmap.icon_home_rbtn2_pressed, R.mipmap.icon_home_rbtn3_pressed, R.mipmap.icon_home_rbtn4_pressed,R.mipmap.ic_home_navigation_center_pressed};
    private int[] mUnSelectedIcon = {R.mipmap.icon_home_rbtn1_unpress, R.mipmap.icon_home_rbtn2_unpress, R.mipmap.icon_home_rbtn3_unpress, R.mipmap.icon_home_rbtn4_unpress,R.mipmap.ic_home_navigation_center_unpress};

    @BindView(R.id.mainTabBar)
    MainNavigateTabBar mNavigateTabBar;

    @BindView(R.id.tab_post_icon)
    ImageView mTabPostIcon;

    private int currentPosition = -1;

    //百度地图
//    private LocationClient mLocationClient;

    @Override
    protected int getContentView() {
        return R.layout.activity_home_2;
    }

    @Override
    protected String getTitleName() {
        return "首页";
    }

    @Override
    public Home2Presenter getmPresenter() {
        return new Home2Presenter(this);
    }

    @Override
    protected int getToolBarType() {
        return TOOLBAR_TYPE_HOME;
    }

    @Override
    protected void onCreate() {
        if (Constant.user.isEmpty()) {
            Constant.user.getDataFromSp();
            JPushInterface.setAlias(this, Constant.user.getMobilePhone(), new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {
                    Logger.d("极光别名设置成功 = " + s + "    i =" + i);
                }
            });
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

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadUserInfo();
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

        mNavigateTabBar.addTab(Home3Fragment.class, new MainNavigateTabBar.TabParam(mUnSelectedIcon[0], mSelectedIcon[0], TAG_PAGE_HOME));
//        mNavigateTabBar.addTab(Home2Fragment.class, new MainNavigateTabBar.TabParam(mUnSelectedIcon[0], mSelectedIcon[0], TAG_PAGE_HOME));
        mNavigateTabBar.addTab(RankingFragment.class, new MainNavigateTabBar.TabParam(mUnSelectedIcon[2], mSelectedIcon[2], TAG_PAGE_MESSAGE));
        mNavigateTabBar.addTab(CenterVipFunctionFragment.class, new MainNavigateTabBar.TabParam(mUnSelectedIcon[4], mSelectedIcon[4], TAG_PAGE_PUBLISH));
        mNavigateTabBar.addTab(RmShopNewFragment.class, new MainNavigateTabBar.TabParam(mUnSelectedIcon[1], mSelectedIcon[1], TAG_PAGE_CITY));
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
        EaseUserChatUtils.clear();
        mPresenter.getMyFriends();

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
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        PermissionGen.needPermission(Home2Activity.this, REQUEST_PERMISSION_LOCATION, locationPermission);
    }

    @PermissionSuccess(requestCode = REQUEST_PERMISSION_LOCATION)
    public void getLocationPermissionSuccess() {
        initBDLocation();
    }

    private void initBDLocation() {
//        mLocationClient = new LocationClient(getApplicationContext());
//
//        LocationClientOption option = new LocationClientOption();
//        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
//
//        option.setCoorType("bd09ll");
//        //可选，默认gcj02，设置返回的定位结果坐标系
//
//        int span = 1000 * 60 * 10; //10秒
//        option.setScanSpan(span);
//        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
//
//        option.setIsNeedAddress(true);
//        //可选，设置是否需要地址信息，默认不需要
//
//        option.setOpenGps(true);
//        //可选，默认false,设置是否使用gps
//
//        option.setLocationNotify(true);
//        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
//
//        option.setIsNeedLocationDescribe(true);
//        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//
//        option.setIsNeedLocationPoiList(true);
//        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
//
//        option.setIgnoreKillProcess(false);
//        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
//
//        option.SetIgnoreCacheException(false);
//        //可选，默认false，设置是否收集CRASH信息，默认收集
//
//        option.setEnableSimulateGps(false);
//        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
//
//        mLocationClient.setLocOption(option);
//
//        mLocationClient.registerLocationListener(new BDLocationListener() {
//            @Override
//            public void onReceiveLocation(BDLocation location) {
//                //获取定位结果
//                String longitude = String.valueOf(location.getLongitude());
//                String latitude = String.valueOf(location.getLatitude());
//
//                mPresenter.getUserLocation(longitude, latitude);
//            }
//
//            public void onConnectHotSpotMessage(String s, int i) {
//
//            }
//
//
//        });
//
//        mLocationClient.start();
    }

    public void onClickPublish(View v) {
        if (Constant.user.isEmpty()){
            toNextActivity(LoginActivity.class);
            return;
        }
        if (Constant.userInfo != null && "2".equals(Constant.userInfo.getRoleId())){
            mNavigateTabBar.setCurrentSelectedTab(2);
        } else {
            EventBusUtils.post(new HomeTabLayoutEvent());
            if(mNavigateTabBar.getCurrentSelectedTab() != 0){
                mNavigateTabBar.setCurrentSelectedTab(0);
            }
        }
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

//    /**
//     * 扫一扫
//     * @param view
//     */
//    @OnClick(R.id.tv_rich_scan)
//    public void richScan(View view) {
//        cancelMainDialog();
//        PermissionGen.needPermission(Home2Activity.this,
//                REQUEST_PERMISSION_CAMERA, Manifest.permission.CAMERA);
//    }

    /**
     * 扫一扫
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRichScan(RichScanEvent event){
        PermissionGen.needPermission(Home2Activity.this,
                REQUEST_PERMISSION_CAMERA, Manifest.permission.CAMERA);
    }

    /**
     * 分享
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShare(ShareEvent event){
        openShare();
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
        toNextActivity(UserCardInfoActivity.class, bundle);
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

    @Override
    public void showUserInfo(UserInfoDto userInfoDto) {
        Constant.userInfo = userInfoDto;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userNotLogin(UserIsEmptyEvent emptyEvent){
        Constant.user.clear();
        toNextActivity(LoginActivity.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginFail(LoginFailEvent event){
        showToast("登录失败，请稍后再试");
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
//                Constant.user.clear();
//                EventBusUtils.post(new LoginFailEvent());
            }
        });
    }
}
