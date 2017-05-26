package com.km.rmbank.module.personal.goodsmanager;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.GoodsTypeAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.GoodsTypeDto;
import com.km.rmbank.dto.HomeGoodsTypeDto;
import com.km.rmbank.event.GoodsTypeEvent;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.EventBusUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

public class GoodsTypeActivity extends BaseActivity<GoodsTypePresenter> implements GoodsTypeContract.View {

    private int[] backgroundRes = {R.drawable.shape_new_goods_select_goods_type_1,
            R.drawable.shape_new_goods_select_goods_type_2,
            R.drawable.shape_new_goods_select_goods_type_3,
            R.drawable.shape_new_goods_select_goods_type_4,
            R.drawable.shape_new_goods_select_goods_type_5,
            R.drawable.shape_new_goods_select_goods_type_6,
            R.drawable.shape_new_goods_select_goods_type_7};

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private HomeGoodsTypeDto goodsTypeDto;

    @BindView(R.id.title)
    TextView title;

    private boolean isTwoLevel = false;
    @Override
    protected int getContentView() {
        return R.layout.activity_goods_type;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "商品类型";
    }

    @Override
    public GoodsTypePresenter getmPresenter() {
        return new GoodsTypePresenter(this);
    }

    @Override
    protected void onCreate() {
        isTwoLevel = getIntent().getBooleanExtra("isTwoLevel",false);
        Logger.d(this.toString());
        String rightBtn = "下一步";
        title.setText("一级分类");
        if (isTwoLevel){
            rightBtn = "保存";
            title.setText("二级分类");
        }

        setRightBtnClick(rightBtn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsTypeAdapter adapter = (GoodsTypeAdapter) mRecyclerView.getAdapter();
                HomeGoodsTypeDto goodsTypeDto = adapter.getCheckedGoodsType();
                List<HomeGoodsTypeDto> subGoodsTypeDtos = goodsTypeDto.getTypeList();
                if (goodsTypeDto.isEmpty()){
                    showToast("请选择商品类型");
                    return;
                }

                if (isTwoLevel){
                    EventBusUtils.post(new GoodsTypeEvent(GoodsTypeActivity.this.goodsTypeDto,goodsTypeDto));
                    toNextActivity(CreateNewGoodsActivity.class);
                } else if (subGoodsTypeDtos.size() > 0){
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isTwoLevel",true);
                    bundle.putParcelable("goodsTypeDto",goodsTypeDto);
                    toNextActivity(GoodsTypeActivity.class,bundle);
                }
//                else {
//                    EventBusUtils.post(new GoodsTypeEvent(goodsTypeDto,null));
//                    toNextActivity(CreateNewGoodsActivity.class);
//                }

//                Bundle bundle = new Bundle();
//                bundle.putParcelable("goodsTypeDto",goodsTypeDto);
//                setResult(1000,bundle);
            }
        });
        goodsTypeDto = getIntent().getParcelableExtra("goodsTypeDto");

    }

    @Override
    public void initGoodsTypeView() {
        RVUtils.setGridLayoutManage(mRecyclerView,4);
//        RVUtils.addDivideItemForRv(mRecyclerView,0xffffffff);
        GoodsTypeAdapter adapter = new GoodsTypeAdapter(this);
        mRecyclerView.setAdapter(adapter);

        if (isTwoLevel){
            showGoodsType(goodsTypeDto.getTypeList());
        } else {
            mPresenter.getGoodsType();
        }
    }

    @Override
    public void showGoodsType(List<HomeGoodsTypeDto> goodsTypeDtos) {
        if (isTwoLevel){
            Random mRandom = new Random();
            for (HomeGoodsTypeDto goodsTypeDto : goodsTypeDtos){
                goodsTypeDto.setBackgroundRes(backgroundRes[mRandom.nextInt(backgroundRes.length)]);
            }
        }
        GoodsTypeAdapter adapter = (GoodsTypeAdapter) mRecyclerView.getAdapter();
        adapter.addData(goodsTypeDtos);
        adapter.setDefaultChecked(goodsTypeDto);
    }
}
