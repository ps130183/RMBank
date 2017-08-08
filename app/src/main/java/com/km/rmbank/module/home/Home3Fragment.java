package com.km.rmbank.module.home;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.km.rmbank.R;
import com.km.rmbank.adapter.ViewPagerAdapter;
import com.km.rmbank.adapter.ViewPagerTabLayoutAdapter;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.event.HomeTabLayoutEvent;
import com.km.rmbank.event.RichScanEvent;
import com.km.rmbank.event.ShareEvent;
import com.km.rmbank.module.Home2Activity;
import com.km.rmbank.module.home.message.MessageActivity;
import com.km.rmbank.module.personal.shopcart.ShoppingCartActivity;
import com.km.rmbank.module.rmshop.GoodsFragment;
import com.ps.androidlib.entity.TabEntity;
import com.ps.androidlib.utils.EventBusUtils;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.zaaach.toprightmenu.TopRightMenu;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home3Fragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.iv_more)
    ImageView ivMore;

    @BindView(R.id.common_tab_layout)
    CommonTabLayout mCommonTabLayout;
    private String[] mTitle = {"俱乐部","动态","约吗","捡漏","合伙人"};
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private TopRightMenu mTopRightMenu;


    public static Home3Fragment newInstance(Bundle bundle) {
        Home3Fragment fragment = new Home3Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home3;
    }

    @Override
    protected void createView() {
        initViewPager();
        initTopRightMenu();
//        setHasOptionsMenu(true);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolBar);

    }

    @Override
    public void onResume() {
        super.onResume();
        mToolBar.setTitle("");
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        menu.clear();
//        inflater.inflate(R.menu.toolbar_home_right,menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.rich_scan://扫一扫
//                EventBusUtils.post(new RichScanEvent());
//                break;
//            case R.id.message:
//                toNextActivity(MessageActivity.class);
//                break;
//            case R.id.share:
//                EventBusUtils.post(new ShareEvent());
//                break;
//            default:break;
//        }
//        return true;
//    }

    @OnClick(R.id.iv_more)
    public void clickRightBtn(View view){
        if (mTopRightMenu != null){
            mTopRightMenu.showAsDropDown(ivMore,-230,0);
        }
    }
    private void  initTopRightMenu(){
        mTopRightMenu = new TopRightMenu(getActivity());

//添加菜单项
        List<com.zaaach.toprightmenu.MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new com.zaaach.toprightmenu.MenuItem(R.mipmap.ic_home_scan_qrcode, "扫一扫"));
        menuItems.add(new com.zaaach.toprightmenu.MenuItem(R.mipmap.ic_home_message, "消息"));
        menuItems.add(new com.zaaach.toprightmenu.MenuItem(R.mipmap.ic_share_white_48dp, "分享"));

        mTopRightMenu
                .setHeight(RecyclerView.LayoutParams.WRAP_CONTENT)     //默认高度480
                .setWidth(320)      //默认宽度wrap_content
                .showIcon(false)     //显示菜单图标，默认为true
                .dimBackground(true)        //背景变暗，默认为true
                .needAnimationStyle(true)   //显示动画，默认为true
                .setAnimationStyle(R.style.TRM_ANIM_STYLE)
                .addMenuList(menuItems)
//                .addMenuItem(new MenuItem(R.mipmap.facetoface, "面对面快传"))
//                .addMenuItem(new MenuItem(R.mipmap.pay, "付款"))
                .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick(int position) {
                        switch (position){
                            case 0://扫一扫
                                EventBusUtils.post(new RichScanEvent());
                                break;
                            case 1:
                                toNextActivity(MessageActivity.class);
                                break;
                            case 2:
                                EventBusUtils.post(new ShareEvent());
                                break;
                        }
                    }
                });
    }

    private void initViewPager(){
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(Home3ClubFragment.newInstance(null));
        fragments.add(Home3DynamicFragment.newInstance(null));
        fragments.add(Home3ActionRecentFragment.newInstance(null));
        fragments.add(Home3ForumFragment.newInstance(null));
        fragments.add(Home3VipIntroduceFragment.newInstance(null));
        ArrayList<CustomTabEntity> tabEntityList = new ArrayList<>();
        for (int i = 0; i < mTitle.length; i++){
            tabEntityList.add(new TabEntity(mTitle[i],R.mipmap.icon_home_rbtn1_unpress,R.mipmap.icon_home_rbtn1_pressed));
        }
        mCommonTabLayout.setTabData(tabEntityList);
//        ViewPagerTabLayoutAdapter viewPagerAdapter = new ViewPagerTabLayoutAdapter(this.getFragmentManager(),fragments,titleList);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this.getFragmentManager(),fragments);
        mViewPager.setAdapter(viewPagerAdapter);


        mCommonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCommonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(0);
        mCommonTabLayout.setCurrentTab(0);

//        mCommonTabLayout.setViewPager(mViewPager);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void homeTabLayout(HomeTabLayoutEvent event){
        if (mCommonTabLayout.getCurrentTab() != 4){
            mCommonTabLayout.setCurrentTab(4);
            mViewPager.setCurrentItem(4);
        }
    }

}
