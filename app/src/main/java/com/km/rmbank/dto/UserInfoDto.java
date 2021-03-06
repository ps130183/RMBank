package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.km.rmbank.basic.BaseEntity;

/**
 * Created by kamangkeji on 17/3/31.
 */

public class UserInfoDto extends BaseEntity implements Parcelable {

    /**
     * birthday : 1988-01-01
     * portraitUrl : http://192.168.31.216:8080/image/default/default_icon_head.png
     * nickName : 15631707132
     */

    private String birthday;
    private String portraitUrl;
    private String nickName;
    /**
     * total : 0
     * registerDate : 86
     * roleName : 体验式会员
     * updateDate : 2017-05-15
     * roleId : 3
     */

    private String total;
    private int registerDate;
    private String roleName;
    private String updateDate;
    private String roleId;

    private String allowStutas;

    private int clubStatus;//1:已编辑 0：未编辑
    private int isNotEditCard;// 是否编辑名片：0为未编辑，1为已编辑

    private String activeValueAll;

    private int isTeach;//0  1

    public int getIsTeach() {
        return isTeach;
    }

    public void setIsTeach(int isTeach) {
        this.isTeach = isTeach;
    }

    public int getIsNotEditCard() {
        return isNotEditCard;
    }

    public void setIsNotEditCard(int isNotEditCard) {
        this.isNotEditCard = isNotEditCard;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getActiveValueAll() {
        return activeValueAll;
    }

    public void setActiveValueAll(String activeValueAll) {
        this.activeValueAll = activeValueAll;
    }

    @Override
    public String toString() {
        return "UserInfoDto{" +
                "birthday='" + birthday + '\'' +
                ", portraitUrl='" + portraitUrl + '\'' +
                ", nickName='" + nickName + '\'' +
                ", total='" + total + '\'' +
                ", registerDate=" + registerDate +
                ", roleName='" + roleName + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", roleId='" + roleId + '\'' +
                ", allowStutas='" + allowStutas + '\'' +
                ", clubStatus=" + clubStatus +
                ", isNotEditCard=" + isNotEditCard +
                ", activeValueAll='" + activeValueAll + '\'' +
                ", isTeach=" + isTeach +
                '}';
    }

    public int getClubStatus() {
        return clubStatus;
    }

    public void setClubStatus(int clubStatus) {
        this.clubStatus = clubStatus;
    }

    public UserInfoDto() {
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(int registerDate) {
        this.registerDate = registerDate;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getAllowStutas() {
        return allowStutas;
    }

    public void setAllowStutas(String allowStutas) {
        this.allowStutas = allowStutas;
    }

    @Override
    public boolean isEmpty() {
        if (TextUtils.isEmpty(nickName) || TextUtils.isEmpty(portraitUrl)){
            return true;
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.birthday);
        dest.writeString(this.portraitUrl);
        dest.writeString(this.nickName);
        dest.writeString(this.total);
        dest.writeInt(this.registerDate);
        dest.writeString(this.roleName);
        dest.writeString(this.updateDate);
        dest.writeString(this.roleId);
        dest.writeString(this.allowStutas);
        dest.writeInt(this.clubStatus);
        dest.writeInt(this.isNotEditCard);
        dest.writeString(this.activeValueAll);
        dest.writeInt(this.isTeach);
    }

    protected UserInfoDto(Parcel in) {
        this.birthday = in.readString();
        this.portraitUrl = in.readString();
        this.nickName = in.readString();
        this.total = in.readString();
        this.registerDate = in.readInt();
        this.roleName = in.readString();
        this.updateDate = in.readString();
        this.roleId = in.readString();
        this.allowStutas = in.readString();
        this.clubStatus = in.readInt();
        this.isNotEditCard = in.readInt();
        this.activeValueAll = in.readString();
        this.isTeach = in.readInt();
    }

    public static final Creator<UserInfoDto> CREATOR = new Creator<UserInfoDto>() {
        @Override
        public UserInfoDto createFromParcel(Parcel source) {
            return new UserInfoDto(source);
        }

        @Override
        public UserInfoDto[] newArray(int size) {
            return new UserInfoDto[size];
        }
    };
}
