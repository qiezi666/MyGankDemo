package com.lin.mvp.mygankdemo.bean;

import java.util.List;

public class GankDataResults {
    private List<Gank> App;
    private List<Gank> 休息视频;
    private List<Gank> 福利;
    private List<Gank> 前端;
    private List<Gank> iOS;
    private List<Gank> Android;

    public List<Gank> getApp() {
        return this.App;
    }

    public void setApp(List<Gank> App) {
        this.App = App;
    }

    public List<Gank> get休息视频() {
        return this.休息视频;
    }

    public void set休息视频(List<Gank> 休息视频) {
        this.休息视频 = 休息视频;
    }

    public List<Gank> get福利() {
        return this.福利;
    }

    public void set福利(List<Gank> 福利) {
        this.福利 = 福利;
    }

    public List<Gank> get前端() {
        return this.前端;
    }

    public void set前端(List<Gank> 前端) {
        this.前端 = 前端;
    }

    public List<Gank> getIOS() {
        return this.iOS;
    }

    public void setIOS(List<Gank> iOS) {
        this.iOS = iOS;
    }

    public List<Gank>  getAndroid() {
        return this.Android;
    }

    public void setAndroid(List<Gank>  Android) {
        this.Android = Android;
    }

    @Override
    public String toString() {
        return "GankDataResults{" +
                "App=" + App +
                ", 休息视频=" + 休息视频 +
                ", 福利=" + 福利 +
                ", 前端=" + 前端 +
                ", iOS=" + iOS +
                ", Android=" + Android +
                '}';
    }
}
