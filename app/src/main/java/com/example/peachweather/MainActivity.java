package com.example.peachweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    RecyclerViewAdapter adapter;
    LinearLayout block;
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
        recyclerView2 = findViewById(R.id.infoRecyclerView);
        currentWeatherIcon = findViewById(R.id.currentWeatherIcon);
        block = findViewById(R.id.currentWeatherBlock);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        CurrentWeatherData currentWeatherData = new CurrentWeatherData();
        switch (currentWeatherData.getWeather(cnst.MAIN.get())){
            case "Clear":
                currentWeatherIcon.setImageResource(R.drawable.sun);
                break;
            case "Rain":
                currentWeatherIcon.setImageResource(R.drawable.rain);
                break;
            case "Snow":
                currentWeatherIcon.setImageResource(R.drawable.snow);
                break;
            default:
                currentWeatherIcon.setImageResource(R.drawable.sun_cloud);
        }
        currentMinMax.setText("min: "+ currentWeatherData.getMain(cnst.TEMP_MIN.get()) + " - max: " + currentWeatherData.getMain(cnst.TEMP_MAX.get()));
        currentCity.setText("Dzisiaj");
        weatherMain.setText(currentWeatherData.getWeather(cnst.DESCRIPTION.get()));
        currentTemp.setText(currentWeatherData.getMain(cnst.TEMP.get()) + "℃");
        FiveDaysWeatherData fiveDaysWeatherData = new FiveDaysWeatherData();
        ArrayList<Day> days = fiveDaysWeatherData.getDays();
        adapter = new RecyclerViewAdapter(days,this);
        //adapter2 = new RecyclerForInfo(fiveDaysWeatherData.getDays(),this);

        recyclerView.setAdapter(adapter);

        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MainActivity.this, DetailedInfo.class);
                intent.putExtra("pos", 69);
                startActivity(intent);
            }
        });

    }

}