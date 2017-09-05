package com.km.rmbank.utils;

import android.content.Context;
import android.os.Build;
import android.os.Handler;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.km.rmbank.event.UpdateEaseUserUnreadNumberEvent;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.EventBusUtils;
import com.ps.androidlib.utils.MToast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pengsong on 2016-09-13.
 */
public class EMUtils {

    public static Map<String,EaseUser> easeUserMap;

    /**
     * 设置登录状态监听
     * @param context
     * @param connect
     */
    public static void setEMConnectionListener(Context context,boolean connect){
        if (connect && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            EMClient.getInstance().addConnectionListener(new EMConnectListener(context));
        }
    }

    /**
     * 环信登录检测
     */
    private static class EMConnectListener implements EMConnectionListener {
        private Context instance;

        public EMConnectListener(Context instance) {
            this.instance = instance;
        }

        @Override
        public void onConnected() {

        }

        @Override
        public void onDisconnected(final int error) {
            new Handler(instance.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
//                    LoginUserModel loginUserModel = LoginUserModel.getDataOfCurrentLoginUser(XUtilsManager.getDB());
                    if (error == EMError.USER_REMOVED) {// 显示帐号已经被移除
                        if (!Constant.user.isEmpty()){
                            MToast.showToast(instance, "帐号已经被移除");
//                            Constant.user.clear();
                        }
                    } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {// 显示帐号在其他设备登录
                        if (!Constant.user.isEmpty()) {
//                            Utils.showToast(instance, "帐号在其他设备登录");
//                            Constant.user.clear();
                        }
                    } else {
//                        if (NetUtils.hasNetwork(instance)){//连接不到聊天服务器
//                            Utils.showToast(instance,"连接不到聊天服务器");
//                        } else{//当前网络不可用，请检查网络设置
//                            Utils.showToast(instance,"当前网络不可用，请检查网络设置");
//                        }

                    }
                }
            });
        }
    }


    /**
     * 设置消息接收 监听
     * @param context
     * @param receiver
     * @return
     */
    public static EMMessageListener setMessageReceiveListener(Context context,boolean receiver){
        EMMessageListener emMessageListener = new EMMessageListener(context);
        if (receiver  && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){//注册  接收
            EMClient.getInstance().chatManager().addMessageListener(emMessageListener);
        }

        return emMessageListener;
    }

    /**
     * 注销消息接收监听
     * @param emMessageListener
     */
    public static void removeMessageReceiverListener(EMMessageListener emMessageListener){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            EMClient.getInstance().chatManager().removeMessageListener(emMessageListener);
        }
    }

    public static class EMMessageListener implements com.hyphenate.EMMessageListener{

        private Context context;

        public EMMessageListener(Context context) {
            this.context = context;
//            userModel = LoginUserModel.getDataOfCurrentLoginUser(XUtilsManager.getDB());
        }

        @Override
        public void onMessageReceived(List<EMMessage> list) {//收到消息
            for (EMMessage emMessage : list){
                if (emMessage.getTo().equals(Constant.user.getMobilePhone())){
//                    Utils.showLog("消息来自  ------  " + emMessage.getFrom() + "   " + emMessage.getBody());
//                    Utils.showToast(context, emMessage.getTo());

                    String ticker = EaseCommonUtils.getMessageDigest(emMessage, context);
                    if(emMessage.getType() == EMMessage.Type.TXT){
                        ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                    }
                    EaseUser user = EaseUserUtils.getUserInfo(emMessage.getFrom());
                    EaseUI.getInstance().getNotifier().vibrateAndPlayTone(emMessage);
                    user.setUnread(true);
                    EventBusUtils.post(new UpdateEaseUserUnreadNumberEvent(user));
                    if(user != null){
                        Logger.d(EaseUserUtils.getUserInfo(emMessage.getFrom()).getNick() + ": " + ticker);
//                        MToast.showToast(context, EaseUserUtils.getUserInfo(emMessage.getFrom()).getNick() + ": " + ticker);
                    }else{
                        Logger.d(emMessage.getFrom() + ": " + ticker);
//                        MToast.showToast(context, emMessage.getFrom() + ": " + ticker);
                    }
                }
            }
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> list) {//收到透传消息

        }

        @Override
        public void onMessageRead(List<EMMessage> list) {

        }

        @Override
        public void onMessageDelivered(List<EMMessage> list) {

        }

        @Override
        public void onMessageChanged(EMMessage emMessage, Object o) {//消息状态变动

        }
    }

    /**
     * 加载好友列表
     * @param context
     */
//    public static void loadWineFriendsList(final Context context){
//        LoginUserModel loginUserModel = LoginUserModel.getDataOfCurrentLoginUser(XUtilsManager.getDB());
//        if (loginUserModel == null){
//            return;
//        }
//        Common.xUtilsManager.HttpPostRequest(context, Common.URL + "/auth/member/list/friend", new HashMap<String, String>(), true,
//                Common.xUtilsManager.new PostCallback() {
//                    @Override
//                    public void handleRequestResult(RequestResult<List<UserInfoVo>> result) {
//                        if (result.getStatus().equals(RetCode.SUCCESS.getStatus())){
//
//                            List<UserInfoVo> userInfoVos = result.getResult();
//                            if (easeUserMap == null){
//                                easeUserMap = new HashMap<String, EaseUser>();
//                            } else {
//                                easeUserMap.clear();
//                            }
//
//                            for (int i = 0; i < userInfoVos.size(); i++){
//                                UserInfoVo userInfoVo = userInfoVos.get(i);
//                                EaseUser user = new EaseUser(userInfoVo.getMobilePhone());
//                                user.setNick(userInfoVo.getNickName());
//                                user.setAvatar(userInfoVo.getPortraitUrl());
//                                EaseCommonUtils.setUserInitialLetter(user);
//                                easeUserMap.put("user_"+i,user);
//                            }
//
//                            //更新好友发来的未读消息
//                            EventBusUtils.post(new EventParam.UpdateEaseUserUnreadNumberEvent(easeUserMap));
//
//                        } else {
//                            Utils.showToast(context,result.getMessage());
//                        }
//                    }
//                });
//    }

}
