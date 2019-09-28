package com.stdio.newsapi;

public class NewsSpinnerModel {
    boolean isHeader;
    String name;

    public NewsSpinnerModel(boolean isHeader, String name) {
        this.isHeader = isHeader;
        this.name = name;
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
