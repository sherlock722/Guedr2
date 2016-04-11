package com.fjc.guedr2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.widget.ImageView;
import android.widget.TextView;

public class ForecastActivity extends AppCompatActivity {

    private static final int REQUEST_UNIT = 1;

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
         //mMax_temp.setText(String.valueOf(forecast.getMaxTemp()));
        mMax_temp.setText(String.format("Temperatura máxima: %.2f", forecast.getMaxTemp()));
        mMin_temp.setText(String.format("Temperatura mínima: %.2f", forecast.getMinTemp()));
        mHumidity.setText(String.format("Humedad: %.2f", forecast.getHumidity()));
        mDescription.setText(forecast.getDescription());
        //Para el icono utilizamos un método que nos devuelve un recurso
        mForecast_image.setImageResource(forecast.getIcon());


    }

    //Indica como es el Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Llamo al método del padre
        super.onCreateOptionsMenu(menu);
        //Para el Menu
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        //return super.onCreateOptionsMenu(menu);
        return true; //Se han realizado modificaciones
    }

    //Indica que pasa al pulsar una opción de Menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        boolean superReturn = super.onOptionsItemSelected(item);

        if (item.getItemId()==R.id.menu_show_settings) {

            //Lanzamos la actividad SettingsActivity utilizando un intent explicito
            //ya que decimos que actividad explicitamente se tiene que lanzar
            Intent intent = new Intent(this,SettingActivity.class);

            //Lanzamos el intent explicito para lanzar la pantalla de ajustes (sin recibir parametros)
            //startActivity(intent);

            //Lanzamos el intent recogiendo los parametros que me manda la pantalla de ajustes
            startActivityForResult(intent,REQUEST_UNIT);
            return true;
        }

        return superReturn;
    }
}
