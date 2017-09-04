package com.km.rmbank.module.personal.forum;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ForumDto;

/**
 * Created by kamangkeji on 17/8/14.
 */

public interface ReleaseForumContract {
   interface View extends BaseView{
       void showImage(String imageUrl,int position);
       void releaseForumSuccess();
   }

   interface Presenter extends BasePresenter{
       void uploadImage(String imagePath,int position);
       void releaseForum(ForumDto forumDto);
   }
}
