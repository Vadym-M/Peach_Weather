package com.example.peachweather;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    ArrayList<Day> days;
    Context context;
    Day day = new Day();
    DecimalFormat df = new DecimalFormat("#");
    public RecyclerViewAdapter(ArrayList<Day> days, Context context) {
        days.remove(0);
        if(days.size()>=5){
            days.remove(days.size()-1);
        }
        this.days = days;
        this.context = context;
        Log.d("log", "ADAPTER SIZE " + days.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.block_of_weather, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position == 0)
        {
            holder.day.setText("Jutro");
        }else {
            holder.day.setText(dateToUpperCase(day.getNameOfDay(days.get(position).date)));
        }
    holder.tempMax.setText(df.format(Float.valueOf(days.get(position).getMaxTemp())) + "°");
    holder.tempMin.setText(df.format(Float.valueOf(days.get(position).getMinTemp())) + "°");
    holder.desc.setText(days.get(position).getDesc());
        switch (days.get(position).getWeather()){
            case "Clear":
                holder.icon.setImageResource(R.drawable.sun);
                break;
            case "Rain":
                holder.icon.setImageResource(R.drawable.rain);
                break;
            case "Snow":
                holder.icon.setImageResource(R.drawable.snow);
                break;
            default:
                holder.icon.setImageResource(R.drawable.sun_cloud);
        }

    }
    private String dateToUpperCase(String date){
        return date.substring(0,1).toUpperCase()
                + date.substring(1).toLowerCase();
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView day, tempMax, tempMin, desc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.dayWeatherIcon);
            day = itemView.findViewById(R.id.currentDay);
            desc = itemView.findViewById(R.id.desc);
            tempMax = itemView.findViewById(R.id.dayTempMax);
            tempMin = itemView.findViewById(R.id.dayTempMin);
        }
    }
}
