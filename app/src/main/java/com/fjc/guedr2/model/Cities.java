package com.fjc.guedr2.model;

import com.fjc.guedr2.R;

import java.util.LinkedList;

/**
 * Created by javier on 15/4/16.
 */
public class Cities {

    private LinkedList<City> mCities;


    public Cities() {
        mCities = new LinkedList<City>();
        mCities.add(new City("Madrid", new Forecast(29,10,26,"Soleado con nubes", R.drawable.sun_cloud)));
        //mCities.add(new City("Jaen", new Forecast(38,20,10,"Soleado", R.drawable.ico_01)));
        //mCities.add(new City("Quito", new Forecast(25,12,10,"Arcoiris", R.drawable.ico_10)));

        //Sin Forecast
        mCities.add(new City("Jaen"));
        mCities.add(new City("Quito"));
        mCities.add(new City("Londres"));
        mCities.add(new City("Paris"));
        mCities.add(new City("Roma"));
    }

    public LinkedList<City> getCities() {
        return mCities;
    }
}
