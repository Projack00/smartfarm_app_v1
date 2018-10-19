package com.example.kuhas.smartfarm_04.models;

import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class DHT {

    public int humidity, temperature;
    public String time;

    public DHT() {
    }

    public DHT(int humidity, int temperature, String time) {
        this.humidity = humidity;
        this.temperature = temperature;
        this.time = time;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
