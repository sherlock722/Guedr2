package com.fjc.guedr2;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by javier on 14/4/16.
 */
public class FragmentForecast extends Fragment {

    //Para saber de que pantalla vengo (este se utiliza para la pantalla de ajustes)
    private static final int REQUEST_UNIT = 1;

    private TextView mMax_temp;
    private TextView mMin_temp;
    private TextView mHumidity;
    private TextView mDescription;
    private ImageView mForecast_image;
    private boolean showCelsius;
    private Forecast mForecast;

    //Para persistir datos
    private static final String PREFERENCE_UNITS="units";


    @Nullable
    @Override

    //Este metodo necesita devolver la Vista Raiz despues de crerar el fragment
    //Las actividades llaman a los onCreateView de los fragment y los fragments devuelve la vista
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //Devuelvo la vista del fragment
        //Paso el container (de donde cuelga)
        //Paso false
        View root = inflater.inflate(R.layout.fragment_forecast, container,false);

        //Recuperar un valor del fichero integer
        //Log.v("INTEGER", "EL resultado es: " + getResources().getInteger(R.integer.sherlock722));


        //En la vista raiz (root) tengo acceso al método findViewById
        mMax_temp = (TextView) root.findViewById(R.id.max_temp);
        mMin_temp = (TextView) root.findViewById(R.id.min_temp);
        mHumidity = (TextView) root.findViewById(R.id.humidity);
        mDescription = (TextView) root.findViewById(R.id.description_forecast);
        mForecast_image = (ImageView) root.findViewById(R.id.forecast_img);

        //Valor por defecto para showCelsius;
        //showCelsius = true;

        //Recuperamos los valores almacenados
        showCelsius = PreferenceManager.getDefaultSharedPreferences(getActivity()).
                getBoolean(PREFERENCE_UNITS, true); //Se asigna true como valor por defecto si inicialmente no tienen valor


        //Creo el modelo
        //Forecast forecast = new Forecast(24,10,25,"Sol y nubes", R.drawable.sun_cloud);
        mForecast = new Forecast(24,10,25,"Sol y nubes", R.drawable.sun_cloud);

        setForecast(mForecast);

        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Tengo menu (llama a los métodos que crean los menus)
        setHasOptionsMenu(true);
    }

    //Indica como es el Menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //Llamo al método del padre
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_forecast, menu);
    }

    //Indica que pasa al pulsar una opción de Menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        boolean superReturn = super.onOptionsItemSelected(item);

        if (item.getItemId()==R.id.menu_show_settings) {

            //Lanzamos la actividad SettingsActivity utilizando un intent explicito
            //ya que decimos que actividad explicitamente se tiene que lanzar
            Intent intent = new Intent(getActivity(),SettingActivity.class);

            //Pasamos parametros a la segunda pantalla
            intent.putExtra(SettingActivity.EXTRA_CURRENT_UNIT,showCelsius);

            //Lanzamos el intent explicito para lanzar la pantalla de ajustes (sin recibir parametros)
            //startActivity(intent);

            //Lanzamos el intent recogiendo los parametros que me manda la pantalla de ajustes
            startActivityForResult(intent, REQUEST_UNIT);
            return true;
        }

        return superReturn;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_UNIT){
            //Vengo de la pantalla de ajustes
            if (resultCode == Activity.RESULT_OK){

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
                /*
                //1-Persistimos las preferencias
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                //2-Entramos en modo Edicion
                SharedPreferences.Editor editor = prefs.edit();
                //3-Guardamos los datos
                editor.putBoolean(PREFERENCE_UNITS, showCelsius);
                //4-Se hace commit
                editor.commit();
                */
                //La otra manera de hacer lo anterior sería
                PreferenceManager.getDefaultSharedPreferences(getActivity())
                        .edit()
                        .putBoolean(PREFERENCE_UNITS, showCelsius)
                        .commit();

                //Actualizamos el módelo
                setForecast(mForecast);

                //Avisamos al usuario de los cambios en las preferencias (Toast)
                //Toast.makeText(this,"Cambios Realizados",Toast.LENGTH_LONG).show();

                //Avisamos al usuario utilizando SnackBar
                //Me guardo la referencia de la vista o decirle al SnackBar que se ponga delante del View
                Snackbar.make(getView(), "Preferencias Actualizadas", Snackbar.LENGTH_LONG)
                        .setAction("Deshacer", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //Restauramos la variable showCelsius
                                showCelsius = oldShowCelsius;

                                //Guardamos el valor anterior en las preferencias
                                PreferenceManager.getDefaultSharedPreferences(getActivity())
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
    }

    public void setForecast (Forecast forecast){

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


    }

    public static float atFarnheid (Float celsius){

        return (celsius * 1.8f) + 32;
    }
}
