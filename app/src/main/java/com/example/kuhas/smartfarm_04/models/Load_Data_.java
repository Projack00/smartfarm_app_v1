package com.example.kuhas.smartfarm_04.models;

public class Load_Data_ {
     String mode ,key;
//    public int hummidMax, hummidMin, temMax, temMin;

    public Load_Data_() {
    }

    public Load_Data_(String mode, String key) {
        this.mode = mode;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
