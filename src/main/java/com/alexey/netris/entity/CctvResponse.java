package com.alexey.netris.entity;


import com.alexey.netris.util.UrlType;

public class CctvResponse {
    private Long id;
    private String value;
    private String ttl;
    private UrlType urlType;
    private String videoUrl;


    public CctvResponse(Long id, String value, String ttl, UrlType urlType, String videoUrl) {
        this.id = id;
        this.value = value;
        this.ttl = ttl;
        this.urlType = urlType;
        this.videoUrl = videoUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

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
