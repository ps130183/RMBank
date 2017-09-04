package com.km.rmbank.module.personal.forum;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.km.rmbank.R;
import com.km.rmbank.adapter.HomeForumAdapter;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.ForumDto;
import com.km.rmbank.event.MyForumCommentEvent;
import com.km.rmbank.event.UpdateViewPagerHeightEvent;
import com.km.rmbank.module.home.image.PictureShowActivity;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.SwipeRefreshUtils;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.EventBusUtils;
import com.ps.androidlib.utils.KeyboardWrapper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
public class ForumsOfMyselfFragment extends BaseFragment<ForumOfMePresenter> implements ForumOfMeContract.View {
    @BindView(R.id.rv_forum)
    RecyclerView rvForum;

    public static ForumsOfMyselfFragment newInstance(Bundle bundle) {
        ForumsOfMyselfFragment fragment = new ForumsOfMyselfFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    protected int getContentView() {
        return R.layout.fragment_my_forumsk;
    }

    @Override
    public ForumOfMePresenter getmPresenter() {
        return new ForumOfMePresenter(this);
    }

    @Override
    protected void createView() {
        initRecycler();
    }


    /**
     * 初始化列表
     */
    private void initRecycler(){
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

//        rvForum.setLayoutManager(new FullyLinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
//        RVUtils.setLinearLayoutManage(rvForum, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvForum,RVUtils.DIVIDER_COLOR_DEFAULT,2);
        final HomeForumAdapter adapter = new HomeForumAdapter(getContext());
        rvForum.setAdapter(adapter);
        ((DefaultItemAnimator) rvForum.getItemAnimator()).setSupportsChangeAnimations(false);
        mPresenter.loadForumAllData("0",1);

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
                MyForumCommentEvent event = new MyForumCommentEvent(true);
                event.setPosition(position);
                event.setFrom(MyForumCommentEvent.From.MYFORUM);
                EventBusUtils.post(event);
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
    public void showForum(List<ForumDto> forumDtos, int pageNo) {
        HomeForumAdapter adapter = (HomeForumAdapter) rvForum.getAdapter();
        adapter.addData(forumDtos,pageNo);
    }

    @Override
    public void showMoreComment(List<ForumDto.ForumCommentDto> forumCommentDtos, int position) {
        HomeForumAdapter adapter = (HomeForumAdapter) rvForum.getAdapter();
        adapter.getItemData(position).setMoreForumCommentDtos(forumCommentDtos);
        adapter.notifyItemChanged(position);
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
        adapter.notifyItemChanged(position);
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
            mPresenter.loadMoreComment(forumDto.getId(),position);
        }

        forumDto.setCommentsNumber(forumDto.getCommentsNumber() + 1);
        adapter.notifyItemChanged(position);

//        etComment.setText("");
    }

    public LinearLayoutManager getLayoutManager(){
        return (LinearLayoutManager) rvForum.getLayoutManager();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showAddComment(MyForumCommentEvent event){
        if (event.isRequest()){
            Logger.d("这是添加评论的请求");
            return;
        }
        if (event.getTo() == null || event.getTo() == MyForumCommentEvent.From.LIKEORCOMMENT){
            Logger.d("没有目标页面");
            return;
        }

        HomeForumAdapter adapter = (HomeForumAdapter) rvForum.getAdapter();
        ForumDto forumDto = adapter.getItemData(event.getPosition());
        mPresenter.addForumComment(forumDto.getId(),event.getNewComment(),event.getPosition());

    }
}
