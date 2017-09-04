package com.km.rmbank.module.personal.forum;

import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.cell.MyForumInfoBottomCell;
import com.km.rmbank.cell.MyForumInfoTopCell;
import com.km.rmbank.dto.ForumDto;
import com.km.rmbank.dto.ForumInfoDto;
import com.km.rmbank.event.MyForumCommentEvent;
import com.km.rmbank.ui.FirstRecyclerView;
import com.km.rv_libs.TemplateAdapter;
import com.km.rv_libs.base.ICell;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.EventBusUtils;
import com.ps.androidlib.utils.KeyboardUtils;
import com.ps.androidlib.utils.KeyboardWrapper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyForumInfosActivity extends BaseActivity<MyForumInfosPresenter> implements MyForumInfosContract.View {

    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;

    @BindView(R.id.rv_forum)
    FirstRecyclerView rvForum;

    private KeyboardWrapper mKeyboardWrapper;

    private MyForumInfoBottomCell forumInfoBottomCell;
    private MyForumInfoTopCell forumInfoTopCell;


    @BindView(R.id.ll_comment)
    RelativeLayout llComment;
    @BindView(R.id.et_comment)
    EditText etComment;
    private MyForumCommentEvent myForumLikeOrCommentEvent;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_forum_infos;
    }

    @Override
    protected String getTitleName() {
        return "捡漏专区";
    }

    @Override
    public MyForumInfosPresenter getmPresenter() {
        return new MyForumInfosPresenter(this);
    }

    @Override
    protected void onCreate() {
        setRightIconClick(R.mipmap.ic_my_forum_info_release, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toNextActivity(ReleaseForumActivity.class);
            }
        });

        mKeyboardWrapper = new KeyboardWrapper(rlRoot);
        mKeyboardWrapper.setHideKeyboardListener(new KeyboardWrapper.OnHideKeyboardListener() {
            @Override
            public void hideKeyboard() {
                llComment.setVisibility(View.GONE);
            }
        });
        initViewPager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getMyForumInfos();
    }

    private void initViewPager() {

        RVUtils.setLinearLayoutManage(rvForum, LinearLayoutManager.VERTICAL);
        TemplateAdapter adapter = new TemplateAdapter();
        List<ICell> forumCells = new ArrayList<>();
        forumInfoTopCell = new MyForumInfoTopCell(null);
        forumCells.add(forumInfoTopCell);
        forumInfoBottomCell = new MyForumInfoBottomCell(null, getSupportFragmentManager());
        forumCells.add(forumInfoBottomCell);
        adapter.addAll(forumCells);
        rvForum.setAdapter(adapter);
        rvForum.setSubRvFirstVisibleListener(new FirstRecyclerView.SubRvFirstVisibleListener() {
            @Override
            public boolean getSubRvFirstVisible(MotionEvent event) {
                LinearLayoutManager llm = forumInfoBottomCell.getCurViewLlm();
                if (llm.findFirstVisibleItemPosition() == 0) {
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void showMyForumInfo(ForumInfoDto forumInfoDto) {
        forumInfoTopCell.setData(forumInfoDto);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myForumLikeOrComment(MyForumCommentEvent event) {
        if (!event.isRequest()){
            return;
        }
        myForumLikeOrCommentEvent = event;
        llComment.setVisibility(View.VISIBLE);
        etComment.requestFocus();
        KeyboardUtils.showSoftInput(etComment);
    }

    @OnClick(R.id.btn_confirm)
    public void confirmComment(View view) {
        if (!myForumLikeOrCommentEvent.isRequest()) {
            return;
        }
        String comment = etComment.getText().toString();
        if (TextUtils.isEmpty(comment)) {
            showToast("提交的评论不能为空");
            return;
        }

        KeyboardUtils.hideSoftInput(this, etComment);

        myForumLikeOrCommentEvent.setRequest(false);
        myForumLikeOrCommentEvent.setNewComment(comment);
        myForumLikeOrCommentEvent.setTo(myForumLikeOrCommentEvent.getFrom());
        EventBusUtils.post(myForumLikeOrCommentEvent);
        etComment.setText("");
//        ForumDto forumDto = myForumLikeOrCommentEvent.getForumDto();
//        mPresenter.addForumComment(forumDto.getId(), comment, myForumLikeOrCommentEvent.getPosition());
    }
}
