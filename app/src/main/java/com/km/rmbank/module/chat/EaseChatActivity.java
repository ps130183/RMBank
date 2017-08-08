package com.km.rmbank.module.chat;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.utils.EaseUserChatUtils;
import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.module.personal.userinfo.EditUserCardActivity;
import com.km.rmbank.module.personal.userinfo.UserCardInfoActivity;
import com.km.rmbank.utils.Constant;

import butterknife.BindView;

public class EaseChatActivity extends BaseActivity<EaseChatPresenter> implements EaseChatContract.View {

    @BindView(R.id.fl_chat)
    FrameLayout flChat;

    @BindView(R.id.title)
    TextView mTitle;

    private String userid;

    @Override
    protected int getContentView() {
        return R.layout.activity_ease_chat;
    }

    @Override
    public EaseChatPresenter getmPresenter() {
        return new EaseChatPresenter(this);
    }

    @Override
    protected String getTitleName() {
        return null;
    }

    @Override
    protected void onCreate() {

        userid = getIntent().getStringExtra("to_user_id");
        String nickName = getIntent().getStringExtra("user_nick_name");
        boolean isService = getIntent().getBooleanExtra("isService",false);
        mTitle.setText(nickName);

        if (!isService){
            setRightIconClick(R.mipmap.ic_ease_chat_user, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.getUserCardInfo(Constant.QRCODE_URL + userid);
                }
            });
        }
    }

    @Override
    public void showUserCart(UserCardDto userCardDto, String phone) {
        if (userCardDto.isEmpty2()){
            showToast("该用户尚未编辑名片");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("userCardDto",userCardDto);
        bundle.putString("friendPhone",phone);
        toNextActivity(UserCardInfoActivity.class,bundle);
    }

    @Override
    public void showCurLoginUserInfo(UserInfoDto userInfoDto) {
        EaseUser easeUser = new EaseUser(Constant.user.getMobilePhone());
        easeUser.setAvatar(userInfoDto.getPortraitUrl());
        easeUser.setNickname(userInfoDto.getNickName());
        EaseUserChatUtils.addCurLoginUser(easeUser);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        EaseChatFragment chatFragment = new EaseChatFragment();

        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
//        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
        args.putString(EaseConstant.EXTRA_USER_ID, userid);
        chatFragment.setArguments(args);
        ft.replace(R.id.fl_chat,chatFragment);
        ft.commit();
    }
}
