package com.example.kuhas.smartfarm_04;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuhas.smartfarm_04.models.DHT;
import com.example.kuhas.smartfarm_04.models.StatusMode_TyperMashroom;
import com.example.kuhas.smartfarm_04.page.AlertNotification;
import com.example.kuhas.smartfarm_04.page.Detail_Mushroom;
import com.example.kuhas.smartfarm_04.page.Grapg;
import com.example.kuhas.smartfarm_04.page.Insert_Mashroom;
import com.example.kuhas.smartfarm_04.page.NotificationService;
import com.example.kuhas.smartfarm_04.page.Update_Data_Mashroom;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String switchOn = "ON";
    String switchOff = "OFF";
    public String mSelected = null;
    String value_mode_text;

    Button btnShowDialog;
    TextView mode, StatusTemp, StatusHumid, time;
    TextView textfan, textPump, textLED;
    Switch switchFann, switchPump, switchLED;

    FirebaseDatabase database;
    DatabaseReference myRefFan;
    DatabaseReference myRefPump;
    DatabaseReference myRefLED;
    DatabaseReference refMode, refDHT;
    DatabaseReference refTypeMushroom;
    DatabaseReference where_DHT;
    DatabaseReference set_Tem_min, set_Tem_max, set_Hu_min, set_Hu_max;

    String string_Tem_min;

    ArrayList<String> stringArrayList = new ArrayList<>();
    ArrayAdapter<String> stringArrayAdapter, myArrList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//////////////////// startService //////////////////////////////////////////////////////////////////
        startService(new Intent(this, NotificationService.class));
////////////////////// END startService ////////////////////////////////////////////////////////////

        database = FirebaseDatabase.getInstance();

        refTypeMushroom = database.getReference("TypeMushroom");

        myRefFan = database.getReference("sFanStatus");
        myRefPump = database.getReference("sPumpStatus");
        myRefLED = database.getReference("sLEDStatus");
////////////////////////////////////////////////////////////////////////////////////////////////////
        set_Tem_min = database.getReference("set_Tem_min");
        set_Tem_max = database.getReference("set_Tem_max");
        set_Hu_min = database.getReference("set_Hu_min");
        set_Hu_max = database.getReference("set_Hu_max");
////////////////////////////////////////////////////////////////////////////////////////////////////
        where_DHT = database.getReference("TypeMushroom");

        findView();

        ///SwithConsole
        swithFan();
        swithPump();
        swithLED();
        statusMode();
        LogDHT();
        Dialog();
        where_DHT();
////////////////////////////////////////////////////////////////////////////////////////////////////
//        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
////////////////////////////////////////////////////////////////////////////////////////////////////

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void Dialog() {


        refTypeMushroom.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                stringArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_singlechoice);

                for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren()) {
                    StatusMode_TyperMashroom mode_typer = myDataSnapshot.getValue(StatusMode_TyperMashroom.class);
                    stringArrayAdapter.add(mode_typer.getMode());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Select Favorite Team Mode");
                builder.setSingleChoiceItems(stringArrayAdapter, 100, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSelected = stringArrayAdapter.getItem(which);
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), " สถานะ : " +
                                mSelected, Toast.LENGTH_SHORT).show();


                        refMode.setValue(mSelected);

                        where_DHT.child(mSelected).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Log.i("TAG", "where_DHT = > " + dataSnapshot.getValue());
                                set_Hu_max.setValue(dataSnapshot.child("hummidMax").getValue());
                                set_Tem_min.setValue(dataSnapshot.child("temMin").getValue());
                                set_Tem_max.setValue(dataSnapshot.child("temMax").getValue());
                                set_Hu_min.setValue(dataSnapshot.child("hummidMin").getValue());

                                string_Tem_min = String.valueOf(dataSnapshot.child("mode").getValue());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("ยกเลิก", null);
                // สุดท้ายอย่าลืม show() ด้วย
                builder.show();
            }
        });
    }

    private void LogDHT() {
        refDHT = database.getReference("logDHT");
//        refDHT = database.getReference("logDHT").limitToFirst(10);
        refDHT.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren()) {
                    DHT pointValue = myDataSnapshot.getValue(DHT.class);
//                    Log.i("TAG", "tag_-=>"+ pointValue.getTime());


                    time.setText(pointValue.getTime());
                    StatusTemp.setText(pointValue.getTemperature() + " C ");
                    StatusHumid.setText(pointValue.getHumidity() + " % ");


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void statusMode() {
        refMode = database.getReference("Mode_Status");

        refMode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                value_mode_text = dataSnapshot.getValue(String.class);
                mode.setText("สถานะ : " + value_mode_text);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void swithFan() {

//  Read Firebase from the database
        myRefFan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//   This method is called once with the initial value and again
//   whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
//                Log.d("myRef", "Value is: " + dataSnapshot.getValue());
                textfan.setText(value);

//                boolean StatusChecked = ((value == "ON") ? true : false);
//                if (value == "ON"){
                if (value.equals(switchOn)) {
                    switchFann.setChecked(true);
//                    Log.i("myRef_01", "switchFann = true");

                } else {
                    switchFann.setChecked(false);
//                    Log.i("myRef_01", "switchFann = false");
                }
//                switchFann.setChecked(StatusChecked);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//              Failed to read value
                Log.w("myRef", "Failed to read value.", databaseError.toException());
            }
        });
