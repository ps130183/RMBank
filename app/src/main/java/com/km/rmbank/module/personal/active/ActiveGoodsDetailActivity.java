package com.km.rmbank.module.personal.active;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.km.rmbank.R;
import com.km.rmbank.adapter.ViewPagerTabLayoutAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.ActiveGoodsDto;
import com.km.rmbank.event.ConfirmGoodsNumberEvent;
import com.km.rmbank.event.GoodsDetailNumberEvent;
import com.ps.androidlib.animator.ShowViewAnimator;
import com.ps.androidlib.utils.DialogUtils;
import com.ps.androidlib.utils.EventBusUtils;
import com.ps.androidlib.utils.ViewUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ActiveGoodsDetailActivity extends BaseActivity<ActiveGoodsDetailPresenter> implements ActiveGoodsDetailContract.View {

    private String[] mTitle = {"商品", "详情"};
    private ActiveGoodsDto mGoodsDetails;
    @BindView(R.id.s_tab_layout)
    SlidingTabLayout mTabLayout;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

//    @BindView(R.id.tv_follow)
//    TextView tvFollow;

    private String productNo;


    //以下是 选择商品的数量
    @BindView(R.id.rl_set_goods_number)
    RelativeLayout rlSetGoodsNumbenr;
    @BindView(R.id.ll_set_goods_number)
    LinearLayout llSetGoodsNumber;
    @BindView(R.id.et_buy_goods_count)
    EditText etBuyGoodsCount;

    private ActiveGoodsInfoFragment mActiveGoodsInfoFragment;


    @Override
    protected int getContentView() {
        return R.layout.activity_active_goods_detail;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return null;
    }

    @Override
    public ActiveGoodsDetailPresenter getmPresenter() {
        return new ActiveGoodsDetailPresenter(this);
    }

    @Override
    protected void onCreate() {
        productNo = getIntent().getStringExtra("productNo");
        mPresenter.getActiveGoodsDetailInfo(productNo);
    }

    @NonNull
    @Override
    protected List<CenterViewInterface> getCenterViews() {
        List<CenterViewInterface> centerViews = new ArrayList<>();
        centerViews.add(new GoodsCenterView());
        return centerViews;
    }


    @Override
    public void showActiveGoodsDetailInfo(ActiveGoodsDto activeGoodsDto) {
        mGoodsDetails = activeGoodsDto;
        initViewPager(activeGoodsDto);
        initShowGoodsNumber();
    }

    @OnClick(R.id.tv_convert)
    public void convertActiveGoods(View view){
        final String productCount = etBuyGoodsCount.getText().toString();
        final String receiveAddressId = mActiveGoodsInfoFragment == null ? "" : mActiveGoodsInfoFragment.getmReceiverAddress().getId();
        if (TextUtils.isEmpty(receiveAddressId)){
            showToast("请先选择收货地址");
            return;
        }
        DialogUtils.showDefaultAlertDialog("是否要兑换该商品？", new DialogUtils.ClickListener() {
            @Override
            public void clickConfirm() {
                mPresenter.convertActiveGoods(productNo,productCount,receiveAddressId);
            }
        });
    }

    @Override
    public void convertSuccess() {
        DialogUtils.showDefaultAlertDialog("兑换成功，请到 我的-活跃值-兑换商品-兑换清单 中查看。", new DialogUtils.ClickListener() {
            @Override
            public void clickConfirm() {

            }
        });
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
    private void initViewPager(ActiveGoodsDto goodsDetailsDto) {

        List<String> mTitleList = new ArrayList<>();
        for (String title : mTitle) {
            mTitleList.add(title);
        }

        List<Fragment> fragments = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putParcelable("goodsDetailsDto", goodsDetailsDto);
        if (mActiveGoodsInfoFragment == null){
            mActiveGoodsInfoFragment = ActiveGoodsInfoFragment.newInstance(bundle);
        }
        fragments.add(mActiveGoodsInfoFragment);//商品信息

        bundle = new Bundle();
        bundle.putStringArrayList("goodsDetails", (ArrayList<String>) goodsDetailsDto.getProductDetailList());
        fragments.add(ActiveGoodsDetailsFragment.newInstance(bundle));//商品详情

//        bundle = new Bundle();
//        bundle.putParcelable("goodsDetailsDto", goodsDetailsDto);
//        fragments.add(GoodsEvaluateFragment.newInstance(bundle));//评价信息
        ViewPagerTabLayoutAdapter adapter = new ViewPagerTabLayoutAdapter(getSupportFragmentManager(), fragments, mTitleList);
        mViewPager.setAdapter(adapter);
        mTabLayout.setViewPager(mViewPager);
    }

    /** ----------------- 以下是 选择商品数量 --------------------- */

    @OnClick(R.id.rl_set_goods_number)
    public void onRlTouch(View v){
        EventBusUtils.post(new GoodsDetailNumberEvent(goodsCount));
    }

    @OnClick(R.id.ll_set_goods_number)
    public void onllTouch(View v){

    }

    private ShowViewAnimator animator;
    private void initShowGoodsNumber(){
        animator = new ShowViewAnimator();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showGoodsNumber(GoodsDetailNumberEvent event){
        goodsCount = event.getNumber();
        addOrCutGoodsCount(0);
        if (rlSetGoodsNumbenr.getVisibility() == View.GONE){
            rlSetGoodsNumbenr.setVisibility(View.VISIBLE);
        }

        animator.showViewByAnimator(llSetGoodsNumber, new ShowViewAnimator.onHideListener() {
            @Override
            public void hide() {
                rlSetGoodsNumbenr.setVisibility(View.GONE);
            }
        });
    }

    @OnClick(R.id.btn_add)
    public void addNumber(View view){
        addOrCutGoodsCount(1);
    }

    @OnClick(R.id.btn_cut)
    public void  cutNumber(View view){
        addOrCutGoodsCount(-1);
    }

    @OnClick(R.id.btn_confirm)
    public void confirmNumber(View view){
        EventBusUtils.post(new GoodsDetailNumberEvent(goodsCount));
        EventBusUtils.post(new ConfirmGoodsNumberEvent(goodsCount));
    }

    private int goodsCount = 1;
    /**
     * 选择商品数量  加 或者 减
     *
     * @param count
     */
    private void addOrCutGoodsCount(int count) {

//        int stock = mGoodsDetails.getResidualStock();
        if (goodsCount > 0 ) {
            goodsCount += count;
        }
        etBuyGoodsCount.setText(goodsCount + "");
    }

    /** ----------------- 以上是 选择商品数量 --------------------- */

}
