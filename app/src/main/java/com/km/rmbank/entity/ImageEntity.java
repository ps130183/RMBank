package com.km.rmbank.entity;

/**
 * Created by kamangkeji on 17/3/29.
 */

public class ImageEntity {
    private String imagePath;

    public ImageEntity(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
