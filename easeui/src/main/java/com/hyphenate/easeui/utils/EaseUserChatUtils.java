package com.hyphenate.easeui.utils;

import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamangkeji on 17/6/9.
 */

public class EaseUserChatUtils {

    private static List<EaseUser> mUsers = null;
    private static EaseUser curLoginUser = null;

    static {
        mUsers = new ArrayList<>();
    }

    public static void init(){
        EaseUI.getInstance().setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                EaseUser user = null;
                if (username.equals(curLoginUser.getUsername())){//登录的人
                    user = curLoginUser;
//                    user.setNickname();
                } else {
                    for (EaseUser easeUser : mUsers){
                        if (username.equals(easeUser.getUsername())){
                            user = easeUser;
                            break;
                        }
                    }
                }
                return user;
            }
        });
    }

    public static void addUser(EaseUser easeUser){
        if (!checkUser(easeUser)){
            mUsers.add(easeUser);
        }
    }

    public static void clear(){
        if (mUsers != null){
            mUsers.clear();
        }
    }

    public static List<EaseUser> getUsers() {
        return mUsers;
    }

    /**
     * 获取所在位置
     * @param user
     * @return
     */
    public static int getPosition(EaseUser user){
        int position = -1;
        for (int i = 0; i < mUsers.size(); i++){
            if (user.getUsername().equals(mUsers.get(i).getUsername())){
                position = i;
                break;
            }
        }

        return position;
    }

    /**
     * 是否有未读消息
     * @return
     */
    public static boolean isUnreadMsg(){
        boolean unread = false;

        for (EaseUser user : mUsers){
            if (user.isUnread()){
                unread = true;
                break;
            }
        }

        return unread;
    }

    public static void addCurLoginUser(EaseUser user){
        curLoginUser = user;
    }

    private static boolean checkUser(EaseUser easeUser){
        for (EaseUser user : mUsers){
            if (easeUser.getUsername().equals(user.getUsername())){
                return true;
            }
        }

        return false;
    }

}
