package com.km.rmbank.module.personal.order.detail.evaluate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.OrderDto;
import com.km.rmbank.event.EvaluateSuccessEvent;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.EventBusUtils;
import com.ps.androidlib.utils.glide.GlideUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class GoodsEvaluateActivity extends BaseActivity<GoodsEvaluatePresenter> implements GoodsEvaluateContract.View {

    @BindView(R.id.et_evaluate)
    EditText etEvaluate;


    private OrderDto mOrderDto;

    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.iv_goods)
    ImageView ivGoods;
    @BindView(R.id.tv_goods_name)
    TextView tvGodosName;
    @BindView(R.id.tv_goods_count)
    TextView tvGoodsCount;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;

    @Override
    protected int getContentView() {
        return R.layout.activity_goods_evaluate;
    }

    @Override
    protected String getTitleName() {
        return "商品评论";
    }

    @Override
    public GoodsEvaluatePresenter getmPresenter() {
        return new GoodsEvaluatePresenter(this);
    }

    @Override
    protected void onCreate() {
        mOrderDto = getIntent().getParcelableExtra("orderDto");
        initView(mOrderDto);
    }

    private void initView(OrderDto orderDto){
        tvShopName.setText(orderDto.getShopName());
        GlideUtils.loadImage(ivGoods,orderDto.getThumbnailUrl());
        tvGodosName.setText(orderDto.getProductName());
        tvGoodsCount.setText(orderDto.getProductCount()+"");
        tvTotalMoney.setText(orderDto.getTotalPrice());
    }

    @OnClick(R.id.btn_evaluate)
    public void evaluate(View view){
        String content = etEvaluate.getText().toString();
        if (TextUtils.isEmpty(content)){
            showToast("请填写评论的内容");
            return;
        }
        mPresenter.evaluateGoods(mOrderDto.getOrderNo(),content);
    }

    @Override
    public void evaluateSuccess() {
        showToast("评论成功");
        EventBusUtils.post(new EvaluateSuccessEvent(mOrderDto.getOrderNo()));
        finish();
    }
}
