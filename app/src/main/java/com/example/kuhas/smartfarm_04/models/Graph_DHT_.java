package com.example.kuhas.smartfarm_04.models;

/**
 * Created by Wikanda on 1/9/2561.
 */

public class Graph_DHT_ {
    int humidity, temperature;
    String time;

    public Graph_DHT_() {
    }

    public Graph_DHT_(int humidity, int temperature, String time) {
        this.humidity = humidity;
        this.temperature = temperature;
        this.time = time;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getTime() {
        return time;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
