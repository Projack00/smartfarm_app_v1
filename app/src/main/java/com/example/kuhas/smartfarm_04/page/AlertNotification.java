package com.example.kuhas.smartfarm_04.page;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.kuhas.smartfarm_04.FirebaseClient.Alert_equipment;
import com.example.kuhas.smartfarm_04.R;

public class AlertNotification extends AppCompatActivity {
    TextView textView4, time, equipment;
    String alarm_Value_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_notification);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("แจ้งเตือน");


//        stopService(new Intent(this, NotificationService.class));

        Intent intent = new Intent();
        alarm_Value_count = intent.getStringExtra("alarm_Value_count");
        textView4 = findViewById(R.id.textView4);
        time = findViewById(R.id.textView5);
        equipment = findViewById(R.id.textView6);

        Alert_equipment alert_equipment = new Alert_equipment(this, textView4,time,equipment);
        alert_equipment.refreshData();

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
