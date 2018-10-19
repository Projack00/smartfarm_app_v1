package com.example.kuhas.smartfarm_04.models;

public class Alert_Notification {
    private int alarm_Value_count, status;
    private String time, equipment;


    public Alert_Notification(int alarm_Value_count, int status, String time, String equipment) {
        this.alarm_Value_count = alarm_Value_count;
        this.status = status;
        this.time = time;
        this.equipment = equipment;

    }

    public Alert_Notification() {

    }

    public int getAlarm_Value_count() {
        return alarm_Value_count;
    }

    public void setAlarm_Value_count(int alarm_Value_count) {
        this.alarm_Value_count = alarm_Value_count;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
}
