package com.stdio.newsapi;

public class SourcesModel {
    String name;
    String domain;

    public SourcesModel(String name, String domain) {
        this.name = name;
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
