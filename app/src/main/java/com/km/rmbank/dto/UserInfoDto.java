package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kamangkeji on 17/3/31.
 */

public class UserInfoDto implements Parcelable {

    /**
     * birthday : 1988-01-01
     * portraitUrl : http://192.168.31.216:8080/image/default/default_icon_head.png
     * nickName : 15631707132
     */

    private String birthday;
    private String portraitUrl;
    private String nickName;

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

    @Override
    public String toString() {
        return "UserInfoDto{" +
                "birthday='" + birthday + '\'' +
                ", portraitUrl='" + portraitUrl + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
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
    }

    public UserInfoDto() {
    }

    protected UserInfoDto(Parcel in) {
        this.birthday = in.readString();
        this.portraitUrl = in.readString();
        this.nickName = in.readString();
    }

    public static final Parcelable.Creator<UserInfoDto> CREATOR = new Parcelable.Creator<UserInfoDto>() {
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
