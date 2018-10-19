package com.example.kuhas.smartfarm_04.page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.kuhas.smartfarm_04.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class View_Mushroom_Data extends AppCompatActivity {
    Context context;
    String key;
    FirebaseDatabase database;
    DatabaseReference reference;
   private TextView textHumidMin, textHumidMax, textTemMin, textTemMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("รายละเอียด");

        Intent intent = getIntent();
        key = intent.getStringExtra("View");
        final TextView view = findViewById(R.id.tView);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("TypeMushroom");
        ////////// Find View By ID +++++++++++++++++++++++++++++++++++++
        textHumidMin = findViewById(R.id.textHumidMin);
        textHumidMax = findViewById(R.id.textHumidMax);
        textTemMin = findViewById(R.id.textTemMin);
        textTemMax = findViewById(R.id.textTemMax);
//        view.setText(key);

        reference.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                view.setText(dataSnapshot.child("mode").getValue().toString());
                textHumidMin.setText(dataSnapshot.child("hummidMin").getValue().toString());
                textHumidMax.setText(dataSnapshot.child("hummidMax").getValue().toString());
                textTemMin.setText(dataSnapshot.child("temMin").getValue().toString());
                textTemMax.setText(dataSnapshot.child("temMax").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

