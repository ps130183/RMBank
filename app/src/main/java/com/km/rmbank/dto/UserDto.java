package com.km.rmbank.dto;

import android.text.TextUtils;

import com.km.rmbank.basic.BaseEntity;
import com.km.rmbank.utils.Constant;
import com.ps.androidlib.utils.SPUtils;

/**
 * Created by kamangkeji on 17/3/17.
 */

public class UserDto extends BaseEntity {

    /**
     * mobilePhone : 15631707132
     * hxPwd : eccbc87e4b5ce2fe28308fd9f2a7baf3
     * token : b7811eb9f25948f59eb659514aca8072
     * type : 1
     */

    private String mobilePhone;
    private String hxPwd;
    private String token;

    private String roleId;

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getHxPwd() {
        return hxPwd;
    }

    public void setHxPwd(String hxPwd) {
        this.hxPwd = hxPwd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * 将登录信息保存到 sharePreference
     */
    public void saveToSp(){
        SPUtils spUtils = SPUtils.getInstance();
        spUtils.put("mobilePhone",TextUtils.isEmpty(mobilePhone)?"":mobilePhone);
//        spUtils.put("hxPwd",TextUtils.isEmpty(hxPwd)?"":hxPwd);
        spUtils.put("token",TextUtils.isEmpty(token)?"":token);
        spUtils.put("roleId",TextUtils.isEmpty(roleId)?"":roleId);
    }

    /**
     * 清空sharePreference 中的数据
     */
    public void clear(){
        SPUtils spUtils = SPUtils.getInstance();
        spUtils.remove("mobilePhone");
//        spUtils.remove("hxPwd");
        spUtils.remove("token");
        spUtils.remove("roleId");
        getDataFromSp();
    }

    /**
     * 从sharePreference 获取用户信息
     */
    public void getDataFromSp(){
        SPUtils spUtils = SPUtils.getInstance();
        mobilePhone = spUtils.getString("mobilePhone","");
//        hxPwd = spUtils.getString("hxPwd","");
        token = spUtils.getString("token","");
        roleId = spUtils.getString("roleId","");
    }

    /**
     * 是否为空
     * @return
     */
    @Override
    public boolean isEmpty(){
        if (TextUtils.isEmpty(token)){
            return true;
        }
        return false;
    }


}
