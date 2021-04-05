package com.example.peachweather;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FiveDaysWeatherData {
    JSONObject jsonData;
    ArrayList<JSONObject> jsonObj = new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();
    ArrayList<DayData> dayData;
    ArrayList<Day> days = new ArrayList<>();

    public ArrayList<Day> getDays() {
        return days;
    }

    public void setDays(ArrayList<Day> days) {
        this.days = days;
    }

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
                    for(int i = 0; i<jsonData.getJSONArray(cnst.LIST.get()).length(); i++){
                        jsonObj.add(jsonData.getJSONArray(cnst.LIST.get()).getJSONObject(i));
                    }
                    getDates();
                    getDaysList();
                    for(Day d: days){
                        Log.d("log","Day is:" + d.getDate());
                        for(DayData dy: d.getDay()){
                            Log.d("log","TEEEEEEEEEEST" + dy.getJsn().names());
                        }
                    }

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
                if(!checkSimilar(j.getString(cnst.DATE.get()).substring(0,10))){
                    dates.add(j.getString(cnst.DATE.get()).substring(0,10));
                }
            }else{
                dates.add(j.getString(cnst.DATE.get()).substring(0,10));
            }
        }
    }
    private void getDaysList() throws JSONException {
            float maxTemp =0 , minTemp = 0;
            String weather = cnst.CLEAR.get();
            String des = "bezchmurnie";
            for(int i = 0; i< dates.size(); i++){
                dayData = new ArrayList<>();
                for(JSONObject j: jsonObj){
                    if(j.getString(cnst.DATE.get()).substring(0,10).equals(dates.get(i))){
                        if(Float.parseFloat(j.getJSONObject(cnst.MAIN.get()).getString(cnst.TEMP_MAX.get())) > maxTemp){
                            maxTemp = Float.parseFloat(j.getJSONObject(cnst.MAIN.get()).getString(cnst.TEMP_MAX.get()));
                        }
                        if(Float.parseFloat(j.getJSONObject(cnst.MAIN.get()).getString(cnst.TEMP_MIN.get())) < minTemp){
                            minTemp = Float.parseFloat(j.getJSONObject(cnst.MAIN.get()).getString(cnst.TEMP_MIN.get()));
                        }
                        if(j.getJSONArray(cnst.WEATHER.get()).getJSONObject(0).getString(cnst.MAIN.get()).equals(cnst.RAIN.get())){
                            weather = cnst.RAIN.get();
                            des = j.getJSONArray(cnst.WEATHER.get()).getJSONObject(0).getString(cnst.DESCRIPTION.get());
                        }
                        if(j.getJSONArray(cnst.WEATHER.get()).getJSONObject(0).getString(cnst.MAIN.get()).equals(cnst.CLOUD.get()) && !weather.equals(cnst.RAIN.get())){
                            weather = cnst.CLOUD.get();
                            des = j.getJSONArray(cnst.WEATHER.get()).getJSONObject(0).getString(cnst.DESCRIPTION.get());
                        }

                        dayData.add(new DayData(j));
                    }
                }
                days.add(new Day(dayData, dates.get(i), String.valueOf(minTemp), String.valueOf(maxTemp), weather, des));
                dayData = null;
                maxTemp = 0;
                minTemp = 0;
                weather = cnst.CLEAR.get();
                des = "bezchmurnie";
            }

    }
    private boolean checkSimilar(String s){
        boolean result = false;
        for(String st: dates){
            if(st.equals(s)){
                result = true;
                break;
            }
            else{
                result = false;
            }
        }
        return result;
    }
}
