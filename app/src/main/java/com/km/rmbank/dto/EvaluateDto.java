package com.km.rmbank.dto;

/**
 * Created by kamangkeji on 17/4/13.
 */

public class EvaluateDto {
    private String userName;
    private String date;
    private String content;

    public EvaluateDto() {
    }

    public EvaluateDto(String userName, String date, String content) {
        this.userName = userName;
        this.date = date;
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
