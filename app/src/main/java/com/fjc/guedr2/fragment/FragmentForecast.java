package com.fjc.guedr2.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.fjc.guedr2.activity.SettingActivity;
import com.fjc.guedr2.adapter.ForcastRecyclerViewAdapter;
import com.fjc.guedr2.model.City;
import com.fjc.guedr2.model.Forecast;
import com.fjc.guedr2.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

/**
 * Created by javier on 14/4/16.
 */
public class FragmentForecast extends Fragment {

    //Para saber de que pantalla vengo (este se utiliza para la pantalla de ajustes)
    private static final int REQUEST_UNIT = 1;
    private static final int LOADING_VIEW_INDEX = 0;
    private static final int FORECAST_VIEW_INDEX = 1;

    //Me lo llevo al ForcastRecyclerViewAdapter
    /*private TextView mMax_temp;
    private TextView mMin_temp;
    private TextView mHumidity;
    private TextView mDescription;
    private ImageView mForecast_image;*/

    private boolean showCelsius;
    private Forecast mForecast;
    private TextView mCityName;
    private City mCity;
    private ViewSwitcher mViewSwitcher;
    private ProgressBar mProgressBar;
    private RecyclerView mList;


    //Para persistir datos
    public static final String PREFERENCE_UNITS="units";
    public static final String ARG_CITY_NAME="cityName";

    public static final String ARG_CITY="city";


    //public static FragmentForecast newInstance (String cityName){

    public static FragmentForecast newInstance (City city){

        FragmentForecast fragment = new FragmentForecast();

        Bundle arguments = new Bundle();
        //arguments.putString(FragmentForecast.ARG_CITY, city);
        arguments.putSerializable(FragmentForecast.ARG_CITY,city);
        fragment.setArguments(arguments);

        return fragment;
    }

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

        //Me los llevo al ForcastRecyclerViewAdapter
        /*mMax_temp = (TextView) root.findViewById(R.id.max_temp);
        mMin_temp = (TextView) root.findViewById(R.id.min_temp);
        mHumidity = (TextView) root.findViewById(R.id.humidity);
        mDescription = (TextView) root.findViewById(R.id.description_forecast);
        mForecast_image = (ImageView) root.findViewById(R.id.forecast_img);*/
        mCityName = (TextView) root.findViewById(R.id.city);
        mViewSwitcher = (ViewSwitcher) root.findViewById(R.id.view_switcher);
        mProgressBar = (ProgressBar) root.findViewById(R.id.progress);

        //Le decimos al ViewSwitcher como queremos las animaciones entre vistas
        //Cuando entra una vista
        mViewSwitcher.setInAnimation(getActivity(), android.R.anim.fade_in);
        //Cuando se sale de una vista
        mViewSwitcher.setOutAnimation(getActivity(), android.R.anim.fade_out);

        //Vamos a configurar el RecyclerView
        mList = (RecyclerView) root.findViewById(android.R.id.list);
        //Le decimos al RecyclerView como quiero que te muestres y para ello utilizo el setLayoutManager
        //indicando como quiero mostrar cada uno de los elementos del Recycler (en este caso como una tabla (LinearLayoutManager))
        //Existe tambien el GridLayoutManager que muestra los elementos como una parrilla.
        mList.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Parrilla
        //mList.setLayoutManager(new GridLayoutManager(getActivity(),2));

        //Ahora le indicamos como se animan. Podemos crear nuestrr¡as propias animaciones o utilizar
        //las que vienen por defecto en el sistema
        mList.setItemAnimator(new DefaultItemAnimator());

        //Por último indicamos el adaptador (Le paso una lista vacia)
        mList.setAdapter(new ForcastRecyclerViewAdapter(new LinkedList<Forecast>(), getActivity()));



        //Con los argumentos que se recuperan configuro la vista (sólo paso nombre de la ciudad
        //Bundle arguments = getArguments();
        //String cityName= arguments.getString(ARG_CITY_NAME);
        //mCityName.setText(cityName);

