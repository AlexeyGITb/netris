package com.alexey.netris.entity;

public class TokenData extends DataMarker{
    private String value;
    private String ttl;


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

}
