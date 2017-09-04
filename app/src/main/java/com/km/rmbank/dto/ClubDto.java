package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.km.rmbank.basic.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamangkeji on 17/7/1.
 */

public class ClubDto extends BaseEntity implements Parcelable {

    /**
     * clubLogo : http://47.93.184.121/img/guest/201707/chanwu.jpg
     * clubName : 禅舞俱乐部
     */

    private String id;
    private String clubLogo;
    private String clubName;
    private String content;
    private String backgroundImg;
    private int keepStatus;
    private List<ClubDetailBean> clubDetailList;

    @Override
    public String toString() {
        return "ClubDto{" +
                "id='" + id + '\'' +
                ", clubLogo='" + clubLogo + '\'' +
                ", clubName='" + clubName + '\'' +
                ", content='" + content + '\'' +
                ", backgroundImg='" + backgroundImg + '\'' +
                ", keepStatus=" + keepStatus +
                ", clubDetailList=" + clubDetailList +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBackgroundImg() {
        return backgroundImg;
    }

    public void setBackgroundImg(String backgroundImg) {
        this.backgroundImg = backgroundImg;
    }

    public List<ClubDetailBean> getClubDetailList() {
        return clubDetailList;
    }

    public void setClubDetailList(List<ClubDetailBean> clubDetailList) {
        this.clubDetailList = clubDetailList;
    }

    public boolean getKeepStatus() {
        return keepStatus == 0 ? false : true;
    }

    public void setKeepStatus(int keepStatus) {
        this.keepStatus = keepStatus;
    }

    @Override
    public boolean isEmpty() {
        if (TextUtils.isEmpty(clubLogo) || TextUtils.isEmpty(clubName) || TextUtils.isEmpty(content)){
            return true;
        }
//        if (clubDetailList != null){
//            boolean isEmpty = false;
//            for (ClubDetailBean bean : clubDetailList){
//                isEmpty = bean.isEmpty();
//                if (isEmpty){
//                    break;
//                }
//            }
//            return isEmpty;
//        }
        return false;
    }

    public static class ClubDetailBean extends BaseEntity implements Parcelable {
        private String clubImage;
        private List<String> clubImageList;
        private String clubContent;

        public String getClubImage() {
            return clubImage;
        }

        public void setClubImage(String clubImage) {
            this.clubImage = clubImage;
        }

        public String getClubContent() {
            return clubContent;
        }

        public void setClubContent(String clubContent) {
            this.clubContent = clubContent;
        }

        public List<String> getClubImageList() {
            return clubImageList;
        }

        public void setClubImageList(List<String> clubImageList) {
            clubImageList = clubImageList;
        }

        @Override
        public String toString() {
            return "ClubDetailBean{" +
                    "clubImage='" + clubImage + '\'' +
                    ", ClubImageList=" + clubImageList +
                    ", clubContent='" + clubContent + '\'' +
                    '}';
        }

        @Override
        public boolean isEmpty() {
            if (TextUtils.isEmpty(clubImage) || TextUtils.isEmpty(clubContent)){
                return true;
            }
            return false;
        }

        public ClubDetailBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.clubImage);
            dest.writeStringList(this.clubImageList);
            dest.writeString(this.clubContent);
        }

        protected ClubDetailBean(Parcel in) {
            this.clubImage = in.readString();
            this.clubImageList = in.createStringArrayList();
            this.clubContent = in.readString();
        }

        public static final Creator<ClubDetailBean> CREATOR = new Creator<ClubDetailBean>() {
            @Override
            public ClubDetailBean createFromParcel(Parcel source) {
                return new ClubDetailBean(source);
            }

            @Override
            public ClubDetailBean[] newArray(int size) {
                return new ClubDetailBean[size];
            }
        };
    }

    public ClubDto() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.clubLogo);
        dest.writeString(this.clubName);
        dest.writeString(this.content);
        dest.writeString(this.backgroundImg);
        dest.writeInt(this.keepStatus);
        dest.writeTypedList(this.clubDetailList);
    }

    protected ClubDto(Parcel in) {
        this.id = in.readString();
        this.clubLogo = in.readString();
        this.clubName = in.readString();
        this.content = in.readString();
        this.backgroundImg = in.readString();
        this.keepStatus = in.readInt();
        this.clubDetailList = in.createTypedArrayList(ClubDetailBean.CREATOR);
    }

    public static final Creator<ClubDto> CREATOR = new Creator<ClubDto>() {
        @Override
        public ClubDto createFromParcel(Parcel source) {
            return new ClubDto(source);
        }

        @Override
        public ClubDto[] newArray(int size) {
            return new ClubDto[size];
        }
    };
}
