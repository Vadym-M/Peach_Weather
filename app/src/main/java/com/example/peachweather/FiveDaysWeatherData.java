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
    Day currentDay;

    public ArrayList<Day> getDays() {
        if(days.size()>=5){ days.remove(days.size()-1);}
        if(days.size()>4) {
            days.remove(0);
        }
        Log.d("log", "SIZE IS : "+ days.size());
        return days;
    }

    public Day getCurrentDay() {
        Log.d("log", "CURRENY DAY: " + currentDay.getDate());
        return currentDay;
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
                        .addHeader("x-rapidapi-key", "55e384f6c9msh6c75a06007dc332p1ff13ejsnd757a6f9424b")
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
                        for(DayData dy: d.getDay()){
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
                        if(j.getJSONArray(cnst.WEATHER.get()).getJSONObject(0).getString(cnst.MAIN.get()).equals(cnst.SNOW.get())){
                            weather = cnst.SNOW.get();
                            des = j.getJSONArray(cnst.WEATHER.get()).getJSONObject(0).getString(cnst.DESCRIPTION.get());
                        }
                        if(j.getJSONArray(cnst.WEATHER.get()).getJSONObject(0).getString(cnst.MAIN.get()).equals(cnst.RAIN.get()) && !weather.equals(cnst.SNOW.get())){
                            weather = cnst.RAIN.get();
                            des = j.getJSONArray(cnst.WEATHER.get()).getJSONObject(0).getString(cnst.DESCRIPTION.get());
                        }
                        if(j.getJSONArray(cnst.WEATHER.get()).getJSONObject(0).getString(cnst.MAIN.get()).equals(cnst.CLOUD.get()) && !weather.equals(cnst.RAIN.get()) && !weather.equals(cnst.SNOW.get())){
                            weather = cnst.CLOUD.get();
                            des = j.getJSONArray(cnst.WEATHER.get()).getJSONObject(0).getString(cnst.DESCRIPTION.get());
                        }

                        dayData.add(new DayData(j));
                    }
                }
                Log.d("log", "INIT :  okey");
                days.add(new Day(dayData, dates.get(i), String.valueOf(minTemp), String.valueOf(maxTemp), weather, des));
                dayData = null;
                maxTemp = 0;
                minTemp = 0;
                weather = cnst.CLEAR.get();
                des = "bezchmurnie";
            }
            currentDay = days.get(0);

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
