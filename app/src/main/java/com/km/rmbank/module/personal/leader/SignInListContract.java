package com.km.rmbank.module.personal.leader;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.SignInDto;

import java.util.List;

/**
 * Created by PengSong on 17/12/28.
 */

public interface SignInListContract {
    interface View extends BaseView{
        void showSignInLists(List<SignInDto> signInDtos);
    }
    interface Presenter extends BasePresenter{
        void loadSignInLists(String actionId);
    }
}
