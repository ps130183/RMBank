package com.km.rmbank.module.nearbyvip;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.km.rmbank.R;
import com.km.rmbank.adapter.NearbyVipAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.NearbyVipDto;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.module.personal.userinfo.EditUserCardActivity;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.SwipeRefreshUtils;
import com.km.rmbank.utils.retrofit.SecretConstant;

import java.util.List;

import butterknife.BindView;

public class NearbyVipActivity extends BaseActivity<NearbyVipPresenter> implements NearbyVipContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private static final String QRCODE_URL = SecretConstant.API_HOST + SecretConstant.API_HOST_PATH + "/user/saoUserCard/info?mobilePhone=";
    @Override
    protected int getContentView() {
        return R.layout.activity_nearby_vip;
    }

    @Override
    protected String getTitleName() {
        return "附近合伙人";
    }

    @Override
    public NearbyVipPresenter getmPresenter() {
        return new NearbyVipPresenter(this);
    }

    @Override
    protected void onCreate() {
        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swiper_refresh);
    }

    @Override
    public void initRecyclerView() {
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView);
        final NearbyVipAdapter adapter = new NearbyVipAdapter(this);
        mRecyclerView.setAdapter(adapter);
        adapter.addLoadMore(mRecyclerView, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getNearbyVip(adapter.getNextPage());
            }
        });
        mPresenter.getNearbyVip(1);
        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<NearbyVipDto>() {
            @Override
            public void onItemClick(NearbyVipDto itemData, int position) {
                if (TextUtils.isEmpty(itemData.getAllowStutas()) || "0".equals(itemData.getAllowStutas())){
                    mPresenter.getUserCardInfo(QRCODE_URL + itemData.getMobilePhone());
                } else {
                    showToast("该用户不允许查看名片");
                }
            }

        });

        SwipeRefreshUtils.initSwipeRefresh(mSwipeRefresh, new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getNearbyVip(1);
            }
        });
    }

    @Override
    public void getNearbyVipSuccess(List<NearbyVipDto> nearbyVipDtos, int pageNo) {
        NearbyVipAdapter adapter = (NearbyVipAdapter) mRecyclerView.getAdapter();
        adapter.addData(nearbyVipDtos,pageNo);
    }

    @Override
    public void showUserCart(UserCardDto userCardDto, String phone) {
        if (userCardDto.isEmpty()){
            showToast("该用户尚未编辑名片");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("userCardDto",userCardDto);
        bundle.putString("friendPhone",phone);
        toNextActivity(EditUserCardActivity.class,bundle);
    }
}
