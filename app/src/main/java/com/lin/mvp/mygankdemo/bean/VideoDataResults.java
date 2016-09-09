package com.lin.mvp.mygankdemo.bean;

public class VideoDataResults {
    private String createdAt;
    private String publishedAt;
    private String _id;
    private String source;
    private boolean used;
    private String type;
    private String url;
    private String desc;
    private String who;

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getPublishedAt() {
        return this.publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String get_id() {
        return this._id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean getUsed() {
        return this.used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getWho() {
        return this.who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}
