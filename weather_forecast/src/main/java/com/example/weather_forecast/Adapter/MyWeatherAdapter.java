package com.example.weather_forecast.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather_forecast.Enity.MyWeatherBin;
import com.example.weather_forecast.R;

import java.util.ArrayList;
import java.util.List;

public class MyWeatherAdapter extends RecyclerView.Adapter <MyWeatherAdapter.MyViewHolder>{
    private Context context;
    private List<MyWeatherBin.ResultBean.DataBean.WeatherBeanX> weatherBeanX = new ArrayList<>();
    public MyWeatherAdapter(Context context){
        this.context = context;

    }
    public void addWeather(List<MyWeatherBin.ResultBean.DataBean.WeatherBeanX> weatherBean,
                           boolean isClear){
        if (isClear){
            this.weatherBeanX.clear();
            this.weatherBeanX.addAll(weatherBean);
        }else {
            this.weatherBeanX.addAll(weatherBean);
        }
        notifyDataSetChanged();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView_weatherIcon;
        TextView textView_weather;
        TextView textView_temperature;
        TextView textView_weekforday;
         MyViewHolder(View itemView) {
            super(itemView);
            imageView_weatherIcon = itemView.findViewById(R.id.recyclerview_item_weathericon);
            textView_weather = itemView.findViewById(R.id.recyclerview_item_weather);
            textView_temperature  = itemView.findViewById(R.id.recyclerview_item_temperature);
            textView_weekforday = itemView.findViewById(R.id.recyclerview_item_weekforday);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.recyclerview_item,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyWeatherBin.ResultBean.DataBean.WeatherBeanX weather = weatherBeanX.get(position);
        String weatherType = weather.getInfo().getDay().get(0);
        if (weatherType.equals("0")){
            holder.imageView_weatherIcon.setImageResource(R.drawable.sunny);
        }else if (weatherType.equals("1")){
            holder.imageView_weatherIcon.setImageResource(R.drawable.cloudy);
        }else if (weatherType.equals("2")){
            holder.imageView_weatherIcon.setImageResource(R.drawable.lotcloudy);
        }else if (weatherType.equals("3")){
            holder.imageView_weatherIcon.setImageResource(R.drawable.rain);
        }
        String weaterbean = weather.getInfo().getDay().get(1);
        holder.textView_weather.setText(weaterbean);
        String temperature = weather.getInfo().getDay().get(2)+"℃/"+weather.getInfo().getNight()
                .get(2)+"℃";
        holder.textView_temperature.setText(temperature);
        holder.textView_weekforday.setText(weather.getWeek());

    }

    @Override
    public int getItemCount() {
        return weatherBeanX.size();
    }

}
