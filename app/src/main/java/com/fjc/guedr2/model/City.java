package com.fjc.guedr2.model;

import java.io.Serializable;

/**
 * Created by javier on 15/4/16.
 */
public class City implements Serializable{

    private String nName;
    private Forecast mForecast;

    public City(String nName, Forecast forecast) {
        this.nName = nName;
        mForecast = forecast;
    }

    public String getnName() {
        return nName;
    }

    public void setnName(String nName) {
        this.nName = nName;
    }

    public Forecast getForecast() {
        return mForecast;
    }

    public void setForecast(Forecast forecast) {
        mForecast = forecast;
    }

    @Override
    public String toString() {
        return getnName();
    }
}
