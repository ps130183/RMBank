package com.km.rmbank.dto;

/**
 * Created by kamangkeji on 17/7/1.
 */

public class ClubDto {

    /**
     * clubLogo : http://47.93.184.121/img/guest/201707/chanwu.jpg
     * clubName : 禅舞俱乐部
     */

    private String clubLogo;
    private String clubName;

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

    @Override
    public String toString() {
        return "ClubDto{" +
                "clubLogo='" + clubLogo + '\'' +
                ", clubName='" + clubName + '\'' +
                '}';
    }
}
