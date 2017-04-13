package com.km.rmbank.module.home.message;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.MessageDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/4/13.
 */

public interface MessageContract {
    interface View extends BaseView{
        void initRecyclerview();
        void showMessage(List<MessageDto> messageDtos,int pageNo);
    }
    interface Presenter extends BasePresenter{
        void getMessage(int pageNo);
    }
}
