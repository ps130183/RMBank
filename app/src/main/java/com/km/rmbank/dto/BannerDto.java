package com.km.rmbank.dto;

/**
 * Created by kamangkeji on 17/4/21.
 */

public class BannerDto {


    /**
     * avatarUrl : http://192.168.31.136:8080/img/guest/201704/a37a53697e2e4cfc8377876466f376ef.jpeg
     * id : 16
     * type : 1
     */

    private String avatarUrl;
    private String id;
    private String type;
    private String title;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
