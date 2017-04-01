package com.km.rmbank.dto;

import android.text.TextUtils;

import com.km.rmbank.basic.BaseEntity;

import java.util.List;

/**
 * Created by kamangkeji on 17/3/31.
 */

public class UserCardDto extends BaseEntity {
    private String name;//真实姓名

    private String cardPhone;//名片手机号

    private String position;//所在公司职位

    private String company;//所在公司

    private String companyProfile;//企业介绍

    private List<IndustryDto> provideResourcesArr;//提供资源

    private List<IndustryDto> demandResourcesArr;//需求资源

    private String provideResourcesId;//提供资源

    private String demandResourcesId;//需求资源

    private String location;//所在地

    private String detailedAddress;//详细地址

    private String emailAddress;//邮箱

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardPhone() {
        return cardPhone;
    }

    public void setCardPhone(String cardPhone) {
        this.cardPhone = cardPhone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(String companyProfile) {
        this.companyProfile = companyProfile;
    }

    public String getProvideResourcesId() {
        return provideResourcesId;
    }

    public void setProvideResourcesId(String provideResourcesId) {
        this.provideResourcesId = provideResourcesId;
    }

    public String getDemandResourcesId() {
        return demandResourcesId;
    }

    public void setDemandResourcesId(String demandResourcesId) {
        this.demandResourcesId = demandResourcesId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public List<IndustryDto> getProvideResourcesArr() {
        return provideResourcesArr;
    }

    public void setProvideResourcesArr(List<IndustryDto> provideResourcesArr) {
        this.provideResourcesArr = provideResourcesArr;
    }

    public List<IndustryDto> getDemandResourcesArr() {
        return demandResourcesArr;
    }

    public void setDemandResourcesArr(List<IndustryDto> demandResourcesArr) {
        this.demandResourcesArr = demandResourcesArr;
    }

    @Override
    public String toString() {
        return "UserCardDto{" +
                "name='" + name + '\'' +
                ", cardPhone='" + cardPhone + '\'' +
                ", position='" + position + '\'' +
                ", company='" + company + '\'' +
                ", companyProfile='" + companyProfile + '\'' +
                ", provideResourcesArr=" + provideResourcesArr +
                ", demandResourcesArr=" + demandResourcesArr +
                ", provideResourcesId='" + provideResourcesId + '\'' +
                ", demandResourcesId='" + demandResourcesId + '\'' +
                ", location='" + location + '\'' +
                ", detailedAddress='" + detailedAddress + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }

    /**
     * 是否为空
     * @return
     */
    @Override
    public boolean isEmpty(){
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(cardPhone) || TextUtils.isEmpty(company) || TextUtils.isEmpty(position)
                || TextUtils.isEmpty(companyProfile) || TextUtils.isEmpty(provideResourcesId) || TextUtils.isEmpty(demandResourcesId)
                || TextUtils.isEmpty(location) || TextUtils.isEmpty(detailedAddress) || TextUtils.isEmpty(emailAddress)){
            return true;
        }
        return false;
    }
}
