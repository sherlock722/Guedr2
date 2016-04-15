package com.fjc.guedr2.fragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fjc.guedr2.R;
import com.fjc.guedr2.model.Cities;


/**
 * A simple {@link Fragment} subclass.
 */
public class CityPagerFragment extends Fragment {

    public CityPagerFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_city_pager, container, false);

        //Accedemos al ViewPager de nuestra interfaz
        ViewPager pager = (ViewPager) root.findViewById(R.id.view_pager);

        //Cuantos fragment tiene nuestra interfaz >> Necesitamos lo que se llama la clase Adaptador
        //Permite unir el modelo con la interfaz. Pasamos el módelo al adaptador y el Pager sabrá  apartir del Adaptador
        //lo que tiene que poner


        //Le decimos al ViewPager quien es su adaptador que le dará los fragment que debe dibujar
        pager.setAdapter(new CityPagerAdapter(getFragmentManager()));


        return root;
    }

}

//Creamos la calse CityPagerAdapter
class CityPagerAdapter extends FragmentPagerAdapter { //v.13 support //FragmentPager Adapter es una clase abstracta

    private Cities mCities;
    public CityPagerAdapter(FragmentManager fm) {

        super(fm);
        mCities=new Cities();

    }

    //Que fragment va en una determinada posición
    @Override
    public android.app.Fragment getItem(int position) {

        //Vamos a pasar parametros al fragment a partir de argumentos
        String cityName = mCities.getCities().get(position).getnName();

        FragmentForecast fragment = new FragmentForecast();

        Bundle arguments = new Bundle();
        arguments.getString("cityName", cityName);
        fragment.setArguments(arguments);


        /* Necesito un fragment que devuelva el FragmentForecast */
        return new FragmentForecast();
    }

    //Número de partes en el ViewPAger
    @Override
    public int getCount() {

        //return 10;
        return mCities.getCities().size();
    }


}