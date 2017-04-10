package com.km.rmbank.dto;

/**
 * Created by kamangkeji on 17/4/6.
 */

public class MemberTypeDto {

    /**
     * experience : 体验式会员介绍
     * id : 1
     * memberMoney : 1980
     * roleId : 3
     */

    private String experience;
    private String id;
    private String memberMoney;
    private String roleId;
    private String partner;

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberMoney() {
        return memberMoney;
    }

    public void setMemberMoney(String memberMoney) {
        this.memberMoney = memberMoney;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }
}
