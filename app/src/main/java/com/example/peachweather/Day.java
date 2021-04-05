package com.example.peachweather;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Day {
    ArrayList<DayData> day;
    String date;
    String minTemp, maxTemp, weather;

    public Day() {
    }

    public Day(ArrayList<DayData> day, String date, String minTemp, String maxTemp, String weather) {
        this.weather = weather;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.date = date;
        this.day = day;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<DayData> getDay() {
        return day;
    }

    public void setDay(ArrayList<DayData> day) {
        this.day = day;
    }
    public String getNameOfDay(String time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat pattern = new SimpleDateFormat("EEEE", Locale.forLanguageTag("pl"));
        String nameOfDay = pattern.format(newDate);
        return nameOfDay;
    }
}
