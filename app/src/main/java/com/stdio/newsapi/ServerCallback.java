package com.stdio.newsapi;

import org.json.JSONObject;

public interface ServerCallback {
    void onSuccess(JSONObject result);
}
