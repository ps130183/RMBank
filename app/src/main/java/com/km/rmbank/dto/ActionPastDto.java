package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.km.rmbank.basic.BaseEntity;

import java.util.List;

/**
 * Created by kamangkeji on 17/7/18.
 */

public class ActionPastDto extends BaseEntity implements Parcelable {

    private String id;
    private String clubId;
    private String clubName;
    private String clubLogo;
    private long createDate;
    private String avatarUrl;
    private String title;
    private List<DynamicBean> dynamicList;
    private List<DynamicBean> detailList;
    private int viewCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ActionPastDto{" +
                "id='" + id + '\'' +
                ", clubId='" + clubId + '\'' +
                ", clubName='" + clubName + '\'' +
                ", clubLogo='" + clubLogo + '\'' +
                ", createDate=" + createDate +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", title='" + title + '\'' +
                ", dynamicList=" + dynamicList +
                ", detailList=" + detailList +
                ", viewCount=" + viewCount +
                '}';
    }

    public String getClubLogo() {
        return clubLogo;
    }

    public void setClubLogo(String clubLogo) {
        this.clubLogo = clubLogo;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public List<DynamicBean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DynamicBean> detailList) {
        this.detailList = detailList;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DynamicBean> getDynamicList() {
        return dynamicList;
    }

    public void setDynamicList(List<DynamicBean> dynamicList) {
        this.dynamicList = dynamicList;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public boolean isEmpty() {
        if (TextUtils.isEmpty(clubId)
                || TextUtils.isEmpty(title)){
            return true;
        }
        if (dynamicList != null){
            boolean isEmpty = false;
            for (DynamicBean bean : dynamicList){
                if (bean.isEmpty()){
                    isEmpty = true;
                    break;
                }
            }
            return isEmpty;
        }
        return false;
    }



    public static class DynamicBean extends BaseEntity implements Parcelable {
        private String dynamicImage;
        private String dynamicImageContent;

        @Override
        public String toString() {
            return "DynamicBean{" +
                    "dynamicImage='" + dynamicImage + '\'' +
                    ", dynamicImageContent='" + dynamicImageContent + '\'' +
                    '}';
        }

        public String getDynamicImage() {
            return dynamicImage;
        }

        public void setDynamicImage(String dynamicImage) {
            this.dynamicImage = dynamicImage;
        }

        public String getDynamicImageContent() {
            return dynamicImageContent;
        }

        public void setDynamicImageContent(String dynamicImageContent) {
            this.dynamicImageContent = dynamicImageContent;
        }

        @Override
        public boolean isEmpty() {
            if (TextUtils.isEmpty(dynamicImage) || TextUtils.isEmpty(dynamicImageContent)){
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
            dest.writeString(this.dynamicImage);
            dest.writeString(this.dynamicImageContent);
        }

        public DynamicBean() {
        }

        protected DynamicBean(Parcel in) {
            this.dynamicImage = in.readString();
            this.dynamicImageContent = in.readString();
        }

        public static final Creator<DynamicBean> CREATOR = new Creator<DynamicBean>() {
            @Override
            public DynamicBean createFromParcel(Parcel source) {
                return new DynamicBean(source);
            }

            @Override
            public DynamicBean[] newArray(int size) {
                return new DynamicBean[size];
            }
        };
    }

    public ActionPastDto() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.clubId);
        dest.writeString(this.clubName);
        dest.writeString(this.clubLogo);
        dest.writeLong(this.createDate);
        dest.writeString(this.avatarUrl);
        dest.writeString(this.title);
        dest.writeTypedList(this.dynamicList);
        dest.writeTypedList(this.detailList);
        dest.writeInt(this.viewCount);
    }

    protected ActionPastDto(Parcel in) {
        this.id = in.readString();
        this.clubId = in.readString();
        this.clubName = in.readString();
        this.clubLogo = in.readString();
        this.createDate = in.readLong();
        this.avatarUrl = in.readString();
        this.title = in.readString();
        this.dynamicList = in.createTypedArrayList(DynamicBean.CREATOR);
        this.detailList = in.createTypedArrayList(DynamicBean.CREATOR);
        this.viewCount = in.readInt();
    }

    public static final Creator<ActionPastDto> CREATOR = new Creator<ActionPastDto>() {
        @Override
        public ActionPastDto createFromParcel(Parcel source) {
            return new ActionPastDto(source);
        }

        @Override
        public ActionPastDto[] newArray(int size) {
            return new ActionPastDto[size];
        }
    };
}