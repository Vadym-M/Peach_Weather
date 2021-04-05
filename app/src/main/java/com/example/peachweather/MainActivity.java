package com.example.peachweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    TextView currentTemp, weatherMain, currentCity, currentMinMax;
    ImageView currentWeatherIcon;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentTemp = findViewById(R.id.temp);
        currentMinMax = findViewById(R.id.currentMinMax);
        weatherMain = findViewById(R.id.weatherMain);
        currentCity = findViewById(R.id.currentCity);
        recyclerView = findViewById(R.id.recyclerView);
        currentWeatherIcon = findViewById(R.id.currentWeatherIcon);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        CurrentWeatherData currentWeatherData = new CurrentWeatherData();
        if(currentWeatherData.getWeather(cnst.MAIN.get()).equals("Clear")){
            currentWeatherIcon.setImageResource(R.drawable.sun);
        }
        else if(currentWeatherData.getWeather(cnst.MAIN.get()).equals("Rain")){
            currentWeatherIcon.setImageResource(R.drawable.rain);
        }else{
            currentWeatherIcon.setImageResource(R.drawable.sun_cloud);
        }
        currentMinMax.setText("min: "+ currentWeatherData.getMain(cnst.TEMP_MIN.get()) + " - max: " + currentWeatherData.getMain(cnst.TEMP_MAX.get()));
        currentCity.setText("Dzisiaj");
        weatherMain.setText(currentWeatherData.getWeather(cnst.DESCRIPTION.get()));
        currentTemp.setText(currentWeatherData.getMain(cnst.TEMP.get()) + "℃");
        FiveDaysWeatherData fiveDaysWeatherData = new FiveDaysWeatherData();
        adapter = new RecyclerViewAdapter(fiveDaysWeatherData.getDays(),this);
        recyclerView.setAdapter(adapter);


    }

}