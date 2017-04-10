package com.km.rmbank.module.rmshop.goods;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.km.rmbank.R;
import com.km.rmbank.adapter.ViewPagerTabLayoutAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.dto.GoodsDetailsDto;
import com.ps.androidlib.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GoodsActivity extends BaseActivity<GoodsDetailsPresenter> implements GoodsDetailsContract.View {

    private String[] mTitle = {"商品", "详情", "评价"};
    private GoodsDetailsDto mGoodsDetails;
    @BindView(R.id.s_tab_layout)
    SlidingTabLayout mTabLayout;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindView(R.id.tv_follow)
    TextView tvFollow;

    private String productNo;

    private boolean isFollow;

    @Override
    protected int getContentView() {
        return R.layout.activity_goods;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return null;
    }

    @Override
    public GoodsDetailsPresenter getmPresenter() {
        return new GoodsDetailsPresenter(this);
    }

    @Override
    protected void onCreate() {
        productNo = getIntent().getStringExtra("productNo");
        mPresenter.getGoodsDetails(productNo);
    }

    @NonNull
    @Override
    protected List<CenterViewInterface> getCenterViews() {
        List<CenterViewInterface> centerViews = new ArrayList<>();
        centerViews.add(new GoodsCenterView());
        return centerViews;
    }

    @Override
    public void showGoodsDetails(GoodsDetailsDto goodsDetailsDto) {
        mGoodsDetails = goodsDetailsDto;
        isFollow = "0".equals(goodsDetailsDto.getIsfollow()) ? false : true;
        initViewPager(goodsDetailsDto);
        initBottom();
    }

    @Override
    public void followGoodsSuccess() {
        isFollow = !isFollow;
        setFollowGoods();
    }

    class GoodsCenterView implements CenterViewInterface {

        @Override
        public View getView() {
            return ViewUtils.getView(LayoutInflater.from(context), null, R.layout.center_view_goods);
        }

        @Override
        public void setViewWidget(View view) {

        }
    }

    /**
     * 加载商品信息、详情和评价 三个fragment
     *
     * @param goodsDetailsDto
     */
    private void initViewPager(GoodsDetailsDto goodsDetailsDto) {

        List<String> mTitleList = new ArrayList<>();
        for (String title : mTitle) {
            mTitleList.add(title);
        }

        List<Fragment> fragments = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putParcelable("goodsDetailsDto", goodsDetailsDto);
        fragments.add(GoodsInfoFragment.newInstance(bundle));//商品信息

        bundle = new Bundle();
        bundle.putStringArrayList("goodsDetails", (ArrayList<String>) goodsDetailsDto.getProductDetailList());
        fragments.add(GoodsDetailsFragment.newInstance(bundle));//商品详情
        fragments.add(GoodsInfoFragment.newInstance(null));//评价信息
        ViewPagerTabLayoutAdapter adapter = new ViewPagerTabLayoutAdapter(getSupportFragmentManager(), fragments, mTitleList);
        mViewPager.setAdapter(adapter);
        mTabLayout.setViewPager(mViewPager);
    }

    /**
     * 初始化 下部功能区 按钮
     */
    private void initBottom() {
        setFollowGoods();
    }

    /**
     * 设置改商品是否被关注
     */
    private void setFollowGoods() {
        Drawable top;
        if (isFollow) {
            top = getResources().getDrawable(R.mipmap.ic_followed_60);
            tvFollow.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        } else {
            top = getResources().getDrawable(R.mipmap.ic_unfollow_60);
        }
        tvFollow.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
    }

    @OnClick(R.id.tv_follow)
    public void followGoods(View view) {
        if (mGoodsDetails != null) {
            mPresenter.followGoods(mGoodsDetails.getProductNo());
        }
    }

}
