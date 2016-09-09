package com.lin.mvp.mygankdemo.bean;

import java.util.List;


public class VideoData {
    private boolean error;
    private List<VideoDataResults> results;

    public boolean getError() {
        return this.error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<VideoDataResults> getResults() {
        return this.results;
    }

    public void setResults(List<VideoDataResults> results) {
        this.results = results;
    }
}
