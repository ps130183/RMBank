package com.km.rmbank.module.home;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.km.rmbank.R;
import com.km.rmbank.adapter.HomeAdapter;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.cell.HomeFloorFourCell;
import com.km.rmbank.cell.HomeFloorOneCell;
import com.km.rmbank.cell.HomeFloorThreeCell;
import com.km.rmbank.cell.HomeFloorTwoCell;
import com.km.rmbank.cell.HomeGoodsTypeCell;
import com.km.rmbank.cell.HomeHeaderCell;
import com.km.rmbank.dto.BannerDto;
import com.km.rmbank.dto.HomeGoodsTypeDto;
import com.km.rmbank.dto.HomeNewRecommendDto;
import com.km.rmbank.dto.HomeRecommendDto;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.entity.HomeDataEntity;
import com.km.rmbank.entity.HomeGtEntity;
import com.km.rmbank.module.actionarea.InformationDetailActivity;
import com.km.rmbank.module.home.message.MessageActivity;
import com.km.rmbank.module.rmshop.goods.GoodsActivity;
import com.km.rmbank.module.rmshop.goods.RmShopActivity;
import com.km.rmbank.utils.UmengShareUtils;
import com.km.rv_libs.TemplateAdapter;
import com.km.rv_libs.base.ICell;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.BannerUtils;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by kamangkeji on 17/3/14.
 */

public class HomeNewFragment extends BaseFragment<HomePresenter> implements HomeContract.View {

//    @BindView(R.id.app_bar)
//    AppBarLayout appBar;
//    @BindView(R.id.banner)
//    Banner banner;

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @BindView(R.id.rc_content)
    RecyclerView rcContent;

//    @BindView(R.id.et_search)
//    EditText etSearch;

    private String[] homeGtNames = {"手机","数码","服装鞋帽","交通工具","母婴产品","家用电器","家具家电","电脑",
            "手机","数码","服装鞋帽","交通工具","母婴产品","家用电器","家具家电","电脑",
            "手机","数码","服装鞋帽","交通工具","母婴产品","家用电器","家具家电","全部分类"};
    private int[] homeGtImages = {R.mipmap.ic_home_gt1,R.mipmap.ic_home_gt2,R.mipmap.ic_home_gt3,R.mipmap.ic_home_gt4,R.mipmap.ic_home_gt5,R.mipmap.ic_home_gt6,R.mipmap.ic_home_gt7,R.mipmap.ic_home_gt8,
            R.mipmap.ic_home_gt9,R.mipmap.ic_home_gt10,R.mipmap.ic_home_gt11,R.mipmap.ic_home_gt12,R.mipmap.ic_home_gt13,R.mipmap.ic_home_gt14,R.mipmap.ic_home_gt15,R.mipmap.ic_home_gt16,
            R.mipmap.ic_home_gt17,R.mipmap.ic_home_gt18,R.mipmap.ic_home_gt19,R.mipmap.ic_home_gt20,R.mipmap.ic_home_gt21,R.mipmap.ic_home_gt22,R.mipmap.ic_home_gt23,R.mipmap.ic_home_gt8};

    private int mWindowWidth = 0;
    private int mHomeHeaderHeight = 0;
    private int mHomeHeaderVisibleHeight = 0;
    private int mToolbarHeight = 0;


    private HomeGoodsTypeCell mHomeGoodsTypeCell;

    public static HomeNewFragment newInstance(Bundle bundle) {
        HomeNewFragment fragment = new HomeNewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_new_home;
    }

    @Override
    public HomePresenter getmPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected void createView() {
        mToolBar.setAlpha(0);
        mToolBar.setVisibility(View.GONE);
//        initBanner();
        mWindowWidth = AppUtils.getCurWindowWidth(getContext());
        mHomeHeaderHeight = mWindowWidth / 4 * 3;
        mHomeHeaderVisibleHeight = mHomeHeaderHeight;
        mToolbarHeight = mToolBar.getLayoutParams().height;
//        appBar.getLayoutParams().height = windowWidth / 4 * 3;
//        initrcContentView();
    }

