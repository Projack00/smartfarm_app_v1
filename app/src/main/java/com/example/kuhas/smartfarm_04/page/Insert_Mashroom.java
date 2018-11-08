package com.example.kuhas.smartfarm_04.page;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.provider.Contacts.SettingsColumns.KEY;

public class Insert_Mashroom extends AppCompatActivity {

    Context myContext;
    List<String> lstSource = new ArrayList<String>();
    Spinner spin_temp_max, spin_temp_min, spin_hum_max, spin_hum_min;
    Button Insert;
    EditText editMode;
    FirebaseDatabase database;
    DatabaseReference ref;

    TypeMushroom data;
    private Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert__mashroom);

        getSupportActionBar().setTitle("เพิ่มข้อมูลเห็ด");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        generateeData();

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("TypeMushroom");
        data = new TypeMushroom();
//==================================================================================================
        spin_temp_min = findViewById(R.id.Tem_min);
        spin_temp_max = findViewById(R.id.Tem_Max);
        spin_hum_min = findViewById(R.id.Humid_Min);
        spin_hum_max = findViewById(R.id.Humid_max);
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

        myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.dialog_contact);

        final TextView text_show_data = myDialog.findViewById(R.id.text_show_data);
        final TextView text_detail = myDialog.findViewById(R.id.text_detail);
        Button btn_OK = myDialog.findViewById(R.id.btn_OK);
        Button btn_Cancel = myDialog.findViewById(R.id.btn_CANCEL);

        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "เพิ่มข้อมูลสำเร็จ", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Insert_Mashroom.this,MainActivity.class);
                startActivity(intent);
                finish();
                myDialog.dismiss();
            }
        });
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "ยกเลิกรายการแล้ว", Toast.LENGTH_SHORT).show();
                myDialog.dismiss();

            }
        });
        text_detail.setText("ต้องการออกจากหน้านี้หรือไม่ ??");
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


//==================================================================================================

        Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getValues();
                String s = data.getMode();

                text_show_data.setText("บันทึก " + s + " สำเร็จ");

                if (s.equals("")) {
                    Snackbar.make(v, "Please Edit Name", Snackbar.LENGTH_LONG).show();
                } else {
                    ref.child(s).setValue(data);
                    myDialog.show();
                }
                editMode.setText("");

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
        data.setKey(editMode.getText().toString());
    }

    private void generateeData() {
        // get the selected dropdown list value

        for (int i = 0; i < 100; i++) {
            lstSource.add(String.valueOf(i));
        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent = new Intent(this, Insert_Mashroom.class);
            startActivity(intent);

//        } else if (id == R.id.nav_gallery) {
//            Intent intent = new Intent(this, Update_Data_Mashroom.class);
//            startActivity(intent);

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
