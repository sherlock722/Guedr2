package com.fjc.guedr2.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.fjc.guedr2.R;
import com.fjc.guedr2.fragment.CityPagerFragment;

public class ForecastActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);


        //ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Le decimos a nuestra actividad que queremos usar esa vista toolbar como nuestra ToolBar
        setSupportActionBar(toolbar);

        /*Esto estaba de manera estatica en el activity_forecast.xml
        <fragment
              android:id="@+id/fragment"
              android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:name="com.fjc.guedr2.fragment.CityPagerFragment"
              tools:layout="@layout/fragment_city_pager">

        </fragment>
         */

        //Como construir un fragment de manera dinamica
        FragmentManager fm = getFragmentManager();

        //Comprobamos primero que no tenemos añadido el fragment a nuestra jerarquia
        //Recuerda que las actividades de Android se recrean en varias situaciones
        //Giro del dispositivo
        //Falta de memoria
        //Sale el Teclado
        //Cambia el tamaño de la interfaz por algún motivo (split view)

        //Para eso lo comprobamos preguntandole al FragmentManager a ver si ya exite un R.id.fragment_city_pager
        if (fm.findFragmentById(R.id.fragment_city_pager)==null) {
            //Como no existe lo añadimos como una transaccion a nuestra jerarquia de vistas
            fm.beginTransaction()
                    .add(R.id.fragment_city_pager, new CityPagerFragment())
                    .commit();
        }
    }


    //Para saber de que pantalla vengo (este se utiliza para la pantalla de ajustes)
    /*private static final int REQUEST_UNIT = 1;

    private TextView mMax_temp;
    private TextView mMin_temp;
    private TextView mHumidity;
    private TextView mDescription;
    private ImageView mForecast_image;
    private boolean showCelsius;
    private Forecast mForecast;

    //Para persistir datos
    private static final String PREFERENCE_UNITS="units";*/