    private void initrcContentView(List<HomeGoodsTypeDto> homeGoodsTypeDtos){
        RVUtils.setLinearLayoutManage(rcContent, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rcContent);
        TemplateAdapter adapter = new TemplateAdapter();

        List<HomeGoodsTypeDto> goodsTypeList = new ArrayList<>();

//        for (int i = 0; i < homeGtImages.length ; i++){
//            goodsTypeList.add(new HomeGtEntity(homeGtNames[i],homeGtImages[i]));
//        }

        adapter.add(new HomeHeaderCell("",null));

        mHomeGoodsTypeCell = new HomeGoodsTypeCell(homeGoodsTypeDtos,null);
        mHomeGoodsTypeCell.setmFragmentManager(getFragmentManager());
        adapter.add(mHomeGoodsTypeCell);

        rcContent.setAdapter(adapter);

        final LinearLayoutManager llm = (LinearLayoutManager) rcContent.getLayoutManager();

        rcContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {//根据页面的滑动 显示 或  隐藏标题栏 带动画
                super.onScrolled(recyclerView, dx, dy);
                int position = llm.findFirstVisibleItemPosition();
                if (position == 0){
                    mHomeHeaderVisibleHeight -= dy;
//                    if (mHomeHeaderVisibleHeight < 0){
//                        mHomeHeaderVisibleHeight = 0;
//                    } else if(mHomeHeaderVisibleHeight > mHomeHeaderHeight){
//                        mHomeHeaderVisibleHeight = mHomeHeaderHeight;
//                    }

//                    Logger.d("dy == " + dy + "   mHeaderHeight = " + mHomeHeaderVisibleHeight);
                    if (mHomeHeaderVisibleHeight <= mToolbarHeight){
                        mToolBar.setVisibility(View.VISIBLE);
                        mToolBar.setAlpha(1- (float)(mHomeHeaderVisibleHeight) / mToolbarHeight);
                    } else {
                        mToolBar.setVisibility(View.GONE);
                    }
                } else {
                    mHomeHeaderVisibleHeight = 0;
                    mToolBar.setAlpha(1);
                }
            }
        });
    }

    @Override
    public void getRecommendSuccess(List<HomeRecommendDto> homeRecommendDtos,int pageNo) {
        HomeAdapter adapter = (HomeAdapter) rcContent.getAdapter();
        adapter.addData(homeRecommendDtos,pageNo);
    }

    @Override
    public void showHomeBanner(final List<BannerDto> bannerDtos) {
        List<String> bannerImages = new ArrayList<>();
        for (BannerDto bannerDto : bannerDtos){
            bannerImages.add(bannerDto.getAvatarUrl());
        }
    }

    @Override
    public void showHomeGoodsType(List<HomeGoodsTypeDto> homeDataEntity) {
        initrcContentView(homeDataEntity);
    }

    @Override
    public void ShowHomeNewRecommend(List<HomeNewRecommendDto> homeNewRecommendDtos) {
        TemplateAdapter adapter = (TemplateAdapter) rcContent.getAdapter();
        for (HomeNewRecommendDto homeNewRecommendDto : homeNewRecommendDtos){

            switch (homeNewRecommendDto.getType()){
                case "1":
                    HomeFloorOneCell homeFloorOneCell = new HomeFloorOneCell(homeNewRecommendDto,onFloorOneClick);
                    adapter.add(homeFloorOneCell);
                    break;
                case "2":
                    HomeFloorTwoCell homeFloorTwoCell = new HomeFloorTwoCell(homeNewRecommendDto,onFloorTwoClick);
                    homeFloorTwoCell.setmFragmentManager(getFragmentManager());
                    adapter.add(homeFloorTwoCell);
                    break;
                case "3":
                    HomeFloorThreeCell homeFloorThreeCell = new HomeFloorThreeCell(homeNewRecommendDto,onFloorThreeClick);
                    adapter.add(homeFloorThreeCell);
                    break;
                case "4":
                    HomeFloorFourCell homeFloorFourCell = new HomeFloorFourCell(homeNewRecommendDto,onFloorOneClick);
                    adapter.add(homeFloorFourCell);
                    break;
            }
        }
    }

    @OnClick({R.id.rl_search,R.id.tv_search})
    public void etSearch(View view){
        toNextActivity(SearchActivity.class);
    }

    @OnClick(R.id.tv_message)
    public void message(View view){
        toNextActivity(MessageActivity.class);
    }

    ICell.OnCellClickListener<HomeNewRecommendDto> onFloorOneClick = new ICell.OnCellClickListener<HomeNewRecommendDto>() {
        @Override
        public void cellClick(HomeNewRecommendDto mData, int position) {
            Bundle bundle = new Bundle();
            boolean isLevelOne = false;
            String levelOneId = mData.getProductTypeParentId();
            String levelTwoId = "";
            switch (position){
                case 0:
                    isLevelOne = true;
                    break;
                case 1:
                    levelTwoId = mData.getTypeList().get(0).getId();
                    break;
                case 2:
                    levelTwoId = mData.getTypeList().get(1).getId();
                    break;
                case 3:
                    levelTwoId = mData.getTypeList().get(2).getId();
                    break;
                case 4:
                    levelTwoId = mData.getTypeList().get(3).getId();
                    break;

            }
            bundle.putBoolean("isLevelOne",isLevelOne);
            bundle.putString("levelOneId",levelOneId);
            bundle.putString("levelTwoId",levelTwoId);
            toNextActivity(RmShopActivity.class,bundle);
        }
    };

    ICell.OnCellClickListener<HomeNewRecommendDto> onFloorTwoClick = new ICell.OnCellClickListener<HomeNewRecommendDto>() {
        @Override
        public void cellClick(HomeNewRecommendDto mData, int position) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("isLevelOne",true);
            bundle.putString("levelOneId",mData.getProductTypeParentId());
            bundle.putString("levelTwoId","");
            toNextActivity(RmShopActivity.class,bundle);
        }
    };

    ICell.OnCellClickListener<HomeNewRecommendDto> onFloorThreeClick = new ICell.OnCellClickListener<HomeNewRecommendDto>() {
        @Override
        public void cellClick(HomeNewRecommendDto mData, int position) {
            boolean isLevelOne = false;
            String levelOneId = mData.getProductTypeParentId();
            String levelTwoId = "";
            switch (position){
                case 0:
                    isLevelOne = true;
                    break;
                case 1:
                    levelTwoId = mData.getTypeList().get(0).getId();
                    break;
                case 2:
                    levelTwoId = mData.getTypeList().get(1).getId();
                    break;
                case 3:
                    levelTwoId = mData.getTypeList().get(2).getId();
                    break;
                case 4:
                    levelTwoId = mData.getTypeList().get(3).getId();
                    break;
                case 5:
                    levelTwoId = mData.getTypeList().get(4).getId();
                    break;
                case 6:
                    levelTwoId = mData.getTypeList().get(5).getId();
                    break;
            }
            Bundle bundle = new Bundle();
            bundle.putBoolean("isLevelOne",isLevelOne);
            bundle.putString("levelOneId",levelOneId);
            bundle.putString("levelTwoId",levelTwoId);
            toNextActivity(RmShopActivity.class,bundle);
        }
    };

}
