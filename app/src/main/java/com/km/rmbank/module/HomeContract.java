package com.km.rmbank.module;

import android.view.View;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.dto.UserCardDto;

/**
 * Created by kamangkeji on 17/5/15.
 */

public interface HomeContract {
    interface View extends BaseView{
        void showShareContent(ShareDto shareDto);
        void getUserInfoByQRCodeSuccess(UserCardDto userCardDto, String friendPhone);
    }
    interface Presenter extends BasePresenter{
        void getShareContent();
        void getUserInfoByQRCode(String url);
    }
}
