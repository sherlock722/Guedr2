package com.fjc.guedr2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;
import android.widget.ImageView;
import android.widget.TextView;

public class ForecastActivity extends AppCompatActivity {

    private TextView mMax_temp;
    private TextView mMin_temp;
    private TextView mHumidity;
    private TextView mDescription;
    private ImageView mForecast_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);


         mMax_temp = (TextView) findViewById(R.id.max_temp);
         mMin_temp = (TextView) findViewById(R.id.min_temp);
         mHumidity = (TextView) findViewById(R.id.humidity);
         mDescription = (TextView) findViewById(R.id.description_forecast);
         mForecast_image = (ImageView) findViewById(R.id.forecast_img);


        //Creo el modelo
        Forecast forecast = new Forecast(20,10,25,"Sol y nubes", R.drawable.sun_cloud);
         mMax_temp.setText(String.valueOf(forecast.getMaxTemp()));


    }
}
