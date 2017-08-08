package com.km.rmbank.module.personal.mycontact;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.utils.EaseUserChatUtils;
import com.km.rmbank.R;
import com.km.rmbank.adapter.MyContactAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.MyFriendsDto;
import com.km.rmbank.event.UpdateEaseUserUnreadNumberEvent;
import com.km.rmbank.module.chat.EaseChatActivity;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.SwipeRefreshUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

public class MyContactActivity extends BaseActivity<MyContactPresenter> implements MyContactContract.View {

//    @BindView(R.id.recyclerview)
//    RecyclerView mRecyclerview;

    @BindView(R.id.fl_contact)
    FrameLayout flContact;

    private EaseContactListFragment mEaseContactListFragment;
    @Override
    protected int getContentView() {
        return R.layout.activity_my_contact;
    }

    @Override
    protected String getTitleName() {
        return "我的人脉";
    }

    @Override
    public MyContactPresenter getmPresenter() {
        return new MyContactPresenter(this);
    }

    @Override
    protected void onCreate() {
        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swiper_refresh);
    }

    @Override
    public void initRecyclerview() {
        mPresenter.getMyFriends();
    }

    @Override
    public void showMyContact(final List<MyFriendsDto> myFriendsDtos) {
        for (MyFriendsDto friendsDto : myFriendsDtos){
            EaseUser user = new EaseUser(friendsDto.getMobilePhone());
            user.setAvatar(friendsDto.getPortraitUrl());
            user.setNickname(friendsDto.getNickName());
            EaseUserChatUtils.addUser(user);
        }

        mEaseContactListFragment = new EaseContactListFragment();
        mEaseContactListFragment.setmEaseUsers(EaseUserChatUtils.getUsers());
        mEaseContactListFragment.setContactListItemClickListener(new EaseContactListFragment.EaseContactListItemClickListener() {
            @Override
            public void onListItemClicked(EaseUser user) {
                user.setUnread(false);
                Bundle bundle = new Bundle();
                bundle.putString("to_user_id",user.getUsername());
                bundle.putString("user_nick_name",user.getNickname());
                toNextActivity(EaseChatActivity.class,bundle);
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_contact,mEaseContactListFragment).commit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUnreadNumber(UpdateEaseUserUnreadNumberEvent event){
        int position = EaseUserChatUtils.getPosition(event.getmEaseUser());
        if (position > 0){
            mEaseContactListFragment.updateUnreadNumber(position);
        }
    }
}
