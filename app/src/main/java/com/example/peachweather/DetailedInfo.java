package com.example.peachweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailedInfo extends AppCompatActivity {
    RecyclerForInfo adapter;
    RecyclerView recyclerView;
    ImageView f_curWeatherIcon;
    TextView f_curDate, f_curMinMax, f_weatherMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_info);

        f_curDate = findViewById(R.id.infoCurrentDate);
        f_curMinMax = findViewById(R.id.infoCurrentMinMax);
        f_weatherMain = findViewById(R.id.infoWeatherMain);
        recyclerView = findViewById(R.id.infoRecyclerView);
        int pos = getIntent().getExtras().getInt("pos");
        //f_curWeatherIcon = findViewById(R.id.infoCurrentWeatherIcon);
        if(pos<10)
        {

            FiveDaysWeatherData fiveDaysWeatherData = new FiveDaysWeatherData();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            ArrayList<Day> days = fiveDaysWeatherData.getDays();
            f_curMinMax.setText("max: "+ days.get(pos).getMaxTemp() + " - mix: " + days.get(pos).getMinTemp());
            f_curDate.setText(days.get(pos).getDate());
            f_weatherMain.setText(days.get(pos).getDesc());
            adapter = new RecyclerForInfo(days, this, pos);
            recyclerView.setAdapter(adapter);
        }else{
            FiveDaysWeatherData fiveDaysWeatherData = new FiveDaysWeatherData();
            Day currentDay = fiveDaysWeatherData.getCurrentDay();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            ArrayList<Day> days = new ArrayList<>();
            days.add(currentDay);
            f_curMinMax.setText("max: "+ currentDay.getMaxTemp() + " - mix: " + currentDay.getMinTemp());
            f_curDate.setText(currentDay.getDate());
            adapter = new RecyclerForInfo(days, this, 0);
            recyclerView.setAdapter(adapter);
        }

    }
}
