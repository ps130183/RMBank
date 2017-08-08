package com.km.rmbank.utils;

import com.km.rmbank.dto.UserDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.utils.retrofit.SecretConstant;

/**
 * Created by Sunflower on 2016/1/11.
 */
public class Constant {

    public static final UserDto user = new UserDto();

    public static UserInfoDto userInfo = null;

    public static boolean isPay = false;

    public static boolean isAllowUserCard = true;

    //获取名片 接口
    public static final String QRCODE_URL = SecretConstant.API_HOST + SecretConstant.API_HOST_PATH + "/user/saoUserCard/info/send?mobilePhone=";




}
