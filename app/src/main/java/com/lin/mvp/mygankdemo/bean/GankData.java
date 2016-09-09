package com.lin.mvp.mygankdemo.bean;

public class GankData {
    private String[] category;
    private boolean error;
    private GankDataResults results;

    public String[] getCategory() {
        return this.category;
    }

    public void setCategory(String[] category) {
        this.category = category;
    }

    public boolean getError() {
        return this.error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public GankDataResults getResults() {
        return this.results;
    }

    public void setResults(GankDataResults results) {
        this.results = results;
    }
}
