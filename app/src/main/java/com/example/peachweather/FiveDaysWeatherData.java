package com.example.peachweather;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FiveDaysWeatherData {
    JSONObject jsonData;
    ArrayList<JSONObject> jsonObj = new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();
    ArrayList<DayData> dayData = new ArrayList<>();
    ArrayList<Day> days = new ArrayList<>();
    public FiveDaysWeatherData(){
        Runnable run;
        Thread thread;
        run = new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://community-open-weather-map.p.rapidapi.com/forecast?q=Poznan&units=metric&lang=pl")
                        .get()
                        .addHeader("x-rapidapi-key", "82d36f6eacmsh340f7fbd85d7318p1e67e2jsn798a6ba7706c")
                        .addHeader("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    String myJson = response.body().string();
                    jsonData = new JSONObject(myJson);
                    for(int i = 0; i<jsonData.getJSONArray("list").length(); i++){
                        jsonObj.add(jsonData.getJSONArray("list").getJSONObject(i));
                    }
                    getDates();
                    for(String d:dates){
                        Log.d("log", "DATA --------------?" + d);
                    }
                    Log.d("log", "TEST ================================> " + jsonObj.get(0).getString("dt_txt"));
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        };thread = new Thread(run);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    private void getDates() throws JSONException {
        for(JSONObject j: jsonObj){
            if(dates.size()>0){
                for(String s:dates){
                    
                }
            dates.add(j.getString("dt_txt"));
            }else{
                dates.add(j.getString("dt_txt").substring(0,9));
            }
        }
    }
    private void getDays(){
        for(JSONObject j: jsonObj){

        }
    }
}
