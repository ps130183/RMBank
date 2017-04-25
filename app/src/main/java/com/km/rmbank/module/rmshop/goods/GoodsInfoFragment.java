package com.km.rmbank.module.rmshop.goods;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.event.ConfirmGoodsNumberEvent;
import com.km.rmbank.event.GoodsDetailNumberEvent;
import com.km.rmbank.event.OtherAddressEvent;
import com.km.rmbank.module.personal.receiveraddress.ReceiverAddressActivity;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.BannerUtils;
import com.ps.androidlib.utils.EventBusUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by kamangkeji on 17/3/17.
 */

public class GoodsInfoFragment extends BaseFragment {

    @BindView(R.id.banner)
    Banner banner;

    @BindView(R.id.tv_goods_title)
    TextView tvGoodsTitle;
    @BindView(R.id.tv_goods_sub_title)
    TextView tvGoodsSubTitle;
    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;

    @BindView(R.id.tv_freight_intro)
    TextView tvFreightIntro;

    @BindView(R.id.tv_goods_number)
    TextView tvGoodsNumber;
    private int goodsCount = 1;

    @BindView(R.id.tv_address)
    TextView tvAddress;

    public static GoodsInfoFragment newInstance(Bundle bundle) {
        GoodsInfoFragment fragment = new GoodsInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_goods_info;
    }

    @Override
    protected void createView() {
        Bundle bundle = getArguments();
        if (bundle == null){
            return;
        }
        GoodsDetailsDto goodsDetailsDto = bundle.getParcelable("goodsDetailsDto");
        initBanner(goodsDetailsDto.getProductBannerList());
        initGoodsOtherInfo(goodsDetailsDto);
    }


    /**
     * 加载bannner数据
     * @param bannerUrls
     */
    private void initBanner(final List<String> bannerUrls){
        int windowWidth = AppUtils.getCurWindowWidth(getContext());
        banner.getLayoutParams().height = windowWidth;
        BannerUtils.initBannerFromUrl(banner, bannerUrls, new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
//                showToast(bannerUrls.get(position) + "");
            }
        });
    }

    private void initGoodsOtherInfo(GoodsDetailsDto goodsDetailsDto){
        tvGoodsTitle.setText(goodsDetailsDto.getName());
        tvGoodsSubTitle.setText(goodsDetailsDto.getSubtitle());
        tvGoodsPrice.setText("¥"+goodsDetailsDto.getPrice());

        tvFreightIntro.setText("单个商品运费"+ goodsDetailsDto.getFreightInMaxCount() +"元，每增加一件"+ goodsDetailsDto.getFreightInEveryAdd() +"元");

        setReceiverAddress(goodsDetailsDto.getReceiverAddressDto());
    }


    @OnClick(R.id.iv_more_number)
    public void showChooseGoodsNumber(View view){
        EventBusUtils.post(new GoodsDetailNumberEvent(goodsCount));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiverConfirmGoodsNumber(ConfirmGoodsNumberEvent event){
        goodsCount = event.getGoodsNumber();
        tvGoodsNumber.setText(goodsCount + "个");
    }

    @OnClick(R.id.iv_other_address)
    public void selectOtherAddress(View view){
        Bundle bundle = new Bundle();
        bundle.putBoolean("select_other_address",true);
        toNextActivity(ReceiverAddressActivity.class,bundle);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void otherAddress(OtherAddressEvent event){
        setReceiverAddress(event.getReceiverAddressDto());
    }

    private void setReceiverAddress(ReceiverAddressDto receiverAddressDto){
        if (receiverAddressDto != null){
            tvAddress.setText(receiverAddressDto.getReceiveAddress());
        } else {
            tvAddress.setText("请选择收货地址");
        }
    }
}
