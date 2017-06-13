package com.km.rmbank.event;

import com.hyphenate.easeui.domain.EaseUser;

import java.util.Map;

/**
 * Created by kamangkeji on 17/6/11.
 */

public class UpdateEaseUserUnreadNumberEvent {
    private EaseUser mEaseUser;

    public UpdateEaseUserUnreadNumberEvent(EaseUser mEaseUser) {
        this.mEaseUser = mEaseUser;
    }

    public EaseUser getmEaseUser() {
        return mEaseUser;
    }
}
