package com.km.rmbank.event;

/**
 * Created by kamangkeji on 17/8/9.
 */

public class UploadImageEvent {
    private String imagePath;

    public UploadImageEvent(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
