package com.fjc.guedr2.model;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by javier on 15/4/16.
 */
public class City implements Serializable{

    private String nName;
    private LinkedList<Forecast> mForecast;

    public City(String nName, LinkedList<Forecast> forecast) {
        this.nName = nName;
        mForecast = forecast;
    }

    //Creo un Constructor sólo con el nombre de la Ciudad.
    //El Forecast lo bajo de internet
    public City(String nName) {
        this.nName = nName;
    }

    public String getnName() {
        return nName;
    }

    public void setnName(String nName) {
        this.nName = nName;
    }

    public LinkedList<Forecast> getForecast() {
        return mForecast;
    }

    public void setForecast(LinkedList<Forecast> forecast) {
        mForecast = forecast;
    }

    @Override
    public String toString() {
        return getnName();
    }
}
