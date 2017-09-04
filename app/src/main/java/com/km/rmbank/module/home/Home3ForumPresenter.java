package com.km.rmbank.module.home;

import com.km.rmbank.adapter.HomeForumAdapter;
import com.km.rmbank.dto.ForumDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/8/11.
 */

public class Home3ForumPresenter extends PresenterWrapper<Home3ForumContract.View> implements Home3ForumContract.Presenter {

    public Home3ForumPresenter(Home3ForumContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void loadForumAllData(final int pageNo) {
        mView.showLoading();
        mApiwrapper.getForumList(pageNo)
                .subscribe(newSubscriber(new Consumer<List<ForumDto>>() {
                    @Override
                    public void accept(@NonNull List<ForumDto> forumDtos) throws Exception {
                        mView.showForum(forumDtos,pageNo);
                    }
                }));

    }

    @Override
    public void loadMoreComment(String forumId, final int position) {
        mView.showLoading();
        mApiwrapper.getMoreCommentList(forumId)
                .subscribe(newSubscriber(new Consumer<List<ForumDto.ForumCommentDto>>() {
                    @Override
                    public void accept(@NonNull List<ForumDto.ForumCommentDto> forumCommentDtos) throws Exception {
                        mView.showMoreComment(forumCommentDtos,position);
                    }
                }));
    }

    @Override
    public void likeForum(String forumId, final int position) {
        mView.showLoading();
        mApiwrapper.likeForum(forumId)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.likeForumSuccess(position);
                    }
                }));
    }

    @Override
    public void addForumComment(String forumId, final String commentContent, final int position) {
        mView.showLoading();
        mApiwrapper.addForumComment(forumId,commentContent)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.addForumCommentSuccess(commentContent,position);
                    }
                }));
    }
}
