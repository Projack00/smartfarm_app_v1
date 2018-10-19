package com.example.kuhas.smartfarm_04.FirebaseClient;

import android.content.Context;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kuhas.smartfarm_04.Adapter.aDetail_Data;
import com.example.kuhas.smartfarm_04.models.Load_Data_;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Detail_Data {
    Context c;
    ListView listView;
    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<Load_Data_> load_data = new ArrayList<>();
    aDetail_Data aDetail_data;

    public Detail_Data(Context c, ListView listView) {
        this.c = c;
        this.listView = listView;

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("TypeMushroom");
    }


    public void refreshData() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                load_data.clear();

                for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren()) {

                    Load_Data_ data = new Load_Data_();
                    data.setMode(myDataSnapshot.getValue(Load_Data_.class).getMode());
                    data.setKey(myDataSnapshot.getValue(Load_Data_.class).getKey());
                    load_data.add(data);

                }

                if (load_data.size() > 0) {
                    aDetail_data = new aDetail_Data(c, load_data);
                    listView.setAdapter((ListAdapter) aDetail_data);
                } else {
                    Toast.makeText(c, "No Data ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
