package com.km.rmbank.module;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseUserChatUtils;
import com.km.rmbank.R;
import com.km.rmbank.adapter.HomePersonalFuntionAdapter;
import com.km.rmbank.adapter.ViewPagerTabLayoutAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.cell.HomePastActionOneCell;
import com.km.rmbank.cell.HomePastActionTwoCell;
import com.km.rmbank.dto.InformationDto;
import com.km.rmbank.dto.MyFriendsDto;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.entity.HomePersonalFuntionEntity;
import com.km.rmbank.event.DownloadAppEvent;
import com.km.rmbank.module.home.HomeClubTabFragment;
import com.km.rmbank.module.home.message.MessageActivity;
import com.km.rmbank.module.login.LoginActivity;
import com.km.rmbank.module.personal.account.UserAccountActivity;
import com.km.rmbank.module.personal.attention.AttentionGoodsActivity;
import com.km.rmbank.module.personal.goodsmanager.GoodsManagerActivity;
import com.km.rmbank.module.personal.integral.MyIntegralActivity;
import com.km.rmbank.module.personal.mycontact.MyContactActivity;
import com.km.rmbank.module.personal.order.MyOrderActivity;
import com.km.rmbank.module.personal.receiveraddress.ReceiverAddressActivity;
import com.km.rmbank.module.personal.setting.SettingActivity;
import com.km.rmbank.module.personal.team.MyTeamActivity;
import com.km.rmbank.module.personal.userinfo.EditUserCardActivity;
import com.km.rmbank.module.personal.userinfo.UserInfoActivity;
import com.km.rmbank.module.personal.vip.SelectMemberTypeActivity;
import com.km.rmbank.utils.Constant;
import com.km.rv_libs.TemplateAdapter;
import com.km.rv_libs.base.ICell;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.EventBusUtils;
import com.ps.androidlib.utils.glide.GlideUtils;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.util.ArrayList;
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

public class HomeThreeActivity extends BaseActivity<HomeThreePresenter> implements HomeThreeContract.View {

