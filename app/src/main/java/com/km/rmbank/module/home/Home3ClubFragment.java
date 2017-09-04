package com.km.rmbank.module.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.Home3ActionPastAdapter;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.InformationDto;
import com.km.rmbank.module.club.past.ActionPastDetailActivity;
import com.km.rmbank.utils.SwipeRefreshUtils;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.BannerUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home3ClubFragment extends BaseFragment<Home3ClubPresenter> implements Home3ClubContract.View {

    private int mWindowWidth = 0;
    @BindView(R.id.banner)
    Banner mBanner;

    @BindView(R.id.rv_club_action)
    RecyclerView rvClubAction;

    public static Home3ClubFragment newInstance(Bundle bundle) {

        Home3ClubFragment fragment = new Home3ClubFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home3_club;
    }

    @Override
    public Home3ClubPresenter getmPresenter() {
        return new Home3ClubPresenter(this);
    }

    @Override
    protected void createView() {
        mWindowWidth = AppUtils.getCurWindowWidth(getContext());
        mPresenter.getBannerList();
        initRecyclerview();
        SwipeRefreshUtils.initSwipeRefresh(mSwipeRefresh, new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Observable.just(1)
                        .delay(3, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(@NonNull Integer integer) throws Exception {
                                mPresenter.getBannerList();
                                mPresenter.getActionList(1);
                            }
                        });
            }
        });
    }

    private void initBannerData(final List<InformationDto> informationDtos) {
        mBanner.getLayoutParams().height = mWindowWidth / 320 * 141;
        List<String> bannerDataList = new ArrayList<>();
        for (InformationDto informationDto : informationDtos){
            String[] avatarUrls = informationDto.getAvatarUrl().split("#");
            bannerDataList.add(avatarUrls[0]);
        }
        BannerUtils.initBannerFromUrl(mBanner, bannerDataList, new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                InformationDto informationDto = informationDtos.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("actionPastId",informationDto.getId());
                toNextActivity(ActionPastDetailActivity.class,bundle);
            }
        });
    }

    private void initRecyclerview(){
        RVUtils.setLinearLayoutManage(rvClubAction, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvClubAction,RVUtils.DIVIDER_COLOR_DEFAULT,1);
        final Home3ActionPastAdapter adapter = new Home3ActionPastAdapter(getContext());
        adapter.setClub(true);
        rvClubAction.setAdapter(adapter);

        adapter.addLoadMore(rvClubAction, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getActionList(adapter.getNextPage());
            }
        });
        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<InformationDto>() {
            @Override
            public void onItemClick(InformationDto itemData, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("actionPastId",itemData.getId());
                toNextActivity(ActionPastDetailActivity.class,bundle);
            }

        });

        mPresenter.getActionList(1);

    }

    @Override
    public void showBannerList(List<InformationDto> informationDtos) {
        initBannerData(informationDtos);
    }

    @Override
    public void getActionListSuccess(List<InformationDto> actionDtos, int pageNo) {
        Home3ActionPastAdapter adapter = (Home3ActionPastAdapter) rvClubAction.getAdapter();
        adapter.addData(actionDtos,pageNo);
    }

}
