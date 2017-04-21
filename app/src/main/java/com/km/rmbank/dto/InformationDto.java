package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kamangkeji on 17/4/20.
 */

public class InformationDto implements Parcelable {
    private String avatarUrl;
    private String id;
    private String title;
    private String viewCount;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public InformationDto() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.avatarUrl);
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.viewCount);
    }

    protected InformationDto(Parcel in) {
        this.avatarUrl = in.readString();
        this.id = in.readString();
        this.title = in.readString();
        this.viewCount = in.readString();
    }

    public static final Creator<InformationDto> CREATOR = new Creator<InformationDto>() {
        @Override
        public InformationDto createFromParcel(Parcel source) {
            return new InformationDto(source);
        }

        @Override
        public InformationDto[] newArray(int size) {
            return new InformationDto[size];
        }
    };
}
