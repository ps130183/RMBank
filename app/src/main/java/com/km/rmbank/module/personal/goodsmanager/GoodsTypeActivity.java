package com.km.rmbank.module.personal.goodsmanager;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.adapter.GoodsTypeAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.GoodsTypeDto;

import java.util.List;

import butterknife.BindView;

public class GoodsTypeActivity extends BaseActivity<GoodsTypePresenter> implements GoodsTypeContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private GoodsTypeDto goodsTypeDto;
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
        setRightBtnClick("保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsTypeAdapter adapter = (GoodsTypeAdapter) mRecyclerView.getAdapter();
                GoodsTypeDto goodsTypeDto = adapter.getCheckedGoodsType();
                if (goodsTypeDto.isEmpty()){
                    showToast("请选择商品类型");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable("goodsTypeDto",goodsTypeDto);
                setResult(1000,bundle);
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
    }

    @Override
    public void showGoodsType(List<GoodsTypeDto> goodsTypeDtos) {
        GoodsTypeAdapter adapter = (GoodsTypeAdapter) mRecyclerView.getAdapter();
        adapter.addData(goodsTypeDtos);
        adapter.setDefaultChecked(goodsTypeDto);
    }
}
