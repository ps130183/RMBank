package com.km.rmbank.module.personal.forum;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ForumInfoDto;

/**
 * Created by kamangkeji on 17/8/16.
 */

public interface MyForumInfosContract {
    interface View extends BaseView{
        void showMyForumInfo(ForumInfoDto forumInfoDto);
    }
    interface Presenter extends BasePresenter{
        void getMyForumInfos();
    }
}
