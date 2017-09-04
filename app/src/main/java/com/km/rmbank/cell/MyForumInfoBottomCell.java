package com.km.rmbank.cell;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;

import com.flyco.tablayout.SlidingTabLayout;
import com.km.rmbank.R;
import com.km.rmbank.adapter.ViewPagerTabLayoutAdapter;
import com.km.rmbank.module.personal.forum.ForumOfMyCommentFragment;
import com.km.rmbank.module.personal.forum.ForumsOfMyselfFragment;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamangkeji on 17/8/14.
 */

public class MyForumInfoBottomCell extends BaseCell<String> {

    private String[] mTitle = {"主贴","参与"};
    private FragmentManager mFragmentManager;
    private ViewPager mViewPager;

    private ForumsOfMyselfFragment forumsOfMyselfFragment;
    private ForumOfMyCommentFragment forumOfMyCommentFragment;

    public MyForumInfoBottomCell(String mData,FragmentManager mFragmentManager) {
        super(mData, R.layout.cell_my_forum_info_bottom);
        this.mFragmentManager = mFragmentManager;
    }

    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        initViewPager(holder);
    }

    private void initViewPager(BaseViewHolder holder) {
        mViewPager = holder.findView(R.id.viewpager);
        SlidingTabLayout mTabLayout = holder.findView(R.id.s_tab_layout);

        List<String> mTitleList = new ArrayList<>();
        for (String title : mTitle) {
            mTitleList.add(title);
        }

        List<Fragment> fragments = new ArrayList<>();
        forumsOfMyselfFragment = ForumsOfMyselfFragment.newInstance(null);
        forumOfMyCommentFragment = ForumOfMyCommentFragment.newInstance(null);
        fragments.add(forumsOfMyselfFragment);
        fragments.add(forumOfMyCommentFragment);

        ViewPagerTabLayoutAdapter adapter = new ViewPagerTabLayoutAdapter(mFragmentManager, fragments, mTitleList);
        mViewPager.setAdapter(adapter);
        mTabLayout.setViewPager(mViewPager);
    }


    public LinearLayoutManager getCurViewLlm(){
        int position = mViewPager.getCurrentItem();
        LinearLayoutManager llm;
        if (position == 0){
            llm = forumsOfMyselfFragment.getLayoutManager();
        } else {
            llm = forumOfMyCommentFragment.getLayoutManager();
        }

        return llm;
    }

}