        //Valor por defecto para showCelsius;
        //showCelsius = true;

        //Recuperamos los valores almacenados
        //Lo llevamos al ForcastRecyclerViewAdapter
        /*showCelsius = PreferenceManager.getDefaultSharedPreferences(getActivity()).
                getBoolean(PREFERENCE_UNITS, true); //Se asigna true como valor por defecto si inicialmente no tienen valor
        */


        //Creo el modelo
        //Forecast forecast = new Forecast(24,10,25,"Sol y nubes", R.drawable.sun_cloud);
        mForecast = new Forecast(24,10,25,"Sol y nubes", R.drawable.sun_cloud);

        //setForecast(mForecast);
        updateCityInfo();

        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Tengo menu (llama a los métodos que crean los menus)
        setHasOptionsMenu(true);

        //Es un buen sitio para recuperar los parametros que nos mandan (en este caso es un city)
        if (getArguments()!= null){
            mCity= (City) getArguments().getSerializable(ARG_CITY);
        }
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
                //setForecast(mForecast);
                updateCityInfo();

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
                                //setForecast(mForecast);
                                updateCityInfo();
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
        //mMax_temp.setText(String.format("Temperatura máxima: %.2f", maxTemp));
        //mMin_temp.setText(String.format("Temperatura mínima: %.2f", minTemp));
        //mHumidity.setText(String.format("Humedad: %.2f", forecast.getHumidity()));
        //mDescription.setText(forecast.getDescription());

