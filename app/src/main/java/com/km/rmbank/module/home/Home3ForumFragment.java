package com.km.rmbank.module.home;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.km.rmbank.R;
import com.km.rmbank.adapter.HomeForumAdapter;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.ForumDto;
import com.km.rmbank.module.home.image.PictureShowActivity;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.SwipeRefreshUtils;
import com.ps.androidlib.utils.KeyboardUtils;
import com.ps.androidlib.utils.KeyboardWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home3ForumFragment extends BaseFragment<Home3ForumPresenter> implements Home3ForumContract.View {

    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;

    private KeyboardWrapper mKeyboardWrapper;

    @BindView(R.id.rv_forum)
    RecyclerView rvForum;

    @BindView(R.id.ll_comment)
    RelativeLayout llComment;
    @BindView(R.id.et_comment)
    EditText etComment;


    private int mConfirmCommentPosition;

    public static Home3ForumFragment newInstance(Bundle bundle) {
        Home3ForumFragment fragment = new Home3ForumFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    protected int getContentView() {
        return R.layout.fragment_home3_forum;
    }

    @Override
    public Home3ForumPresenter getmPresenter() {
        return new Home3ForumPresenter(this);
    }

    @Override
    protected void createView() {
        mKeyboardWrapper = new KeyboardWrapper(rlRoot);
        mKeyboardWrapper.setHideKeyboardListener(new KeyboardWrapper.OnHideKeyboardListener() {
            @Override
            public void hideKeyboard() {
                llComment.setVisibility(View.GONE);
            }
        });
        initRecycler();
    }

    /**
     * 初始化列表
     */
    private void initRecycler(){

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
                                mPresenter.loadForumAllData(1);
                            }
                        });
            }
        });

        rvForum.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
                if (getChildCount() > 0) {
                    View view = recycler.getViewForPosition(0);
                    if(view != null){
                        measureChild(view, widthSpec, heightSpec);
                        int measuredWidth = View.MeasureSpec.getSize(widthSpec);
                        int measuredHeight = view.getMeasuredHeight();
                        setMeasuredDimension(measuredWidth, measuredHeight);
                    }
                } else {
                    super.onMeasure(recycler, state, widthSpec, heightSpec);
                }
            }
        });
//        RVUtils.setLinearLayoutManage(rvForum, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvForum,RVUtils.DIVIDER_COLOR_DEFAULT,2);
        final HomeForumAdapter adapter = new HomeForumAdapter(getContext());
        adapter.setmHeaderLayoutRes(R.layout.header_rv_home3_forum);
        rvForum.setAdapter(adapter);
        ((DefaultItemAnimator) rvForum.getItemAnimator()).setSupportsChangeAnimations(false);
        mPresenter.loadForumAllData(1);

        adapter.setOnClickMoreCommentListener(new HomeForumAdapter.OnClickMoreCommentListener() {
            @Override
            public void onClickMoreComment(ForumDto forumDto, int position) {
                mPresenter.loadMoreComment(forumDto.getId(),position);
            }
        });

        adapter.setOnClickLikeOrCommentListener(new HomeForumAdapter.OnClickLikeOrCommentListener() {
            @Override
            public void clickLikeForum(ForumDto forumDto, int position) {
                mPresenter.likeForum(forumDto.getId(),position);
            }

            @Override
            public void clickCommentForum(ForumDto forumDto, int position) {
                llComment.setVisibility(View.VISIBLE);
                etComment.requestFocus();
                KeyboardUtils.showSoftInput(etComment);
                mConfirmCommentPosition = position;
            }
        });
        adapter.setOnClickImageListener(new HomeForumAdapter.OnClickImageListener() {
            @Override
            public void clickImages(List<String> pictureUrls) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("pictureUrls", (ArrayList<String>) pictureUrls);
                toNextActivity(PictureShowActivity.class,bundle);
            }
        });
    }

    @Override
    public void showForum(List<ForumDto> forumDtos,int pageNo) {
        HomeForumAdapter adapter = (HomeForumAdapter) rvForum.getAdapter();
        adapter.addData(forumDtos,pageNo);
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void showMoreComment(List<ForumDto.ForumCommentDto> forumCommentDtos, int position) {
        HomeForumAdapter adapter = (HomeForumAdapter) rvForum.getAdapter();
        adapter.getItemData(position).setMoreForumCommentDtos(forumCommentDtos);
        adapter.notifyItemChanged(position + 1);
    }

    @Override
    public void likeForumSuccess(int position) {
        HomeForumAdapter adapter = (HomeForumAdapter) rvForum.getAdapter();
        ForumDto forumDto = adapter.getItemData(position);
        if ("0".equals(forumDto.getIsNotPraise())){
            forumDto.setIsNotPraise("1");
            forumDto.setPraise(forumDto.getPraise()+1);
        } else {
            forumDto.setIsNotPraise("0");
            forumDto.setPraise(forumDto.getPraise()-1);
        }
        adapter.notifyItemChanged(position + 1);
    }

    @Override
    public void addForumCommentSuccess(String commentContent, int position) {
        HomeForumAdapter adapter = (HomeForumAdapter) rvForum.getAdapter();
        ForumDto forumDto = adapter.getItemData(position);
        ForumDto.ForumCommentDto forumCommentDto = new ForumDto.ForumCommentDto(Constant.userInfo.getNickName(),commentContent);
        if (forumDto.getCommentNumberStatus() == 0){
            forumDto.getRuleCommentsList().add(forumCommentDto);
        } else if (forumDto.getCommentNumberStatus() == 1 && (forumDto.getMoreForumCommentDtos() != null && forumDto.getMoreForumCommentDtos().size() > 0)){
            forumDto.getMoreForumCommentDtos().add(forumCommentDto);
        } else if (forumDto.getCommentNumberStatus() == 1 && (forumDto.getMoreForumCommentDtos() == null || forumDto.getMoreForumCommentDtos().size() == 0)){
            mPresenter.loadMoreComment(forumDto.getId(),mConfirmCommentPosition);
        }

        forumDto.setCommentsNumber(forumDto.getCommentsNumber() + 1);
        adapter.notifyItemChanged(position + 1);

        etComment.setText("");
    }

    @OnClick(R.id.btn_confirm)
    public void confirmComment(View view){
        String comment = etComment.getText().toString();
        if (TextUtils.isEmpty(comment)){
            showToast("提交的评论不能为空");
            return;
        }

        KeyboardUtils.hideSoftInput(getContext(),etComment);

        HomeForumAdapter adapter = (HomeForumAdapter) rvForum.getAdapter();
        ForumDto forumDto = adapter.getItemData(mConfirmCommentPosition);
        mPresenter.addForumComment(forumDto.getId(),comment,mConfirmCommentPosition);

    }
}