/*
    //Esto era el onCreate de la Actividad ForecastActivity
    //@Override
    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_forecast);

        //Recuperar un valor del fichero integer
        //Log.v("INTEGER", "EL resultado es: " + getResources().getInteger(R.integer.sherlock722));

        mMax_temp = (TextView) findViewById(R.id.max_temp);
        mMin_temp = (TextView) findViewById(R.id.min_temp);
        mHumidity = (TextView) findViewById(R.id.humidity);
        mDescription = (TextView) findViewById(R.id.description_forecast);
        mForecast_image = (ImageView) findViewById(R.id.forecast_img);

        //Valor por defecto para showCelsius;
        //showCelsius = true;

        //Recuperamos los valores almacenados
        showCelsius = PreferenceManager.getDefaultSharedPreferences(this).
                getBoolean(PREFERENCE_UNITS, true); //Se asigna true como valor por defecto si inicialmente no tienen valor


        //Creo el modelo
        //Forecast forecast = new Forecast(24,10,25,"Sol y nubes", R.drawable.sun_cloud);
        mForecast = new Forecast(24,10,25,"Sol y nubes", R.drawable.sun_cloud);


         //mMax_temp.setText(String.valueOf(forecast.getMaxTemp()));
        //mMax_temp.setText(String.format("Temperatura máxima: %.2f", forecast.getMaxTemp()));
        //mMin_temp.setText(String.format("Temperatura mínima: %.2f", forecast.getMinTemp()));
        //mHumidity.setText(String.format("Humedad: %.2f", forecast.getHumidity()));
        //mDescription.setText(forecast.getDescription());

        //Para el icono utilizamos un método que nos devuelve un recurso
        //mForecast_image.setImageResource(forecast.getIcon());


    //setForecast(forecast);
    setForecast(mForecast);
    }*/

    //Indica como es el Menu
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Llamo al método del padre
        super.onCreateOptionsMenu(menu);
        //Para el Menu
        getMenuInflater().inflate(R.menu.menu_forecast, menu);
        //return super.onCreateOptionsMenu(menu);
        return true; //Se han realizado modificaciones
    }*/

    //Indica que pasa al pulsar una opción de Menú
    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        boolean superReturn = super.onOptionsItemSelected(item);

        if (item.getItemId()==R.id.menu_show_settings) {

            //Lanzamos la actividad SettingsActivity utilizando un intent explicito
            //ya que decimos que actividad explicitamente se tiene que lanzar
            Intent intent = new Intent(this,SettingActivity.class);

            //Pasamos parametros a la segunda pantalla
            intent.putExtra(SettingActivity.EXTRA_CURRENT_UNIT,showCelsius);

            //Lanzamos el intent explicito para lanzar la pantalla de ajustes (sin recibir parametros)
            //startActivity(intent);

            //Lanzamos el intent recogiendo los parametros que me manda la pantalla de ajustes
            startActivityForResult(intent, REQUEST_UNIT);
            return true;
        }

        return superReturn;
    }*/
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_UNIT){
            //Vengo de la pantalla de ajustes
            if (resultCode == RESULT_OK){

                //Guardamos el valor previo de showCelsius por si el usuario quiere deshacer
                final boolean oldShowCelsius = showCelsius;

                //El usuario ha seleccionado algo y la información vienen en data
                int  optionSelected = data.getIntExtra("units",R.id.celsius_rb); //clave a recuperar el valor ó valor por defecto
                if (optionSelected == R.id.celsius_rb){
                    //El usuario ha seleccionado Celsius
                    Log.v("CELSIUS", "Se ha seleccionado CELSIUS");
                    showCelsius = true;


                } else if (optionSelected == R.id.farenheit_rb){
                    //El usuario ha seleccionado Farenheit
                    Log.v("FARENHEIT", "Se ha seleccionado FARENHEIT");
                    showCelsius = false;

                }

                //1-Persistimos las preferencias
                //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                //2-Entramos en modo Edicion
                //SharedPreferences.Editor editor = prefs.edit();
                //3-Guardamos los datos
                //editor.putBoolean(PREFERENCE_UNITS, showCelsius);
                //4-Se hace commit
                //editor.commit();

                //La otra manera de hacer lo anterior sería
                PreferenceManager.getDefaultSharedPreferences(this)
                        .edit()
                        .putBoolean(PREFERENCE_UNITS, showCelsius)
                        .commit();

                //Actualizamos el módelo
                setForecast(mForecast);

                //Avisamos al usuario de los cambios en las preferencias (Toast)
                //Toast.makeText(this,"Cambios Realizados",Toast.LENGTH_LONG).show();

                //Avisamos al usuario utilizando SnackBar
                Snackbar.make(findViewById(android.R.id.content), "Preferencias Actualizadas", Snackbar.LENGTH_LONG)
                        .setAction("Deshacer", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //Restauramos la variable showCelsius
                                showCelsius = oldShowCelsius;

                                //Guardamos el valor anterior en las preferencias
                                PreferenceManager.getDefaultSharedPreferences(ForecastActivity.this)
                                        .edit()
                                        .putBoolean(PREFERENCE_UNITS, showCelsius)
                                        .commit();
                                //Actualizamos la interfaz(modelo)
                                setForecast(mForecast);
                            }
                        })
                        .show();
            }
        }
    }*/

    /*public void setForecast (Forecast forecast){

        float maxTemp = forecast.getMaxTemp();
        float minTemp = forecast.getMinTemp();

        Log.v ("boolean", String.valueOf(showCelsius));

        if (showCelsius != true){

            maxTemp = atFarnheid(maxTemp);
            minTemp = atFarnheid(minTemp);
        }

        //mMax_temp.setText(String.valueOf(forecast.getMaxTemp()));
        mMax_temp.setText(String.format("Temperatura máxima: %.2f", maxTemp));
        mMin_temp.setText(String.format("Temperatura mínima: %.2f", minTemp));
        mHumidity.setText(String.format("Humedad: %.2f", forecast.getHumidity()));
        mDescription.setText(forecast.getDescription());
        //Para el icono utilizamos un método que nos devuelve un recurso
        mForecast_image.setImageResource(forecast.getIcon());


    }*/

    /*public static float atFarnheid (Float celsius){

        return (celsius * 1.8f) + 32;
    }*/


}
