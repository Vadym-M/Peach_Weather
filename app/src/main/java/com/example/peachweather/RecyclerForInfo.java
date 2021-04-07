package com.example.peachweather;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecyclerForInfo extends RecyclerViewAdapter {
    FiveDaysWeatherData fiveDaysWeatherData = new FiveDaysWeatherData();
    ArrayList<DayData> dayData;
    ArrayList<Day> days;
    int pos;
    public RecyclerForInfo(ArrayList<Day> days, Context context, int pos) {
        super(days, context);
        this.pos = pos;
        this.days = days;
        Log.d("log", "SIZE 2 : " + days.size());
        dayData = days.get(pos).getDay();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.block_of_more_weather, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            JSONObject getWeather = dayData.get(position).getJsn().getJSONArray(cnst.WEATHER.get()).getJSONObject(0);
            JSONObject getMain =  dayData.get(position).getJsn().getJSONObject(cnst.MAIN.get());
            holder.f_time.setText((dayData.get(position).getJsn().getString(cnst.DATE.get())).substring(11, 16));
            holder.f_humidity.setText(getMain.getString(cnst.HUMIDITY.get()) + "%");
            holder.f_temp.setText(df.format(Float.valueOf(getMain.getString(cnst.TEMP.get())))+"°C");
            holder.f_feel_like.setText(" (Odczuwalna " + df.format(Float.valueOf(getMain.getString(cnst.FEELS_LIKE.get())))+ "°C)");
            //holder.f_wind.setText(dayData.get(position).getJsn().getJSONObject(cnst.WIND.get()).getString(cnst.SPEED.get()));
            holder.f_weather.setText(getWeather.getString(cnst.DESCRIPTION.get()));
            switch (getWeather.getString(cnst.MAIN.get())){
                case "Clear":
                    holder.f_icon.setImageResource(R.drawable.sun);
                    break;
                case "Rain":
                    holder.f_icon.setImageResource(R.drawable.rain);
                    break;
                case "Snow":
                    holder.f_icon.setImageResource(R.drawable.snow);
                    break;
                case "Clouds":
                    holder.f_icon.setImageResource(R.drawable.sun_cloud);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return days.get(pos).getDay().size();
    }
}
