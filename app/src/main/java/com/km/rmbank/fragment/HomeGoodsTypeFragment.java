package com.km.rmbank.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.km.rmbank.R;
import com.km.rmbank.adapter.HomeGoodsTypeAdapter;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.HomeGoodsTypeDto;
import com.km.rmbank.entity.HomeGtEntity;
import com.km.rmbank.module.personal.goodsmanager.GoodsTypeActivity;
import com.km.rmbank.module.rmshop.goods.RmShopActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeGoodsTypeFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    public static HomeGoodsTypeFragment newInstance(Bundle bundle) {
        HomeGoodsTypeFragment fragment = new HomeGoodsTypeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home_goods_type;
    }

    @Override
    protected void createView() {



        RVUtils.setGridLayoutManage(mRecyclerView,4);
        HomeGoodsTypeAdapter adapter = new HomeGoodsTypeAdapter(getContext());
        mRecyclerView.setAdapter(adapter);

        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<HomeGoodsTypeDto>() {
            @Override
            public void onItemClick(HomeGoodsTypeDto itemData, int position) {
//               showToast(itemData.getProductTypeName());
                if (itemData.getProductTypeName().indexOf("全部") >= 0){
//                    showToast("全部");
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("fromHome",true);
                    toNextActivity(GoodsTypeActivity.class,bundle);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putBoolean("isLevelOne",true);
                bundle.putString("levelOneId",itemData.getId());
                bundle.putString("levelTwoId","");
                toNextActivity(RmShopActivity.class,bundle);
            }
        });


        List<HomeGoodsTypeDto> mDatas = getArguments().getParcelableArrayList("goodsTypes");
        adapter.addData(mDatas);

    }

}
