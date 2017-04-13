package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kamangkeji on 17/4/12.
 */

public class ActionDto implements Parcelable {

    /**
     * activityPictureUrl : http://192.168.31.216:8088/img2542342
     * activityType : 1
     * content : 不错哦
     * createDate : 1490779228000
     * durationDate : 1490779224000
     * id : 14
     * startDate : 1489483221000
     * title : 活动
     * updateDate : 1490779230000
     */

    private String activityPictureUrl;
    private String activityType;
    private String content;
    private long createDate;
    private long durationDate;
    private String id;
    private long startDate;
    private String title;
    private long updateDate;

    public String getActivityPictureUrl() {
        return activityPictureUrl;
    }

    public void setActivityPictureUrl(String activityPictureUrl) {
        this.activityPictureUrl = activityPictureUrl;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getDurationDate() {
        return durationDate;
    }

    public void setDurationDate(long durationDate) {
        this.durationDate = durationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "ActionDto{" +
                "activityPictureUrl='" + activityPictureUrl + '\'' +
                ", activityType='" + activityType + '\'' +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                ", durationDate=" + durationDate +
                ", id='" + id + '\'' +
                ", startDate=" + startDate +
                ", title='" + title + '\'' +
                ", updateDate=" + updateDate +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.activityPictureUrl);
        dest.writeString(this.activityType);
        dest.writeString(this.content);
        dest.writeLong(this.createDate);
        dest.writeLong(this.durationDate);
        dest.writeString(this.id);
        dest.writeLong(this.startDate);
        dest.writeString(this.title);
        dest.writeLong(this.updateDate);
    }

    public ActionDto() {
    }

    protected ActionDto(Parcel in) {
        this.activityPictureUrl = in.readString();
        this.activityType = in.readString();
        this.content = in.readString();
        this.createDate = in.readLong();
        this.durationDate = in.readLong();
        this.id = in.readString();
        this.startDate = in.readLong();
        this.title = in.readString();
        this.updateDate = in.readLong();
    }

    public static final Parcelable.Creator<ActionDto> CREATOR = new Parcelable.Creator<ActionDto>() {
        @Override
        public ActionDto createFromParcel(Parcel source) {
            return new ActionDto(source);
        }

        @Override
        public ActionDto[] newArray(int size) {
            return new ActionDto[size];
        }
    };
}