//              boolean StatusChecked = true;
//              switchFann.setChecked(true); false

        switchFann.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                  ใช้ if แบบปกติ
//                  if (isChecked) {   fan.setText(switchOn);   }  else  {   fan.setText(switchOff);   }
//                  ใช้ if แบบสั้น

                String fanstatus = ((isChecked) ? switchOn : switchOff);
                myRefFan.setValue(fanstatus);
            }
        });
    }

    private void swithPump() {
        myRefPump.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
//                Log.d("myRef", "Value is: " + dataSnapshot.getValue());
                textPump.setText(value);

                boolean stringpump = (value.equals(switchOn)) ? true : false;
                switchPump.setChecked(stringpump);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("myRefPump", "Failed to read value.", databaseError.toException());
            }
        });

        switchPump.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                  ใช้ if แบบปกติ
//                  if (isChecked) {  fan.setText(switchOn); } else {  fan.setText(switchOff);  }
//                  ใช้ if แบบสั้น
                String stringpump = (isChecked) ? switchOn : switchOff;
                myRefPump.setValue(stringpump);
            }
        });
    }

    private void swithLED() {
        myRefLED.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
//                Log.d("myRef", "Value is: " + dataSnapshot.getValue());
                textLED.setText(value);

                boolean stringLED = (value.equals(switchOn)) ? true : false;
                switchLED.setChecked(stringLED);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("myRefLED", "Failed to read value.", databaseError.toException());
            }
        });

        switchLED.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                  ใช้ if แบบปกติ
//                  if (isChecked) {   fan.setText(switchOn);   } else {  fan.setText(switchOff);  }
//                  ใช้ if แบบสั้น
                String StringLED = (isChecked) ? switchOn : switchOff;
                myRefLED.setValue(StringLED);
            }
        });
    }

    private void findView() {
//  Button
        btnShowDialog = findViewById(R.id.button1);

//  TextView
        time = findViewById(R.id.time);
        mode = findViewById(R.id.mode);
        StatusTemp = findViewById(R.id.StatusTemp);
        StatusHumid = findViewById(R.id.StatusHumid);
        textfan = (TextView) findViewById(R.id.textFan);
        textPump = (TextView) findViewById(R.id.textPump);
        textLED = (TextView) findViewById(R.id.textLED);

//  Swith Button
        switchFann = findViewById(R.id.FanSwith);
        switchPump = findViewById(R.id.pump);
        switchLED = findViewById(R.id.ledSwith);
    }

    private void where_DHT() {


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent = new Intent(this, Insert_Mashroom.class);
            startActivity(intent);

        } else if (id == R.id.notification) {
            Intent intent = new Intent(this, AlertNotification.class);
            startActivity(intent);

        } else if (id == R.id.graph) {
            Intent intent = new Intent(this, Grapg.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(this, Detail_Mushroom.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
