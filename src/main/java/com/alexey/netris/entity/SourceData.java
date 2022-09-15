package com.alexey.netris.entity;


import com.alexey.netris.util.UrlType;

public class SourceData extends DataMarker{
    private UrlType urlType;
    private String videoUrl;

    public UrlType getUrlType() {
        return urlType;
    }

    public void setUrlType(UrlType urlType) {
        this.urlType = urlType;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }


}
