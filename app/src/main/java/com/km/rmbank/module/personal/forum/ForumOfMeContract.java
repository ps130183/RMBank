package com.km.rmbank.module.personal.forum;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ForumDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/8/14.
 */

public interface ForumOfMeContract {
    interface View extends BaseView{
        void showForum(List<ForumDto> forumDtos,int pageNo);
        void showMoreComment(List<ForumDto.ForumCommentDto> forumCommentDtos,int position);

        void likeForumSuccess(int position);
        void addForumCommentSuccess(String commentContent,int position);
    }

    interface Presenter extends BasePresenter{
        void loadForumAllData(String type,int pageNo);
        void loadMoreComment(String forumId,int position);

        void likeForum(String forumId,int position);
        void addForumComment(String forumId,String commentContent,int position);
    }

}
