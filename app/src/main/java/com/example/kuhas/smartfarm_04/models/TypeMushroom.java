package com.example.kuhas.smartfarm_04.models;

public class TypeMushroom {
    private int TemMin;
    private int HummidMin;
    private int HummidMax;
    private int TemMax;
    private String Mode;
    private String key;


    public TypeMushroom() {

    }

    public int getTemMin() {
        return TemMin;
    }

    public void setTemMin(int temMin) {
        TemMin = temMin;
    }

    public int getHummidMin() {
        return HummidMin;
    }

    public void setHummidMin(int hummidMin) {
        HummidMin = hummidMin;
    }

    public int getHummidMax() {
        return HummidMax;
    }

    public void setHummidMax(int hummidMax) {
        HummidMax = hummidMax;
    }

    public int getTemMax() {
        return TemMax;
    }

    public void setTemMax(int temMax) {
        TemMax = temMax;
    }

    public String getMode() {
        return Mode;
    }

    public void setMode(String mode) {
        Mode = mode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
