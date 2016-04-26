package com.fjc.guedr2.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fjc.guedr2.R;
import com.fjc.guedr2.model.Cities;
import com.fjc.guedr2.model.City;

/**
 * A simple {@link Fragment} subclass.
 */
public class CityListFragment extends Fragment {

    //Guardamos una referencia al listener
    private CityListListener mCityListListener;

    public CityListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_city_list, container, false);

        //Accedo al ListView
        ListView list = (ListView) root.findViewById(android.R.id.list);

        //Necesito un modelo con el que darles valores a la vista
        final Cities cities = new Cities();

        //Las listan se rellenan con un adaptador
        //En el caso de las listas existe un adaptador que nos da todo
        ArrayAdapter<City> adapter =
                new ArrayAdapter<City>(getActivity(), //Contexto
                        android.R.layout.simple_list_item_1, //Como pinta la lista. Cogemos una por defecto que trae android
                        cities.getCities() //Lista de ciudades
                );

        //Le asignamos el adaptador a la vista para que pinte las ciudades
        list.setAdapter(adapter);

        //Para que la lista se entere de que se ha pulsado una celda me tengo que crear un listener para la lista
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Aviso a mi actividad (listener)
                //Compruebo si mi listener está enganchado a la actividad
                if (mCityListListener != null){
                    //Aviso a mi listener
                    //Tengo que sacar la ciudad que se ha pulsado
                    City citySelected = cities.getCities().get(position);

                    //Se lo paso al listener (llamo al método de la interfaz)
                    mCityListListener.onCitySelected(citySelected, position);
                }


            }
        });

        //Hacemos algo con el FloatingActionButton
        FloatingActionButton addButton = (FloatingActionButton) root.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Creo un SnackBar
                Snackbar.make(getView(),"FAB Pulsado",Snackbar.LENGTH_LONG).show();
            }
        });

        return root;
    }

    //Asigno mi listener a la actividad (ForecastActivity) a partir de los métodos OnAttach y OnDetach

    //En el método OnAttach este fragment está "enganchado" a la actividad
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        //Mi listener es la actividad (ForecastActivity)
        mCityListListener = (CityListListener) activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //Mi listener es la actividad (ForecastActivity)
        mCityListListener = (CityListListener) getActivity();
    }

    //En el método OnDetach este fragment no está "enganchado" a la actividad
    @Override
    public void onDetach() {
        super.onDetach();

        mCityListListener = null;
    }

    //Creo una interfaz para comunicarme con la actividad que contienen a este fragment (ForecastActivity)
    //Esta interfaz la implementa la actividad que es quien se quiere enterar de que se ha pulsado la lista

    public interface CityListListener {

        void onCitySelected (City city, int position);

    }

}
