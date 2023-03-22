package com.example.animefinalappproject;
// Illya Chebotaryov


import android.media.Image;
import android.provider.ContactsContract;

import java.io.Serializable;

public class AnimeModel implements Serializable {
    private String title;
    private String synopsis;

    private String imageUrl;

    public AnimeModel(String title, String synopsis, String imageUrl) {
        this.title = title;
        this.synopsis = synopsis;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void  setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
}
