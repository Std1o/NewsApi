package com.stdio.newsapi;

public class NewsSpinnerModel {
    boolean isHeader;
    String name;
    String description;
    String url;

    public NewsSpinnerModel(boolean isHeader, String name, String description, String url) {
        this.isHeader = isHeader;
        this.name = name;
        this.description = description;
        this.url = url;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
