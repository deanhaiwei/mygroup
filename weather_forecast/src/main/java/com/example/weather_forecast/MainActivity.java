package com.example.weather_forecast;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.weather_forecast.Adapter.MyWeatherAdapter;
import com.example.weather_forecast.Enity.MyWeatherBin;
import com.example.weather_forecast.Manager.HttpWeathrManager;
import com.natasa.progressviews.CircleSegmentBar;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.DrawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.NavigationView)
    NavigationView navigationView;
    @BindView(R.id.actionbar_side)
    ImageView imageView_side;
    @BindView(R.id.actionbar_titile)
    TextView textView_title;
    @BindView(R.id.actionbar_statics)
    ImageView imageView_statics;
    @BindView(R.id.RecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refrenshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.FrameLyout)
    FrameLayout frameLayout;


    @BindView(R.id.textView_RealTime_Update)
    TextView textView_realtime_updata;
    @BindView(R.id.textView_RealTime_WeekDay)
    TextView textView_realtime_weekDay;
    @BindView(R.id.textView_RealTime_Condition)
    TextView textView_realtime_condition;
    @BindView(R.id.CircleSegmentBarBar)
    CircleSegmentBar progressBar;

    Handler handler = null;
    Runnable runnable = null;
    List<MyWeatherBin.ResultBean.DataBean.WeatherBeanX> weathers = new ArrayList<>();
    private MyWeatherAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        InitialProperties();
        InitialRealTime();
        setListener();
    }
    public void InitialRealTime(){
        progressBar.setCircleViewPadding(5);
        progressBar.setWidth(280);
        progressBar.setWidthProgressBackground(30);
        progressBar.setWidthProgressBarLine(25);
        progressBar.setStartPositionInDegrees(90);
        progressBar.setLinearGradientProgress(true);
    }
    private void setListener() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String cityname =  item.getTitle().toString();
                textView_title.setText(cityname);
                refrashweather(cityname,true);
                drawerLayout.closeDrawer(Gravity.LEFT);


                return true;
            }
        });
    }
    public void refrashweather(String cityname,final boolean isClear){
        HttpWeathrManager.LoadWeather(this, cityname, new HttpWeathrManager.WeatherBeaLoadListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void WeatherBeaLoadEnd(MyWeatherBin myWeatherBin) {
                List<MyWeatherBin.ResultBean.DataBean.WeatherBeanX> Mweathers =
                        myWeatherBin.getResult().getData().getWeather();
                weathers = Mweathers;
                adapter.addWeather(weathers,true);
                swipeRefreshLayout.setRefreshing(false);
                String date = myWeatherBin.getResult().getData().getRealtime().getDate();
                String time = myWeatherBin.getResult().getData().getRealtime().getTime();
                textView_realtime_updata.setText(date+" "+time);
                int week= Integer.parseInt(myWeatherBin.getResult().getData().getRealtime().getWeek());
                String WeekDay = switchWeekDay(week);
                textView_realtime_weekDay.setText(WeekDay);
                String condition = myWeatherBin.getResult().getData().getRealtime().getWeather().getInfo();
                String quality = myWeatherBin.getResult().getData().getPm25().getPm25().getQuality();
                textView_realtime_condition.setText(condition+"|"+"空气质量"+quality);
                int temp = Integer.parseInt(myWeatherBin.getResult().getData().getRealtime().getWeather().getTemperature());
                setTemperature(temp);
            }
        });
    }
    public void setTemperature(final int temp){
        handler.removeCallbacksAndMessages(null);
        runnable = new Runnable() {
            int progress = 0;
            @Override
            public void run() {
                progress++;
                if (progress<100*temp/50){
                    progressBar.setProgress(progress);
                    progressBar.setText(temp+"℃");
                }
                handler.postDelayed(runnable,50);
            }
        };
        handler.postDelayed(runnable,1000);

    }
    private String switchWeekDay(int weekNo) {
        String weekDay = "";
        switch (weekNo) {
            case 1:
                weekDay = "星期一";
                break;
            case 2:
                weekDay = "星期二";
                break;
            case 3:
                weekDay = "星期三";
                break;
            case 4:
                weekDay = "星期四";
                break;
            case 5:
                weekDay = "星期五";
                break;
            case 6:
                weekDay = "星期六";
                break;
            case 7:
                weekDay = "星期日";
                break;
        }
        return weekDay;
    }

    @Override
    protected void onResume() {
        super.onResume();
        String CityName = textView_title.getText().toString();
        refrashweather(CityName,true);
    }

    private void InitialProperties() {
        imageView_side.setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_ATOP);
        imageView_statics.setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_ATOP);
        navigationView.setItemIconTintList(null);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new MyWeatherAdapter(this);
        recyclerView.setAdapter(adapter);
    }
    @OnClick(R.id.actionbar_side)
    public void ClickImageSide(){
        if (!drawerLayout.isDrawerOpen(Gravity.LEFT)){
            drawerLayout.openDrawer(Gravity.LEFT);
        }else {
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
    }
    @OnClick(R.id.actionbar_statics)
    public void isOpenFrameLyout(){
        int visibility  =  frameLayout.getVisibility();
        if (visibility==View.VISIBLE){
            frameLayout.setVisibility(View.INVISIBLE);
        }else {
            frameLayout.setVisibility(View.VISIBLE);
            GraphicalView view = ChartFactory.getLineChartView(this,getDataSet(),getRenderer());
            frameLayout.addView(view);
        }
    }
    public XYMultipleSeriesRenderer getRenderer(){
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        int color[] ={Color.GREEN,Color.YELLOW};
        PointStyle styles[] = new PointStyle[]{
                PointStyle.CIRCLE,PointStyle.SQUARE
        };
        renderer.setXLabels(7);
        renderer.setShowGrid(true);
        renderer.setXLabelsAlign(Paint.Align.RIGHT);
        renderer.setYLabelsAlign(Paint.Align.RIGHT);
        renderer.setChartTitle("温度趋势");
        renderer.setXTitle("未来7天");
        renderer.setYTitle("温度");
        renderer.setChartTitleTextSize(48);
        renderer.setAxisTitleTextSize(30);
        renderer.setLabelsTextSize(30);
        renderer.setLegendTextSize(26);

        renderer.setYLabels(8);
        renderer.setXAxisMax(7.5);
        renderer.setXAxisMin(0.5);
        renderer.setYAxisMin(-15);
        renderer.setYAxisMax(50);


        //设置座标轴的颜色
        renderer.setAxesColor(Color.LTGRAY);
        //设置标签文本的颜色
        renderer.setLabelsColor(Color.LTGRAY);
        //设置拐点的大小
        renderer.setPointSize(8f);
        renderer.setMargins(new int[]{50,50,15,30});
        for (int i=0;i<color.length;i++){
            XYSeriesRenderer r=new XYSeriesRenderer();
            r.setLineWidth(4);
            r.setColor(color[i]);
            r.setFillPoints(true);
            r.setPointStyle(styles[i]);
            renderer.addSeriesRenderer(r);

        }


        return renderer;
    }
    public XYMultipleSeriesDataset getDataSet(){
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        String[]titiles = {"白天温度","夜晚温度"};
        List<double[]> x= new ArrayList<>();
        for (int i=0;i<titiles.length;i++){
            double [] d = new double[]{1,2,3,4,5,6,7};
            x.add(d);
        }
        List<double[]> y= new ArrayList<>();
        double days[] = new double[7];
        double nights[]  = new double[7];
        for (int a = 0 ;a<weathers.size();a++){
            days[a] = Double.parseDouble(weathers.get(a).getInfo().getDay().get(2));
            nights[a] = Double.parseDouble(weathers.get(a).getInfo().getNight().get(2));
        }
        y.add(days);
        y.add(nights);
        for (int i = 0;i<titiles.length;i++){
            XYSeries series = new XYSeries(titiles[i]);
            double[] xv = x.get(i);
            double[]yv = y.get(i);
            for (int a=0;a<xv.length;a++){
                series.add(xv[a],yv[a]);
            }
            dataset.addSeries(series);
        }

        return dataset;
    }
}