    public final static int REQUEST_PERMISSION_CAMERA = 1;
    public final static int REQUEST_PERMISSION_LOCATION = 2;
    private static boolean isExsit = false;


    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.dl_home)
    DrawerLayout dlHome;
    private ActionBarDrawerToggle mDrawerToggle;

    @BindView(R.id.fl_left)
    FrameLayout flLeft;


    //侧滑用户信息

    @BindView(R.id.iv_user_portrait)
    ImageView ivUserPortrait;
    @BindView(R.id.tv_nickname)
    TextView tvNickName;
    @BindView(R.id.tv_join_days)
    TextView tvJoinDays;

    @BindView(R.id.iv_vip_level)
    ImageView ivVipLevel;
    @BindView(R.id.iv_user_card)
    ImageView ivUserCard;
    @BindView(R.id.tv_vip_level)
    TextView tvVipLevel;
    @BindView(R.id.tv_user_card)
    TextView tvUserCard;




    //侧滑  用户的 功能
    private int personalFunctionImgWidth = 0;
    @BindView(R.id.rv_user_funtion1)
    RecyclerView rvUserFunction1;
    private String[] userFunction1Names = {"我的人脉", "我的关注", "我的订单", "收货地址", "我的团队","逛一逛"};
    private int[] userFunction1Res = {R.mipmap.ic_personal_my_friends,
            R.mipmap.ic_personal_my_follows,
            R.mipmap.ic_personal_my_orders,
            R.mipmap.ic_personal_receiver_addresses,
            R.mipmap.ic_personal_my_teams,
            R.mipmap.ic_personal_my_teams};


    @BindView(R.id.rv_user_funtion2)
    RecyclerView rvUserFunction2;
    private String[] userFunction2Names = {"我的积分", "商品管理", "我的俱乐部"};
    private int[] userFunction2Res = {R.mipmap.ic_personal_my_intergals,
            R.mipmap.ic_personal_my_products,
            R.mipmap.ic_personal_my_clubs};


    //首页  上部分  平台 展示或推荐的 俱乐部
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    private String[] tabNames = {"推荐俱乐部","活跃俱乐部","最新俱乐部"};


    @BindView(R.id.rv_past_action)
    RecyclerView rvPastAction;
    private TemplateAdapter pastActionAdapter;

    //百度地图
    private LocationClient mLocationClient;

    @Override
    protected int getContentView() {
        return R.layout.activity_home_three;
    }

    @Override
    protected String getTitleName() {
        return "玩转地球";
    }

    @Override
    public HomeThreePresenter getmPresenter() {
        return new HomeThreePresenter(this);
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

        int windowWidth = AppUtils.getCurWindowWidth(this);
        personalFunctionImgWidth = windowWidth / 4 - AppUtils.dip2px(this, 48);
        Logger.d("personalFunctionImgWidth ==  " + personalFunctionImgWidth);
        flLeft.getLayoutParams().width = windowWidth / 4 * 3;


        mDrawerToggle = new ActionBarDrawerToggle(this, dlHome, mToolBar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (Constant.user.isEmpty()){
                    Constant.user.clear();
                    dlHome.closeDrawers();
                    toNextActivity(LoginActivity.class);
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerToggle.syncState();
        dlHome.setDrawerListener(mDrawerToggle);

        initToolbarRight();
        initUserFunction();
        initTabLayout();
        initRvPastAction();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadUserInfo();
    }

    /**
     * 退出
     */
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

    /**
     * 请求权限
     */
    private void requestLocationPremission() {
        String[] locationPermission = {Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};
        PermissionGen.needPermission(this, REQUEST_PERMISSION_LOCATION, locationPermission);
    }

    @PermissionSuccess(requestCode = REQUEST_PERMISSION_LOCATION)
    public void getLocationPermissionSuccess() {
        initBDLocation();
    }

    /**
     * 初始化百度地图 实时定位
     */
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

    @Override
    public void getUserInfoByQRCodeSuccess(UserCardDto userCardDto, String friendPhone) {

    }

    @Override
    public void locationSuccess() {

    }

    @Override
    public void showMyFriends(List<MyFriendsDto> myFriendsDtos) {
        for (MyFriendsDto friendsDto : myFriendsDtos) {
            EaseUser user = new EaseUser(friendsDto.getMobilePhone());
            user.setAvatar(friendsDto.getPortraitUrl());
            user.setNickname(friendsDto.getNickName());
            EaseUserChatUtils.addUser(user);
        }
    }

    @Override
    public void showPastActions(List<InformationDto> informationDtos, int curPageNo) {
        List<ICell> pastActionCells = new ArrayList<>();
        for (int i = 0; i < informationDtos.size(); i++){
            if (i % 2 == 0){
                pastActionCells.add(new HomePastActionOneCell(this,informationDtos.get(i)));
            } else {
                pastActionCells.add(new HomePastActionTwoCell(this,informationDtos.get(i)));
            }
        }
        pastActionAdapter.addAll(pastActionCells);
    }

    @Override
    public void showUserInfo(UserInfoDto userInfoDto) {
        Constant.userInfo = userInfoDto;
        Constant.isAllowUserCard = (TextUtils.isEmpty(userInfoDto.getAllowStutas()) || "0".equals(userInfoDto.getAllowStutas()));
        GlideUtils.loadCircleImage(ivUserPortrait,userInfoDto.getPortraitUrl());
        tvNickName.setText(userInfoDto.getNickName());
        tvJoinDays.setText("hi,这是你加入玩转地球的第" + userInfoDto.getRegisterDate() + "天");

        String vipType = userInfoDto.getRoleName();
        if ("2".equals(Constant.user.getRoleId())){//合伙人

        } else if ("3".equals(Constant.user.getRoleId())){//体验式

        } else {
            vipType = "普通会员";
        }

        tvVipLevel.setText(vipType);
    }

    /**
     * 初始化侧滑 用户功能按钮
     */
    private void initUserFunction() {

        float fontSize = adjustFontSize(AppUtils.getCurWindowWidth(this), AppUtils.getCurWindowHeight(this));

        tvVipLevel.setTextSize(fontSize);
        tvUserCard.setTextSize(fontSize);

        ivVipLevel.getLayoutParams().width = personalFunctionImgWidth;
        ivVipLevel.getLayoutParams().height = personalFunctionImgWidth;

        ivUserCard.getLayoutParams().width = personalFunctionImgWidth;
        ivUserCard.getLayoutParams().height = personalFunctionImgWidth;

        List<HomePersonalFuntionEntity> entityList1 = new ArrayList<>();
        for (int i = 0; i < userFunction1Names.length; i++) {
            entityList1.add(new HomePersonalFuntionEntity(userFunction1Names[i], userFunction1Res[i]));
        }

        List<HomePersonalFuntionEntity> entityList2 = new ArrayList<>();
        for (int i = 0; i < userFunction2Names.length; i++) {
            entityList2.add(new HomePersonalFuntionEntity(userFunction2Names[i], userFunction2Res[i]));
        }
        RVUtils.setGridLayoutManage(rvUserFunction1, 3);
        HomePersonalFuntionAdapter fun1Adapter = new HomePersonalFuntionAdapter(this);
        fun1Adapter.setImageWidth(personalFunctionImgWidth);
        fun1Adapter.setFontSize(fontSize);
        rvUserFunction1.setAdapter(fun1Adapter);
        fun1Adapter.addData(entityList1);
        fun1Adapter.setItemClickListener(new BaseAdapter.ItemClickListener<HomePersonalFuntionEntity>() {
            @Override
            public void onItemClick(HomePersonalFuntionEntity itemData, int position) {
//                showToast(itemData.getFuntionName());
                switch (position){
                    case 0://我的人脉
                        toNextActivity(MyContactActivity.class);
                        break;
                    case 1://我的关注
                        toNextActivity(AttentionGoodsActivity.class);
                        break;
                    case 2://我的订单
                        toNextActivity(MyOrderActivity.class);
                        break;
                    case 3://收货地址
                        toNextActivity(ReceiverAddressActivity.class);
                        break;
                    case 4://我的团队
                        if (!"2".equals(Constant.userInfo.getRoleId())){
                            showToast("暂无此权限，请升级成为合伙人会员");
                            return;
                        }
                        toNextActivity(MyTeamActivity.class);
                        break;
                    case 5://逛一逛  之前的商城
                        break;

                }
            }

        });

        RVUtils.setGridLayoutManage(rvUserFunction2, 3);
        HomePersonalFuntionAdapter fun2Adapter = new HomePersonalFuntionAdapter(this);
        fun2Adapter.setImageWidth(personalFunctionImgWidth);
        fun2Adapter.setFontSize(fontSize);
        rvUserFunction2.setAdapter(fun2Adapter);
        fun2Adapter.addData(entityList2);
        fun2Adapter.setItemClickListener(new BaseAdapter.ItemClickListener<HomePersonalFuntionEntity>() {
            @Override
            public void onItemClick(HomePersonalFuntionEntity itemData, int position) {

                switch (position){
                    case 0://我的积分
                        if (!"2".equals(Constant.userInfo.getRoleId()) && !"3".equals(Constant.userInfo.getRoleId())){
                            showToast("暂无此权限，请升级成为体验式会员或合伙人会员");
                            return;
                        }
                        toNextActivity(MyIntegralActivity.class);
                        break;
                    case 1://商品管理
                        if (!"2".equals(Constant.userInfo.getRoleId()) && !"3".equals(Constant.userInfo.getRoleId())){
                            showToast("暂无此权限，请升级成为体验式会员或合伙人会员");
                            return;
                        }
                        toNextActivity(GoodsManagerActivity.class);
                        break;
                    case 2://我的俱乐部
                        showToast("暂未开放");
                        break;


                }
            }

        });
    }

    /**
     * 动态获取侧滑菜单中  用户功能  字体大小
     * @param screenWidth
     * @param screenHeight
     * @return
     */
    private int adjustFontSize(int screenWidth, int screenHeight) {
        screenWidth = screenWidth > screenHeight ? screenWidth : screenHeight;
        /**
         * 1. 在视图的 onsizechanged里获取视图宽度，一般情况下默认宽度是320，所以计算一个缩放比率
         rate = (float) w/320   w是实际宽度
         2.然后在设置字体尺寸时 paint.setTextSize((int)(8*rate));   8是在分辨率宽为320 下需要设置的字体大小
         实际字体大小 = 默认字体大小 x  rate
         */
        int rate = (int) (6 * (float) screenWidth / 320); //我自己测试这个倍数比较适合，当然你可以测试后再修改
        return rate < 15 ? 15 : AppUtils.px2dip(this, rate); //字体太小也不好看的
    }

    /**
     * 初始化 toolbar 右侧 按钮
     */
    private void initToolbarRight(){
        mToolBar.inflateMenu(R.menu.toolbar_home_right);
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.rich_scan:
//                        showToast("扫一扫");
//                        cancelMainDialog();
                        PermissionGen.needPermission(HomeThreeActivity.this,
                                REQUEST_PERMISSION_CAMERA, Manifest.permission.CAMERA);
                        break;
                    case R.id.message:
//                        showToast("消息");
                        toNextActivity(MessageActivity.class);
                        break;
                }
                return false;
            }
        });
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
     * 初始化 俱乐部 的相关页面
     */
    private void initTabLayout(){
        List<String> tabNameList = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < tabNames.length; i++){
            tabNameList.add(tabNames[i]);
            Bundle bundle = new Bundle();
            bundle.putString("clubType", String.valueOf((i+1)));
            fragmentList.add(HomeClubTabFragment.newInstance(bundle));
        }

        ViewPagerTabLayoutAdapter adapter = new ViewPagerTabLayoutAdapter(getSupportFragmentManager(),fragmentList,tabNameList);
        viewPager.setAdapter(adapter);
        mTablayout.setupWithViewPager(viewPager);

    }

    /**
     * 初始化 往期活动 列表
     */
    private void initRvPastAction(){
        RVUtils.setLinearLayoutManage(rvPastAction, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvPastAction);
        pastActionAdapter = new TemplateAdapter();
        rvPastAction.setAdapter(pastActionAdapter);

        mPresenter.getPastActions(1);
    }

    @OnClick(R.id.tv_app_setting)
    public void appSettingClick(View view){
        toNextActivity(SettingActivity.class);
    }

    /**
     * 编辑个人信息  头像  昵称
     * @param view
     */
    @OnClick({R.id.rl_personal_info,R.id.iv_user_portrait,R.id.tv_nickname,R.id.iv_arrow_right})
    public void onClickPersonalInfo(View view){
        Bundle bundle = new Bundle();
        bundle.putParcelable("userInfo",Constant.userInfo);
        toNextActivity(UserInfoActivity.class,bundle);
    }

    /**
     * 购买 并成为 体验式 或 合伙人 会员
     * @param view
     */
    @OnClick({R.id.ll_vip_level,R.id.iv_vip_level,R.id.tv_vip_level})
    public void onClickVipLevel(View view){
        toNextActivity(SelectMemberTypeActivity.class);
    }

    /**
     * 去编辑 或 查看  用户 名片信息
     * @param view
     */
    @OnClick({R.id.ll_user_card,R.id.iv_user_card,R.id.tv_user_card})
    public void onClickUserCard(View view){
        toNextActivity(EditUserCardActivity.class);
    }

    /**
     * 我的账户
     * @param view
     */
    @OnClick(R.id.tv_my_account)
    public void onClockMyAccount(View view){
        if (!"2".equals(Constant.userInfo.getRoleId()) && !"3".equals(Constant.userInfo.getRoleId())){
            showToast("暂无此权限，请升级成为体验式会员或合伙人会员");
            return;
        }
        toNextActivity(UserAccountActivity.class);
    }

}
