package com.example.mostviewarticle.model;

import java.util.ArrayList;

public class ArticleData {

    String title;
    String published_date;
    String byline;
    String detail;

    ArrayList<String> media;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublished_date() {
        return published_date;
    }

    public void setPublished_date(String published_date) {
        this.published_date = published_date;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public ArrayList<String> getMedia() {
        return media;
    }

    public void setMedia(ArrayList<String> media) {
        this.media = media;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
