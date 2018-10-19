package com.example.kuhas.smartfarm_04.page;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuhas.smartfarm_04.MainActivity;
import com.example.kuhas.smartfarm_04.R;
import com.example.kuhas.smartfarm_04.models.TypeMushroom;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Update_Data_Mashroom extends AppCompatActivity {
    private Button notification;
    List<String> lstSource = new ArrayList<String>();
    Spinner spin_temp_max, spin_temp_min, spin_hum_max, spin_hum_min;
    Button Insert;
    EditText editMode;
    FirebaseDatabase database;
    DatabaseReference reference;
    String key;
    TypeMushroom data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__data__mashroom);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("แก้ไขข้อมูล");


        Intent intent = getIntent();
        key = intent.getStringExtra("View");

        generateeData();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("TypeMushroom");
        data = new TypeMushroom();
//==================================================================================================
        spin_temp_min = findViewById(R.id.Tem_min);
        spin_temp_max = findViewById(R.id.Tem_Max);
        spin_hum_max = findViewById(R.id.Humid_Min);
        spin_hum_min = findViewById(R.id.Humid_max);
        editMode = findViewById(R.id.Edit_Name_Mashroom);
        Insert = (Button) findViewById(R.id.Insert_Name);
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        ArrayAdapter<String> spin_adapper = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, lstSource);
        spin_temp_min.setAdapter(spin_adapper);
        spin_temp_max.setAdapter(spin_adapper);
        spin_hum_max.setAdapter(spin_adapper);
        spin_hum_min.setAdapter(spin_adapper);


//==================================================================================================
//=========================Test=========================================================================

//        spin_hum_max.setSelection(80);

        reference.child(key).addValueEventListener(new ValueEventListener() {
            int textTemMax, textHumidMin, textHumidMax, textTemMin;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                editMode.setText(dataSnapshot.child("mode").getValue().toString());
                textHumidMin = Integer.parseInt(dataSnapshot.child("hummidMin").getValue().toString());
                textHumidMax = Integer.parseInt(dataSnapshot.child("hummidMax").getValue().toString());
                textTemMin = Integer.parseInt(dataSnapshot.child("temMin").getValue().toString());
                textTemMax = Integer.parseInt(dataSnapshot.child("temMax").getValue().toString());

                spin_temp_max.setSelection(textTemMax);
                spin_temp_min.setSelection(textTemMin);
                spin_hum_max.setSelection(textHumidMax);
                spin_hum_min.setSelection(textHumidMin);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//==================================================================================================

        Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValues();
                String s = data.getMode();
                if (s.equals("")) {
                    Snackbar.make(v, "Please Edit Name", Snackbar.LENGTH_LONG).show();

                } else {
                    reference.child(key).setValue(data);
                    Toast.makeText(Update_Data_Mashroom.this, "บันทึกสำเร็จ", Toast.LENGTH_SHORT).show();

                }

            }

        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getValues() {
        data.setHummidMin(Integer.parseInt(String.valueOf(spin_hum_min.getSelectedItem())));
        data.setHummidMax(Integer.parseInt(String.valueOf(spin_hum_max.getSelectedItem())));
        data.setTemMin(Integer.parseInt(String.valueOf(spin_temp_min.getSelectedItem())));
        data.setTemMax(Integer.parseInt(String.valueOf(spin_temp_max.getSelectedItem())));
        data.setMode(editMode.getText().toString());
        data.setKey(key);
    }

    private void generateeData() {
        // get the selected dropdown list value

        for (int i = 0; i < 100; i++) {
            lstSource.add(String.valueOf(i));
        }
    }

}