        //Para el icono utilizamos un método que nos devuelve un recurso
        //mForecast_image.setImageResource(forecast.getIcon());


    }

    public void updateCityInfo (){


        //Comprobamos si tenemos datos o hay que bajarselos
        if (mCity.getForecast() == null){
            downloadWheater();
        }else{

            //Muestra la lista
            mViewSwitcher.setDisplayedChild(FORECAST_VIEW_INDEX);

            //Tenemos los datos los mostramos en la lista
            mList.setAdapter(new ForcastRecyclerViewAdapter(mCity.getForecast(),getActivity()));
        }



//        Forecast forecast = mCity.getForecast();
//
//        //Si en el modelo me llega una ciudad sin Forecast entonces me lo bajo del servicio del tiempo
//        //Pero si tiene Forecast sigo haciendo lo mismo que antes
//
//        if (forecast == null){
//
//            downloadWheater();
//
//        }else {
//
//            //Muestro en la vista contenedora ViewSwitcher la pantalla del tiempo
//            mViewSwitcher.setDisplayedChild(FORECAST_VIEW_INDEX);
//
//            //Actualizamos el nombre de la ciudad
//            mCityName.setText(mCity.getnName());
//
//            //Muestro en la interfaz mi modelo
//            float maxTemp = forecast.getMaxTemp();
//            float minTemp = forecast.getMinTemp();
//
//            Log.v("boolean", String.valueOf(showCelsius));
//
//            if (showCelsius != true) {
//
//                maxTemp = atFarnheid(maxTemp);
//                minTemp = atFarnheid(minTemp);
//            }
//
//            //mMax_temp.setText(String.valueOf(forecast.getMaxTemp()));
//            mMax_temp.setText(String.format("Temperatura máxima: %.2f", maxTemp));
//            mMin_temp.setText(String.format("Temperatura mínima: %.2f", minTemp));
//            mHumidity.setText(String.format("Humedad: %.2f", forecast.getHumidity()));
//            mDescription.setText(forecast.getDescription());
//            //Para el icono utilizamos un método que nos devuelve un recurso
//            mForecast_image.setImageResource(forecast.getIcon());
//        }


    }


    //Metodo para bajar la información de un servicio de tiempo
    private void downloadWheater() {

        //Necesito bajar la información en segundo plano por lo que utilizo la clase AsyncTask
        //Esta clase da un feedback al usuario, despúes hace la operacion y por último vuelve a
        //dar feedback al usuario

        //AsynTask es una clase abstracta y tenemos que implementar el método doInBackground

        //Recibe tres tipos para hacerlo genericos (tipo los parametros de entrada (City),tipo del progreso (Integer)
        //y la salida al realizar la operación (Forecast)
        final AsyncTask<City, Integer, LinkedList<Forecast>> weatherDowloader = new AsyncTask<City, Integer, LinkedList<Forecast>>() {

            //Guardo el objeto city como una propiedad
            private City mCity;

            //Es el único método obligatorio que tenemos que implementar
            //Se ejecuta en otro hilo distinto del principal
            @Override
            protected LinkedList<Forecast> doInBackground(City... params) { //Numero indeterminado de City (City... params)

                //Nos quedamos con la City (0)
                City city = params[0];
                //Se lo asignamos a la propiedad
                mCity=city;

                //Me creo una URL
                URL url =null;
                //Que voy a bajar en un InputStream (es una clase para recibir datos como un flujo)
                InputStream input = null;

                //Controlamos el error en caso de producirse
                try{

                    //Con String.format puedo crear una cadena a partir de unos parametros
                    url =new URL (String.format("http://api.openweathermap.org/data/2.5/forecast/daily?q=%s,uk&appid=67ec33ef65d1a073b8198058efe25905&units=metric&lang=sp", city.getnName()));

                    //Abro una conexion
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    //Me conecto
                    con.connect();

                    //Metodo que devuelve la longitud de los datos que me estoy bajando (respuesta)
                    //Es interesante porque puedo crear una barra de descarga si la longitud es muy grande
                    //El servicio del tiempo que estamos usando no lo hace no lo hace
                    int responseLengt = con.getContentLength();

                    //Bajamos la informacion por trocitos de 1k en 1k
                    byte data[]= new byte[1024];

                    long currentBytes=0;

                    //Saber cuanto me he bajado
                    int dowloaderBytes;

                    input = con.getInputStream();

                    //Me construyo una cadena cada vez más grande con la clase StringBuilder
                    //Va añadiendo datos que me voy bajando
                    //Es una version mutable de los String
                    StringBuilder sb = new StringBuilder();

                    //Si los datos que me he bajado al leer en este buffer sean <> -1
                    while ((dowloaderBytes = input.read(data)) != -1){//El servicio devuelve -1 cuando ha terminado de leer

                        //Creo una nueva cadena a partir de un array de bytes
                        sb.append(new String(data,0, dowloaderBytes));

                        if (responseLengt > 0) {
                            currentBytes += dowloaderBytes;
                            //Aqui actulizariamos nuestra barra de procesoncon currentBytes
                            publishProgress((int) (dowloaderBytes *100)/responseLengt);
                        }

                    }

                    //Analizamos los datos para convertirlo en algo que se pueda manejar en código

                    //Cojo la cadena mutante  (StringBuilder sb) y lo convierto en una cadena que puedo parsear
                    JSONObject jsonRoot = new JSONObject(sb.toString()); //jsonRoot es la raiz de mi objeto JSON

                    //Tengo que buscar acceder al objeto list con la lista de los distintos días
                    JSONArray days = jsonRoot.getJSONArray("list");

                    //Creamos una lista donde guardar los distintos forecast
                    LinkedList<Forecast> forecasts = new LinkedList<>();

                    for (int i = 0; i < days.length(); i++){

                        //Saco el día actual (que es el primero de list)
                        //JSONObject currentDay = days.getJSONObject(0);

                        //Saco todos los días que me devuelve el servicio
                        JSONObject currentDay = days.getJSONObject(i);

                        //Obtengo los datos de ese currentDay
                        float max = (float) currentDay.getJSONObject("temp").getDouble("max");
                        float min = (float) currentDay.getJSONObject("temp").getDouble("min");
                        float humidity = (float) currentDay.getDouble("humidity");
                        String description = currentDay.getJSONArray("weather").getJSONObject(0).getString("description");
                        String iconString = currentDay.getJSONArray("weather").getJSONObject(0).getString("icon");

                        //Quitamos el último caracter del iconString
                        iconString = iconString.substring(0, iconString.length() - 2);

                        //Valor por defecto a icon
                        int icon = R.drawable.ico_01;

                        //Asignamos valor a icon en funcion de lo que me devuelva iconString leido del JSON
                        if (iconString.equals("01")) {
                            icon = R.drawable.ico_01;
                        } else if (iconString.equals("02")) {
                            icon = R.drawable.ico_02;
                        } else if (iconString.equals("03")) {
                            icon = R.drawable.ico_03;
                        } else if (iconString.equals("04")) {
                            icon = R.drawable.ico_04;
                        } else if (iconString.equals("09")) {
                            icon = R.drawable.ico_09;
                        } else if (iconString.equals("10")) {
                            icon = R.drawable.ico_10;
                        } else if (iconString.equals("11")) {
                            icon = R.drawable.ico_11;
                        } else if (iconString.equals("13")) {
                            icon = R.drawable.ico_13;
                        } else if (iconString.equals("50")) {
                            icon = R.drawable.ico_50;
                        }

                        //Simulamos una carga de 5 segundos
                        //Thread.sleep(5000);

                        //Creamos nuestro objeto forecast
                        //Forecast forecast = new Forecast(max,min,humidity,description,icon);
                        //mCity.setForecast(forecast);

                        //Creamos el objeto forecast y lo guuardamos en la lista
                        forecasts.add(new Forecast(max,min,humidity,description,icon));

                    }
                    //Retornamos el objeto forecast
                    //Si se comenta podemos ver como funciona en el onPostExecute el AlertDialog
                    //return new Forecast(max,min,humidity,description,icon);

                    //Retornamos la lista de forecast
                    return forecasts;

                    //Pedimos que de nuevo se actualice la interfaz
                    //updateCityInfo();

                }catch (Exception ex){

                    //Me permite soltar en el Log la ristra de errores que se produzcan
                    ex.printStackTrace();

                }
                finally {
                    //Cerramos la conexion
                    if (input != null){
                        try {
                            input.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                //Si hay cualquier error devolvemos null
                return null;
            }

            //Estos métodos se ejecutan desde el hilo principal
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //Aqui preparamos la interfaz para la tarea larga. Estamos en el hilo principal
                mViewSwitcher.setDisplayedChild(LOADING_VIEW_INDEX);
            }

            @Override
            protected void onPostExecute(LinkedList<Forecast> forecast) {
                super.onPostExecute(forecast);

                if (forecast != null) {
                    //Aqui preparamos la interfaz con los datos que nos ha dado doInBackground. Estamos en el hilo principal
                    //Actualizamos el modelo
                    mCity.setForecast(forecast);
                    //Actualizamos la inrterfaz
                    updateCityInfo();
                } else{

                    //Ha habido un error se lo indicamos al usuario con un Dialogo
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    //Configuro el Dialogo
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("No se pudo descargar la información del tiempo");
                    alertDialog.setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            downloadWheater();
                        }
                    });
                    alertDialog.setNegativeButton("Volver", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Esto no se hace así. Se avisa a la actividad y ella decide
                            getActivity().finish();
                        }
                    });

                    //Mostramos el dialogo
                    alertDialog.show();


                }


            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                //Actualizamos la barra de progreso
                mProgressBar.setProgress(values [0]);
            }
        };

        //ejecutamos la tarea (El AsynnTask)
        weatherDowloader.execute(mCity);
    }

    //Me lo llevo tambien al ForcastRecyclerViewAdaoter
    public static float atFarnheid (Float celsius){

        return (celsius * 1.8f) + 32;
    }
}
