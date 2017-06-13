package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.km.rmbank.basic.BaseEntity;

import java.util.List;

/**
 * Created by kamangkeji on 17/3/31.
 */

public class UserCardDto extends BaseEntity implements Parcelable {
    private String name;//真实姓名

    private String cardPhone;//名片手机号

    private String position;//所在公司职位

    private String company;//所在公司

    private String companyProfile;//企业介绍

    private List<IndustryDto> provideResourcesMap;//提供资源

    private List<IndustryDto> demandResourcesMap;//需求资源

    @Override
    public String toString() {
        return "UserCardDto{" +
                "name='" + name + '\'' +
                ", cardPhone='" + cardPhone + '\'' +
                ", position='" + position + '\'' +
                ", company='" + company + '\'' +
                ", companyProfile='" + companyProfile + '\'' +
                ", provideResourcesMap=" + provideResourcesMap +
                ", demandResourcesMap=" + demandResourcesMap +
                ", provideResourcesId='" + provideResourcesId + '\'' +
                ", demandResourcesId='" + demandResourcesId + '\'' +
                ", location='" + location + '\'' +
                ", detailedAddress='" + detailedAddress + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", status=" + status +
                ", friendMobilePhone='" + friendMobilePhone + '\'' +
                ", portraitUrl='" + portraitUrl + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }

    private String provideResourcesId;//提供资源

    private String demandResourcesId;//需求资源

    private String location;//所在地

    private String detailedAddress;//详细地址

    private String emailAddress;//邮箱

    private int status;

    private String friendMobilePhone;

    private String portraitUrl;
    private String mobilePhone;
    private String nickName;

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

    public List<IndustryDto> getProvideResourcesMap() {
        return provideResourcesMap;
    }

    public void setProvideResourcesMap(List<IndustryDto> provideResourcesMap) {
        this.provideResourcesMap = provideResourcesMap;
    }

    public List<IndustryDto> getDemandResourcesMap() {
        return demandResourcesMap;
    }

    public void setDemandResourcesMap(List<IndustryDto> demandResourcesMap) {
        this.demandResourcesMap = demandResourcesMap;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFriendMobilePhone() {
        return friendMobilePhone;
    }

    public void setFriendMobilePhone(String friendMobilePhone) {
        this.friendMobilePhone = friendMobilePhone;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 是否为空
     * @return
     */
    @Override
    public boolean isEmpty(){
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(cardPhone) || TextUtils.isEmpty(company) || TextUtils.isEmpty(position)
                || TextUtils.isEmpty(companyProfile)
                || (TextUtils.isEmpty(provideResourcesId) && provideResourcesMap == null)
                || (TextUtils.isEmpty(demandResourcesId) && demandResourcesMap == null)
                || TextUtils.isEmpty(location) || TextUtils.isEmpty(detailedAddress) || TextUtils.isEmpty(emailAddress)){
            return true;
        }
        return false;
    }

    public UserCardDto() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.cardPhone);
        dest.writeString(this.position);
        dest.writeString(this.company);
        dest.writeString(this.companyProfile);
        dest.writeTypedList(this.provideResourcesMap);
        dest.writeTypedList(this.demandResourcesMap);
        dest.writeString(this.provideResourcesId);
        dest.writeString(this.demandResourcesId);
        dest.writeString(this.location);
        dest.writeString(this.detailedAddress);
        dest.writeString(this.emailAddress);
        dest.writeInt(this.status);
        dest.writeString(this.friendMobilePhone);
        dest.writeString(this.portraitUrl);
        dest.writeString(this.mobilePhone);
        dest.writeString(this.nickName);
    }

    protected UserCardDto(Parcel in) {
        this.name = in.readString();
        this.cardPhone = in.readString();
        this.position = in.readString();
        this.company = in.readString();
        this.companyProfile = in.readString();
        this.provideResourcesMap = in.createTypedArrayList(IndustryDto.CREATOR);
        this.demandResourcesMap = in.createTypedArrayList(IndustryDto.CREATOR);
        this.provideResourcesId = in.readString();
        this.demandResourcesId = in.readString();
        this.location = in.readString();
        this.detailedAddress = in.readString();
        this.emailAddress = in.readString();
        this.status = in.readInt();
        this.friendMobilePhone = in.readString();
        this.portraitUrl = in.readString();
        this.mobilePhone = in.readString();
        this.nickName = in.readString();
    }

    public static final Creator<UserCardDto> CREATOR = new Creator<UserCardDto>() {
        @Override
        public UserCardDto createFromParcel(Parcel source) {
            return new UserCardDto(source);
        }

        @Override
        public UserCardDto[] newArray(int size) {
            return new UserCardDto[size];
        }
    };
}
