package com.km.rmbank.cell;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.module.club.ClubActionPastFragment;
import com.km.rmbank.module.club.ClubActionRecentFragment;
import com.km.rmbank.module.club.ClubInfoDetailsFragment;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamangkeji on 17/7/7.
 */

public class ClubInfoDetailsCell extends BaseCell<ClubDto> {

    private boolean isMyClub;
    private Context mContext;
    private FragmentManager mFragmentManager;

    private TextView tvClubIntroduce;
    private TextView tvActionRecent;
    private TextView tvActionPast;
    private List<Fragment> fragmentList;

    private onSelectCurFragmentListener selectCurFragmentListener;

    public ClubInfoDetailsCell(Context mContext, ClubDto mData, FragmentManager mFragmentManager) {
        super(mData, R.layout.cell_club_info_details);
        this.mContext = mContext;
        this.mFragmentManager = mFragmentManager;
    }

    @Override
    public int getItemViewType() {
        return 2;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

//        final ViewPager mViewPager = holder.findView(R.id.viewpager);
        if (mData != null && mData.getClubDetailList() != null) {


            Bundle bundle = new Bundle();
            bundle.putParcelable("clubDto",mData);
            bundle.putBoolean("isMyClub",isMyClub);
            fragmentList = new ArrayList<>();
            fragmentList.add(ClubInfoDetailsFragment.newInstance(bundle));
            fragmentList.add(ClubActionRecentFragment.newInstance(bundle));
            fragmentList.add(ClubActionPastFragment.newInstance(bundle));
//        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(mFragmentManager,fragmentList);
//        mViewPager.setAdapter(viewPagerAdapter);

//        FrameLayout flClubContent = holder.findView(R.id.fl_club_content);
            mFragmentManager.beginTransaction().replace(R.id.fl_club_content, fragmentList.get(0)).commit();

            tvClubIntroduce = holder.getTextView(R.id.tv_club_introduce);
            tvActionRecent = holder.getTextView(R.id.tv_action_recent);
            tvActionPast = holder.getTextView(R.id.tv_action_past);

            tvClubIntroduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setCurFragment(0);
                    selectCurFragmentListener.setCurFragment(0);
                }
            });
            tvActionRecent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setCurFragment(1);
                    selectCurFragmentListener.setCurFragment(1);
                }
            });
            tvActionPast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setCurFragment(2);
                    selectCurFragmentListener.setCurFragment(2);
                }
            });
        }
    }

    public void setCurFragment(int position) {
        switch (position) {
            case 0:
                tvClubIntroduce.setTextColor(ContextCompat.getColor(mContext, R.color.color_pink));
                tvActionRecent.setTextColor(ContextCompat.getColor(mContext, R.color.color_block));
                tvActionPast.setTextColor(ContextCompat.getColor(mContext, R.color.color_block));
                break;
            case 1:
                tvClubIntroduce.setTextColor(ContextCompat.getColor(mContext, R.color.color_block));
                tvActionRecent.setTextColor(ContextCompat.getColor(mContext, R.color.color_pink));
                tvActionPast.setTextColor(ContextCompat.getColor(mContext, R.color.color_block));
                break;
            case 2:
                tvClubIntroduce.setTextColor(ContextCompat.getColor(mContext, R.color.color_block));
                tvActionRecent.setTextColor(ContextCompat.getColor(mContext, R.color.color_block));
                tvActionPast.setTextColor(ContextCompat.getColor(mContext, R.color.color_pink));
                break;
        }

        mFragmentManager.beginTransaction().replace(R.id.fl_club_content, fragmentList.get(position)).commit();
    }

    public interface onSelectCurFragmentListener {
        void setCurFragment(int position);
    }

    public void setSelectCurFragmentListener(onSelectCurFragmentListener selectCurFragmentListener) {
        this.selectCurFragmentListener = selectCurFragmentListener;
    }

    public void setMyClub(boolean myClub) {
        isMyClub = myClub;
    }
}
