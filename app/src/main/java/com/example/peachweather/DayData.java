package com.example.peachweather;

import org.json.JSONObject;

public class DayData {
    JSONObject jsn;
    public DayData(JSONObject jsn) {
        this.jsn = jsn;
    }

    public JSONObject getJsn() {
        return jsn;
    }

    public void setJsn(JSONObject jsn) {
        this.jsn = jsn;
    }
}
