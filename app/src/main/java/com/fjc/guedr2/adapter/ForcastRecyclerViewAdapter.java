package com.fjc.guedr2.adapter;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fjc.guedr2.R;
import com.fjc.guedr2.fragment.FragmentForecast;
import com.fjc.guedr2.model.Forecast;

import java.util.LinkedList;

/**
 * Created by javier on 27/4/16.
 */
public class ForcastRecyclerViewAdapter extends RecyclerView.Adapter<ForcastRecyclerViewAdapter.ForecastViewHolder> {


    private LinkedList<Forecast> mForecast;
    private Context mContext;


    public ForcastRecyclerViewAdapter (LinkedList<Forecast> forecast, Context context){
        super();
        mForecast=forecast;
        mContext=context;
    }

    //En este método muestra la interfaz de cada uno de los elementos
    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Creo la "fila" y se la asigno al ViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_forecast,parent,false);
        //Le decimos al ViewHolder que sabe como representar las celdas la vista de esas celdas que
        //tiene que pintar
        return new ForecastViewHolder(view);
    }

    //Este método se usa cuando va a mostrar la celda en la pantalla
    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {

        holder.bindForecast(mForecast.get(position),mContext);

    }

    @Override
    public int getItemCount() {
        //return 0;
        return mForecast.size();
    }

    public class ForecastViewHolder extends RecyclerView.ViewHolder {

        //private static final String PREFERENCE_UNITS="units";

        private TextView mMax_temp;
        private TextView mMin_temp;
        private TextView mHumidity;
        private TextView mDescription;
        private ImageView mForecast_image;

        public ForecastViewHolder(View itemView) {
            super(itemView);
            mMax_temp = (TextView) itemView.findViewById(R.id.max_temp);
            mMin_temp = (TextView) itemView.findViewById(R.id.min_temp);
            mHumidity = (TextView) itemView.findViewById(R.id.humidity);
            mDescription = (TextView) itemView.findViewById(R.id.description_forecast);
            mForecast_image = (ImageView) itemView.findViewById(R.id.forecast_img);
        }

        public void bindForecast(Forecast forecast, Context context) {

            //Muestro en la interfaz mi modelo
            float maxTemp = forecast.getMaxTemp();
            float minTemp = forecast.getMinTemp();

            boolean showCelsius = PreferenceManager.getDefaultSharedPreferences(context).
                    getBoolean(FragmentForecast.PREFERENCE_UNITS, true); //Se asigna true como valor por defecto si inicialmente no tienen valor


            if (showCelsius != true) {

                maxTemp = atFarnheid(maxTemp);
                minTemp = atFarnheid(minTemp);
            }

            mMax_temp.setText(String.format(context.getString(R.string.max_temp_label), maxTemp));
            mMin_temp.setText(String.format(context.getString(R.string.min_temp_label), minTemp));
            mHumidity.setText(String.format(context.getString(R.string.humidity_label), forecast.getHumidity()));
            mDescription.setText(forecast.getDescription());
            //Para el icono utilizamos un método que nos devuelve un recurso
            mForecast_image.setImageResource(forecast.getIcon());
        }

        public float atFarnheid (Float celsius){

            return (celsius * 1.8f) + 32;
        }

    }
}
