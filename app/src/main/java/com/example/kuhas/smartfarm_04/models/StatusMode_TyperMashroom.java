package com.example.kuhas.smartfarm_04.models;

public class StatusMode_TyperMashroom {
    private int TemMin;
    private int HummidMin;
    private int HummidMax;
    private int TemMax;
    private String Mode;

    public StatusMode_TyperMashroom() {
    }

    public StatusMode_TyperMashroom(int temMin, int hummidMin, int hummidMax, int temMax, String mode) {
        TemMin = temMin;
        HummidMin = hummidMin;
        HummidMax = hummidMax;
        TemMax = temMax;
        Mode = mode;
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
}
