package com.satyajit.newz;

import java.util.ArrayList;

public class NewsModel {

    private int totalResults;
    private String status;
    private ArrayList<articles> articles;

    public NewsModel(int totalResults, String status, ArrayList<com.satyajit.newz.articles> articles) {
        this.totalResults = totalResults;
        this.status = status;
        this.articles = articles;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<com.satyajit.newz.articles> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<com.satyajit.newz.articles> articles) {
        this.articles = articles;
    }
}
