package com.example.kuhas.smartfarm_04.page;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kuhas.smartfarm_04.FirebaseClient.Detail_Data;
import com.example.kuhas.smartfarm_04.Holder.hDetail_Data;
import com.example.kuhas.smartfarm_04.R;

public class Detail_Mushroom extends AppCompatActivity {

    ListView listView;
    Detail_Data fireClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__mushroom);

        getSupportActionBar().setTitle("จัดการข้อมูล");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //FIREBASE DATABASE
        listView = findViewById(R.id.listview);
        fireClient = new Detail_Data(this, listView);
        fireClient.refreshData();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
