package com.example.peachweather;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CurrentWeatherData {
    /*
    public static final String MAIN = "main";
    public static final String WEATHER = "weather";
    public static final String DESCRIPTION = "description";
    public static final String TEMP = "temp";
    public static final String FEELS_LIKE = "feels_like";
    public static final String TEMP_MIN = "temp_min";
    public static final String TEMP_MAX = "temp_max";
    public static final String PRESSURE = "pressure";
    public static final String HUMIDITY = "humidity";
    public static final String VISIBILITY = "visibility";

     */

    Runnable runnable;
    Thread thread;
    JSONObject jsonData;
    DecimalFormat df = new DecimalFormat("#");
    public CurrentWeatherData(){
        runnable = new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://community-open-weather-map.p.rapidapi.com/weather?q=Poznan&lat=0&lon=0&id=2172797&lang=pl&units=metric")
                        .get()
                        .addHeader("x-rapidapi-key", "55e384f6c9msh6c75a06007dc332p1ff13ejsnd757a6f9424b")
                        .addHeader("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                        .build();


                try {
                    Response response = client.newCall(request).execute();
                    String myJson = response.body().string();
                    jsonData = new JSONObject(myJson);
                        Log.d("log", "TEST" + jsonData.getString(cnst.MAIN.get()));

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }


            }
        };thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public String getWeather(String key){
        String vel = "None";
        switch (key){
            case "main":
                try {
                    //Log.d("log", "Its MAIN ===> " + jsonData.getJSONArray("weather").getJSONObject(0).getString(cnst.MAIN.get()));
                    vel = jsonData.getJSONArray(cnst.WEATHER.get()).getJSONObject(0).getString(cnst.MAIN.get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "description":
                try {
                    vel = jsonData.getJSONArray(cnst.WEATHER.get()).getJSONObject(0).getString(cnst.DESCRIPTION.get());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        return vel;
    }
    @SuppressLint("DefaultLocale")
    public String getMain(String key){
        String vel = "None";
        switch (key){
            case "temp":
                try {
                    vel = jsonData.getJSONObject(cnst.MAIN.get()).getString(cnst.TEMP.get()).toString();
                    vel = df.format(Float.valueOf(vel));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "feels_like":
                try {
                    vel = jsonData.getJSONObject(cnst.MAIN.get()).getString(cnst.FEELS_LIKE.get()).toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "temp_min":
                try {
                    vel = jsonData.getJSONObject(cnst.MAIN.get()).getString(cnst.TEMP_MIN.get()).toString();
                    vel = df.format(Float.valueOf(vel));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "temp_max":
                try {
                    vel = jsonData.getJSONObject(cnst.MAIN.get()).getString(cnst.TEMP_MAX.get()).toString();
                    vel = df.format(Float.valueOf(vel));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "pressure":
                try {
                    vel = jsonData.getJSONObject(cnst.MAIN.get()).getString(cnst.PRESSURE.get()).toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "humidity":
                try {
                    vel = jsonData.getJSONObject(cnst.MAIN.get()).getString(cnst.HUMIDITY.get()).toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            default:
                break;
        }
        return vel;
    }
    public String getVisibility(){
        String vel = "None";
        try {
            vel = jsonData.getString(cnst.VISIBILITY.get()).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return vel;
    }
    public String getCity(){

        String vel = "None";
        try {
            vel = jsonData.getString(cnst.CITY.get()).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return vel;
    }
}
enum cnst{
    WIND("wind"),
    SPEED("speed"),
    SNOW("Snow"),
    CLEAR("Clear"),
    RAIN("Rain"),
    CLOUD("Clouds"),
    LIST("list"),
    DATE("dt_txt"),
    CITY("name"),
MAIN ("main"),
WEATHER ("weather"),
DESCRIPTION ("description"), TEMP ("temp"),
FEELS_LIKE ("feels_like"), TEMP_MIN ("temp_min"),
TEMP_MAX ("temp_max"), PRESSURE("pressure"),
HUMIDITY ("humidity"), VISIBILITY ("visibility");
private String s;
    cnst(String s) {
        this.s = s;
    }
    public String get(){ return s;}
}
