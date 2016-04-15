package com.fjc.guedr2.model;

/**
 * Created by javier on 15/4/16.
 */
public class City {

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
}
